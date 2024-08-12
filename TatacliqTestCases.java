package week4.day5;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TatacliqTestCases {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		
		//code for handle notification
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--disable-notification");
				
		//Initiate the Chrome browser
		ChromeDriver objTataBrw = new ChromeDriver(option);
		objTataBrw.manage().window().maximize();
		
		//1. Load the url as 
		objTataBrw.get("https://www.tatacliq.com/");
		objTataBrw.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
		
		//Handle sweet popup
		objTataBrw.findElement(By.xpath("//button[@id='wzrk-cancel']")).click();//--added "disable-notification" initially
				
		//2. MouseHover on 'Brands'
		WebElement objBrands = objTataBrw.findElement(By.xpath("(//div[@class='DesktopHeader__categoryAndBrand'])[2]"));
		new Actions (objTataBrw).moveToElement(objBrands).perform();
		objBrands.click();
		
		//3. MouseHover on 'Watches & Accessories'
		WebElement objWatch = objTataBrw.findElement(By.xpath("//div[text ()='Watches & Accessories']"));
		new Actions (objTataBrw).moveToElement(objBrands).perform();
		objWatch.click();
		
		//4. Choose the first option from the 'Featured brands'.
		WebElement objModel = objTataBrw.findElement(By.xpath("//div[text ()='Casio']"));
		new Actions (objTataBrw).moveToElement(objModel).perform();
		objModel.click();
		
		Thread.sleep(3000);
		
		//5. Select sortby: New Arrivals
		WebElement objSortBy = objTataBrw.findElement(By.xpath("//select[@class='SelectBoxDesktop__hideSelect']"));
		Select dataNew = new Select(objSortBy);
		dataNew.selectByVisibleText("New Arrivals");
		
		//6. choose men from categories filter.
		objTataBrw.findElement(By.xpath("//div[text()='Men']")).click();
		Thread.sleep(3000);
		
		//7. print all price of watches
		System.out.println("Print all the price of listed watches");
		List<WebElement> allWatchPrices = objTataBrw.findElements(By.xpath("//div[@class='ProductDescription__priceHolder']/h3"));
		for (int i=0;i<allWatchPrices.size();i++) {
			System.out.println(allWatchPrices.get(i).getText());
		}		
			
		//8. click on the first resulting watch.
		String sPriceO = objTataBrw.findElement(By.xpath("(//div[@class='ProductDescription__priceHolder']/h3)[1]")).getText();
		System.out.println("Price for the selected product - "+sPriceO +",");
				
		//objTataBrw.findElement(By.xpath("(//div[@class='ProductDescription__priceHolder']/h3)[1]"));
		WebElement objFModel = objTataBrw.findElement(By.xpath("(//div[@class='ProductDescription__content'])[1]"));
		objTataBrw.executeScript("arguments[0].click();", objFModel);//Java Method
		//objFModel.click();
		//new Actions (objTataBrw).click(objFModel).perform();
		//objFModel.click();
		Thread.sleep(3000);	
		
		//9. Handle new window
		Set <String> objMultiWin = objTataBrw.getWindowHandles();
		List <String> objMultiBrw = new ArrayList <String>(objMultiWin);
		objTataBrw.switchTo().window(objMultiBrw.get(1));
		
		//9. compare two price are similar
		String sPricee = objTataBrw.findElement(By.xpath("//div[@class='ProductDetailsMainCard__price']")).getText();
		String[] priceArr = sPricee.split(" ");
		String sPriceN = priceArr[1].toString();
		System.out.println("Price for the selected product before add to bag -"+sPriceN);
		
		if (sPriceO.equals(sPriceN)) {
			System.out.println("Both Prices are same");
		}
				
		//10. click Add to cart and get count from the cart icon.
		objTataBrw.findElement(By.xpath("//span[text()='ADD TO BAG']")).click();
				
		//11. Click on the cart
		objTataBrw.findElement(By.xpath("//span[@class='DesktopHeader__cartCount']")).click();
		Thread.sleep(3000);	
		
		//12. Take a snap of the resulting page. 
		File takeSnapS = objTataBrw.getScreenshotAs(OutputType.FILE);
		File snapFPath = new File("./SnapFolder/Tataclip.png");
		FileUtils.copyFile(takeSnapS, snapFPath);
		
		//13. Close All the opened windows one by one.
		//close current window
		objTataBrw.close();
		
		//Switch back to Parent window and close
		objTataBrw.switchTo().window(objMultiBrw.get(0));
		objTataBrw.close();
				
	}

}
