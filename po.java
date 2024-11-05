package PO;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class po {
    static WebDriver driver;

    public static void main(String[] args) {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://stage.loadsecuresystems.com/");

        login("danish.rehman08@gmail.com", "123456");
        enterShipmentInfo("devdefy", "Hassan Akhtar - hakhtar@devdefy.com", "223344", "CPO-Test1", "+1 (208) 581-0607", "Sayyam");
        
        clickOpenButton();
    }

    private static void login(String email, String password) {
        WebElement emailField = waitForElement(By.id(":r0:"), 10);
        emailField.sendKeys(email);

        WebElement passwordField = waitForElement(By.id(":r1:"), 10);
        passwordField.sendKeys(password);

        WebElement signInButton = waitForElement(By.xpath("//button[@type='submit']"), 10);
        signInButton.click();
    }

    private static void enterShipmentInfo(String companyName, String user, String po, String cpo, String driverPhone, String carrier) {
        clickOrderButton();
        clickNewOrderButton();
        selectCompany(companyName);
        enterUserDetails(user);
        enterPONumber(po);
        enterCPO(cpo);
        selectPhone(driverPhone);
        enterCarrier(carrier);
        clickNext();
    }

    private static void clickOrderButton() {
        WebElement orderButton = waitForElementToBeClickable(By.id("orders"), 30);
        orderButton.click();
    }

    private static void clickNewOrderButton() {
        WebElement newPOButton = waitForElementToBeClickable(By.id("newPo"), 30);
        newPOButton.click();
    }
    
    private static void clickNext() {
        WebElement NxtButton = waitForElementToBeClickable(By.id("next"), 30);
        NxtButton.click();
    }
    
    

    private static void selectCompany(String companyName) {
        WebElement companyNameField = waitForElementToBeClickable(By.id("searchCompany"), 10);
        companyNameField.click();
        companyNameField.clear();
        companyNameField.sendKeys(companyName);

        waitForElement(By.xpath("//li[contains(text(),'" + companyName + "')]"), 10).click();
        System.out.println("Company option selected: " + companyName);
    }

    private static void enterUserDetails(String user) {
        WebElement userField = waitForElement(By.id("searchUser"), 10);
        userField.sendKeys(user);
    }

    private static void enterPONumber(String po) {
        WebElement poField = waitForElement(By.id("poNumber"), 10);
        poField.sendKeys(po);
    }

    private static void enterCPO(String cpo) {
        WebElement cpoField = waitForElement(By.id("customerPoNumber"), 10);
        cpoField.sendKeys(cpo);
    }

    private static void selectPhone(String driverPhone) {
        WebElement phoneField = waitForElementToBeClickable(By.id("searchDriver"), 10);
        phoneField.click();
        phoneField.clear();
        phoneField.sendKeys(driverPhone);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("MuiAutocomplete-popper"))); // Wait until dropdown appears

        WebElement optionsList = driver.findElement(By.className("MuiAutocomplete-popper"));
        List<WebElement> options = optionsList.findElements(By.tagName("li"));

        for (WebElement option : options) {
            if (option.getText().contains(driverPhone)) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                System.out.println("Phone number option selected: " + driverPhone);
                return;
            }
        }

        System.out.println("Phone number option not found: " + driverPhone);
    }

    private static void enterCarrier(String carrier) {
        WebElement carrierField = waitForElement(By.id("carrier"), 10);
        carrierField.sendKeys(carrier);
    }

    private static void clickOpenButton() {
        WebElement openButton = waitForElementToBeClickable(By.xpath("//div[@class='MuiStack-root mui-nzvokd']//button[@title='Open']//*[name()='svg']//*[name()='path' and contains(@d,'M7 10l5 5 ')]"), 10);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", openButton);
        System.out.println("Open button clicked successfully.");
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