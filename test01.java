package automation1;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.openqa.selenium.WebDriver;

public class test01 {

	public static void main(String[] args) {
		//ChromeDriver driver=new ChromeDriver();
		WebDriver driver =new ChromeDriver();
		driver.manage().window().maximize();	
		
		driver.get("https://stage.loadsecuresystems.com/");
		
			
		
		
		String act_title=driver.getTitle();
		
	    
		if(act_title.equals("Load Secure"))
		{
			System.out.println("Test Passed");
		}
		else 
		{
			System.out.println("Test Failed");
		}
		
		driver.quit();
		
		

	}

}
