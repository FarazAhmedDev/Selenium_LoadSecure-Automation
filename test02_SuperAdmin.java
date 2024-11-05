package automation1;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class test02_SuperAdmin {

    static WebDriver driver;

    public static void main(String[] args) {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://stage.loadsecuresystems.com/");
        
        login("danish.rehman08@gmail.com", "123456");
        
        createNewUser("josh", "Mathews", "Mathews@example.com", "CompanyUser", "2121212122", "google");
        
        driver.quit(); 
    }

    public static void login(String email, String password) {
        WebElement emailField = waitForElement(By.id(":r0:"), 10);
        emailField.sendKeys(email);

        WebElement passwordField = waitForElement(By.id(":r1:"), 10);
        passwordField.sendKeys(password);

        WebElement signInButton = waitForElement(By.xpath("//button[@type='submit']"), 10);
        signInButton.click();
    }

 
    public static void createNewUser(String firstName, String lastName, String email, String role, String phone, String companyName) {
        WebElement userButton = waitForElement(By.id("users"), 10);
        userButton.click();

        WebElement newUserButton = waitForElement(By.id("newUsers"), 10);
        newUserButton.click();

        WebElement firstNameField = waitForElement(By.id("fname"), 10);
        firstNameField.sendKeys(firstName);

        WebElement lastNameField = waitForElement(By.id("lname"), 10);
        lastNameField.sendKeys(lastName);

        WebElement newUserEmail = waitForElement(By.id("email"), 10);
        newUserEmail.sendKeys(email);	

        WebElement roleDropdown = waitForElement(By.id("role"), 10);
        roleDropdown.click();

        WebElement companyUserOption = waitForElement(By.id(role), 10);
        companyUserOption.click();

        WebElement newUserPhoneNumber = waitForElement(By.id("phone"), 10);
        newUserPhoneNumber.sendKeys(phone);

        MUIAutocomplete.selectCompany(driver, companyName);

        WebElement addButton = waitForElement(By.id("addUser"), 10);
        addButton.click();
    }

  
    public static WebElement waitForElement(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

  
    public static class MUIAutocomplete {
        public static void selectCompany(WebDriver driver, String companyName) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement companyNameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchCompany")));
            companyNameField.click();
            companyNameField.sendKeys(companyName);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(text(),'" + companyName + "')]")));

            companyNameField.sendKeys(Keys.ARROW_DOWN);
            companyNameField.sendKeys(Keys.ENTER);

            String selectedCompany = companyNameField.getAttribute("value");	
            if (selectedCompany.equals(companyName)) {
                System.out.println("Successfully selected the company: " + selectedCompany);
            } else {
                System.out.println("Failed to select the company: " + companyName);
            }
        }
    }
}
