package com.perfecto.BaseCases;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResult;
import com.perfecto.reportium.test.result.TestResultFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class HomePageNewsParent {
	public RemoteWebDriver driver;
	public ReportiumClient reportiumClient;
	public Properties prop = new Properties();
	public String PerfectoCloud,PerfectoToken,AppLocation;
	public String Device,NameDevice, PackageName, ActivityName, BrowserActivity;
	public int OSVersion,DeviceCount;
	public Actions ac;
	public Dimension dimension,dimension1;
    public int x,y,Starty,Endy,Startx,Endx;
    public AndroidTouchAction atc;
	public File fs;
	public FileInputStream fis;
	public XSSFWorkbook wb;
	public XSSFSheet sh,sh1;
	public ApplicationLocators AL;
	public TestResult testResult = null;
		
	
	@Parameters({"SelecteDevice"})
	@BeforeSuite
	public void TearUp( String SelecteDevice) throws Throwable{
		SelectBrowser(SelecteDevice);
		ValueSet();
		InitializationProcess();
		InstallLaunchApplication();
		String ReportName = "AutomationReport_HomePageNews";
		reportiumClient = Utils.setReportiumClient(driver, reportiumClient);
		reportiumClient.testStart(ReportName, new TestContext("tag2"));
		AL = new ApplicationLocators(driver);
		ac = new Actions(driver);
		atc = new AndroidTouchAction((PerformsTouchActions) driver); 
		ChooserTheme();		
		AlertAccept();
	}
	
	public void ValueSet() throws Throwable {
		InputStream input = new FileInputStream("./GlobalProperties/global.properties");
        prop.load(input);
        
        PerfectoCloud = prop.getProperty("Perfecto_CloudName");
        PerfectoToken = prop.getProperty("Perfecto_SecurityToken");
        AppLocation   = prop.getProperty("Application_Path");
        DeviceCount = Integer.parseInt(prop.getProperty("IterationCount"));
	}
	
	public void InitializationProcess() throws Throwable {
		fs = new File("./libs/Mobile_App_DataProvider.xlsx");
		fis = new FileInputStream(fs);
        wb = new XSSFWorkbook(fis);
        sh = wb.getSheet("FixedData");//Change Name to "SingleData" and it should run on 1 devices
        sh1= wb.getSheet("HomePageData");
        
        for(int i=1;i<=DeviceCount;i++)
		{	
		 //Device =  sh.getRow(i).getCell(0).getStringCellValue();
		 //System.out.println(Device);
		 //OSVersion = (int) sh.getRow(i).getCell(1).getNumericCellValue();
		 PackageName = sh1.getRow(1).getCell(0).getStringCellValue();
		 ActivityName = sh1.getRow(1).getCell(1).getStringCellValue();
		 BrowserActivity = sh1.getRow(1).getCell(2).getStringCellValue();
		}
	}
	
	public void InstallLaunchApplication() throws Exception {
		
		String cloudName = PerfectoCloud;
		String securityToken = PerfectoToken;
		String browserName = "mobileOS";
		DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
		capabilities.setCapability("securityToken", Utils.fetchSecurityToken(securityToken));
		capabilities.setCapability("autoLaunch",true); 
		capabilities.setCapability("fullReset",true);
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", String.valueOf(OSVersion));
		capabilities.setCapability("model", Device);
		capabilities.setCapability("deviceName", NameDevice);
		capabilities.setCapability("enableAppiumBehavior", true);
		capabilities.setCapability("openDeviceTimeout", 2);
		capabilities.setCapability("app", AppLocation);
		capabilities.setCapability("appPackage", PackageName);
		capabilities.setCapability("appActivity", ActivityName);
		Thread.sleep(8000);
		
		try{
			driver = (RemoteWebDriver)(new AppiumDriver<>(new URL("https://" + Utils.fetchCloudName(cloudName)  + ".perfectomobile.com/nexperience/perfectomobile/wd/hub"), capabilities)); 
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);			
		}catch(SessionNotCreatedException e){
			throw new RuntimeException("Driver not created with capabilities: " + capabilities.toString());
		}
	}
	
	public void ChooserTheme() throws Throwable{
		reportiumClient.stepStart("Select Theme.");
		Thread.sleep(1500);
		driver.findElement(By.id("nextBtn")).click();
		Thread.sleep(4000);
		try {
		if(Device.equals("RAZR")) {
			ChooserScroll("up");
		}else if(Device.equalsIgnoreCase("V40")){
			Thread.sleep(5000);
		}
		}catch (Exception e) {
			System.out.println("Start Testing From Here......");
		}
		List<WebElement> ThemeSelector = AL.ThemeOptions;
		int ThemeCount = ThemeSelector.size();
		for(int i=0;i<ThemeCount;i++) {
			WebElement SelectFlavor = ThemeSelector.get(i);
			String Flavor = SelectFlavor.getAttribute("text");
			if(Flavor.contains("HomePage News")) {
				SelectFlavor.click();
				Thread.sleep(500);
				System.out.println("HomePage News Theme Selected.");
				break;
			}else {
				System.out.println("Wrong Flavor added.");
			}
		}
		try {
			if(AL.FinalThemeSelection.isDisplayed()) {
		AL.FinalThemeSelection.click();
			}else {
				System.out.println("Theme is Not Selected as Always in Use.");
			}
		}catch (Exception e) {
			System.out.println("Final Theme Selected Automatically.");
		}
		Thread.sleep(2000);
		reportiumClient.stepEnd();
	} 
	
	public void AlertAccept() throws Throwable{
		reportiumClient.stepStart("Go To HomePage Launcher");
		Thread.sleep(4000);
		AL.NotchSelection.click();
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
		Thread.sleep(1000);
		reportiumClient.stepEnd();
	}
	
	@SuppressWarnings("static-access")
	@AfterMethod
	public void StepDown(ITestResult result) {
		if(result.getStatus() == result.SUCCESS) {
			testResult = TestResultFactory.createSuccess();
		}
		else if (result.getStatus() == result.FAILURE) {
			testResult = TestResultFactory.createFailure(result.getThrowable());
		}
		  
	}
	
	public void ScrollSystem(String direction,int k)throws Throwable{
        for (int i=0;i<=k;i++) {
            x = (int) (dimension.getWidth()/2);
            Starty=0;
            Endy=0;

            switch (direction){
                case "up":
                    Starty = (int) (dimension.getHeight()*0.8);
                    Endy = (int) (dimension.getHeight()*0.2);
                    break;
                case "down":
                    Starty = (int) (dimension.getHeight()*0.2);
                    Endy = (int) (dimension.getHeight()*0.8);
                    break;
            }
            atc.press(PointOption.point(x,Starty)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(x,Endy)).release().perform();
        }
        }
	
	public void ChooserScroll(String dir)throws Throwable{
		dimension1 = AL.ChooserDimension.getSize();
		Thread.sleep(1500);
		
		for (int i=0;i<=1;i++) {
            x = (int) (dimension1.getWidth()/2);
            Starty=0;
            Endy=0;

            switch (dir){
                case "up":
                    Starty = (int) (dimension1.getHeight()*0.8);
                    Endy = (int) (dimension1.getHeight()*0.2);
                    break;
                case "down":
                    Starty = (int) (dimension1.getHeight()*0.2);
                    Endy = (int) (dimension1.getHeight()*0.8);
                    break;
            }
            atc.press(PointOption.point(x,Starty)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(x,Endy)).release().perform();
        }
		
	}
	
	public void SwipeSystem(String direction,int k)throws Throwable{
        for (int i=0;i<=k;i++) {
            x = (int) (dimension.getHeight()/2);
            Startx=0;
            Endx=0;

            switch (direction){
                case "right":
                    Startx = (int) (dimension.getWidth()*0.2);
                    Endx = (int) (dimension.getWidth()*0.8);
                    break;
                case "left":
                    Startx = (int) (dimension.getWidth()*0.8);
                    Endx = (int) (dimension.getWidth()*0.2);
                    break; 
            }
            atc.press(PointOption.point(x,Startx)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(x,Endx)).release().perform();
        }
        }
	
	public  void SelectBrowser(String SelecteDevice){
	    switch(SelecteDevice){

	    case "S8": 	
	    	NameDevice="988E90394A354B5751";
	    	OSVersion=9;
	        break;

	    case "s9+":
	    	Device="Galaxy S9+";
	    	OSVersion=10;
	        break;
	        
	    case "pixel2": 	
	    	Device="Pixel 2";
	    	OSVersion=11;
	        break;

	    case "pixel3":
	    	Device="Pixel 3";
	    	OSVersion=11;
	        break;
	        
	    case "pixel4": 	
	    	Device="Pixel 4";
	    	OSVersion=11;
	        break;

	    case "pixel4xl":
	    	Device="Pixel 4 XL";
	    	OSVersion=11;
	        break;
	        
	    case "lgg8": 	
	    	Device="G8X ThinQ";
	    	OSVersion=10;
	        break;

	    case "v40":
	    	Device="V40";
	    	OSVersion=9;
	        break;
	        
	    case "g6": 	
	    	NameDevice="ZY323FGV4X";
	    	OSVersion=9;
	        break;

	    case "e6":
	    	Device="Moto E6";
	    	OSVersion=9;
	        break;
	        
	    case "oneaction": 	
	    	Device="One Action";
	    	OSVersion=9;
	        break;

	    case "razr":
	    	Device="RAZR";
	    	OSVersion=10;
	        break;
	        
	    case "motoz4": 	
	    	NameDevice="ZY2269C2KW";
	    	OSVersion=9;
	        break;

	    case "s20":
	    	NameDevice="RFCN104T9HA";
	    	OSVersion=10;
	        break;
	        
	    case "a10": 	
	    	Device="Galaxy A10e";
	    	OSVersion=10;
	        break;

	    case "a20":
	    	NameDevice="R58MB3PRYKF";
	    	OSVersion=10;
	        break;
	        
	    case "j3": 	
	    	Device="Galaxy J3 Prime";
	    	OSVersion=9;
	        break;

	    case "s9":
	    	NameDevice="2C6290161F037ECE";
	    	OSVersion=10;
	        break;

	    default: 
	        System.out.println("Wrong input");      
	    }

	}
		
	
	@AfterSuite
	public void TearDown() {
		
		  reportiumClient.testStop(testResult);
		  driver.close();
		  driver.quit();
		  String reportURL = reportiumClient.getReportUrl();
		  System.out.println("URL For Report:- "+reportURL);
	}	
	
}



