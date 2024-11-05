package Driver;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class driver {
	static WebDriver driver;

    public static void main(String[] args) {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://stage.loadsecuresystems.com/");

        login("danish.rehman08@gmail.com", "123456");  

        addCustomer("devdefy", "Josh", "1234567890");

}
    
    private static void login(String email, String password) {
        WebElement emailField = waitForElement(By.id(":r0:"), 10);
        emailField.sendKeys(email);

        WebElement passwordField = waitForElement(By.id(":r1:"), 10);
        passwordField.sendKeys(password);

        WebElement signInButton = waitForElement(By.xpath("//button[@type='submit']"), 10);
        signInButton.click();
    }

    private static void addCustomer(String companyName, String customerName, String phone) {
        clickCustomersButton();
        clickNewCustomerButton();

        WebElement customerNameField = waitForElement(By.id("driverName"), 10);
        customerNameField.sendKeys(customerName);

        WebElement emailField = waitForElement(By.id("phone"), 10);
        emailField.sendKeys(phone);

        selectCompany(companyName);

        WebElement addDRIVERButton = waitForElementToBeClickable(By.id("addDriver"), 30);

        System.out.println("Add Customer Button is displayed: " + addDRIVERButton.isDisplayed());
        System.out.println("Add Customer Button is enabled: " + addDRIVERButton.isEnabled());

        try {
        	addDRIVERButton.click(); 
        } catch (Exception e) {
            System.out.println("Standard click failed, trying JavaScript click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addDRIVERButton);
        }
    }

    private static void clickCustomersButton() {
        WebElement driversButton = waitForElementToBeClickable(By.id("drivers"), 30);
        driversButton.click();
    }

    private static void clickNewCustomerButton() {
        WebElement newdriverButton = waitForElementToBeClickable(By.id("newDrivers"), 30);
        newdriverButton.click();
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
