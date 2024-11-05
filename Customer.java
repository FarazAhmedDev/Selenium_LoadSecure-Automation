package Customer;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class Customer {
    static WebDriver driver;

    public static void main(String[] args) {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://stage.loadsecuresystems.com/");

        login("danish.rehman08@gmail.com", "123456");  

        addCustomer("devdefy", "Josh", "faraz@devdefy.com");

        
    }

    private static void login(String email, String password) {
        WebElement emailField = waitForElement(By.id(":r0:"), 10);
        emailField.sendKeys(email);

        WebElement passwordField = waitForElement(By.id(":r1:"), 10);
        passwordField.sendKeys(password);

        WebElement signInButton = waitForElement(By.xpath("//button[@type='submit']"), 10);
        signInButton.click();
    }

    private static void addCustomer(String companyName, String customerName, String email) {
        clickCustomersButton();
        clickNewCustomerButton();

        WebElement customerNameField = waitForElement(By.id("customerName"), 10);
        customerNameField.sendKeys(customerName);

        WebElement emailField = waitForElement(By.id("email"), 10);
        emailField.sendKeys(email);

        selectCompany(companyName);

        WebElement addCustomerButton = waitForElementToBeClickable(By.id("addCustomer"), 30);

        System.out.println("Add Customer Button is displayed: " + addCustomerButton.isDisplayed());
        System.out.println("Add Customer Button is enabled: " + addCustomerButton.isEnabled());

        try {
            addCustomerButton.click(); 
        } catch (Exception e) {
            System.out.println("Standard click failed, trying JavaScript click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addCustomerButton);
        }
    }

    private static void clickCustomersButton() {
        WebElement customersButton = waitForElementToBeClickable(By.id("customers"), 30);
        customersButton.click();
    }

    private static void clickNewCustomerButton() {
        WebElement newCustomersButton = waitForElementToBeClickable(By.id("newCustomers"), 30);
        newCustomersButton.click();
    }

    private static void selectCompany(String companyName) {
        WebElement companyNameField = waitForElementToBeClickable(By.id("searchCompany"), 10);
        companyNameField.click();
        companyNameField.clear(); 
        companyNameField.sendKeys(companyName);

        WebElement companyOption = waitForElement(By.xpath("//li[contains(text(),'" + companyName + "')]"), 10);
        
        if (companyOption != null) {
            companyOption.click();
            System.out.println("Company option selected: " + companyName);
        } else {
            System.out.println("Company option is not visible.");
        }

        String selectedCompany = companyNameField.getAttribute("value");
        validateCompanySelection(selectedCompany, companyName);
    }

    private static void validateCompanySelection(String selectedCompany, String companyName) {
        if (selectedCompany.equals(companyName)) {
            System.out.println("Successfully selected the company: " + selectedCompany);
        } else {
            System.out.println("Failed to select the company: " + companyName);
        }
    }

    private static WebElement waitForElement(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private static WebElement waitForElementToBeClickable(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
