package COMPANY;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class Company {
    
    static WebDriver driver;

    public static void main(String[] args) {
        setupDriver();

        login("danish.rehman08@gmail.com", "123456");

        createNewCompany("DevvDefyyy", "300", "50 POs | 30 day limit");

        tearDown();
    }

    public static void setupDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://stage.loadsecuresystems.com/");
    }

    public static void login(String email, String password) {
        WebElement emailField = waitForElement(By.id(":r0:"), 10);
        emailField.sendKeys(email);

        WebElement passwordField = waitForElement(By.id(":r1:"), 10);
        passwordField.sendKeys(password);

        WebElement signInButton = waitForElement(By.xpath("//button[@type='submit']"), 10);
        signInButton.click();
    }

    public static void createNewCompany(String companyName, String breadcrumb, String packageOption) {
        openCompanySection();

        WebElement companyNameField = waitForElement(By.id("companyName"), 10);
        companyNameField.sendKeys(companyName);

        WebElement breadcrumbField = waitForElement(By.id("breadcrumb"), 10);
        breadcrumbField.sendKeys(breadcrumb);

        togglePaidButton();

        selectPackage(packageOption);

        assignAndAddCompany();
    }

    public static void openCompanySection() {
        WebElement companyButton = waitForElement(By.id("companies"), 10);
        companyButton.click();

        WebElement newCompanyButton = waitForElement(By.id("newCompanies"), 10);
        newCompanyButton.click();
    }

    public static void togglePaidButton() {
        WebElement paidToggleButton = driver.findElement(By.id("paid"));
        Actions actions = new Actions(driver);
        actions.moveToElement(paidToggleButton).click().perform();
    }

    public static void selectPackage(String packageOption) {
        WebElement packageElement = driver.findElement(By.id(packageOption));
        Actions actions = new Actions(driver);
        actions.moveToElement(packageElement).click().perform();
    }

    public static void assignAndAddCompany() {
        WebElement assignButton = waitForElement(By.id("assign"), 10);
        assignButton.click();

        WebElement addCompanyButton = waitForElement(By.id("addCompany"), 10);
        addCompanyButton.click();
    }

    public static WebElement waitForElement(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
