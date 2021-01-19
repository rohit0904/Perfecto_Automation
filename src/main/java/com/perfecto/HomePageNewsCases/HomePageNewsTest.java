package com.perfecto.HomePageNewsCases;

import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.google.common.base.Verify;
import com.perfecto.BaseCases.HomePageNewsParent;

public class HomePageNewsTest  extends HomePageNewsParent {
	
	@Test(description ="Verify that all the Elements are prensent on the screen.")
	public void FirstCase() throws Throwable{
		
		reportiumClient.stepStart("Verify Sanity Cases for Build Acceptance.");
		WebElement SearchOption = AL.SearchView;
		Verify.verify(SearchOption.isDisplayed());
		
		WebElement HotTopics = AL.HotTopics;
		Verify.verify(HotTopics.isDisplayed());
		
		WebElement SettingsOptions = AL.SettingsOption;
		Verify.verify(SettingsOptions.isDisplayed());
		
		WebElement MenuItem = AL.HumburgerMenu;
		Verify.verify(MenuItem.isDisplayed());
		
		WebElement WeatherOption = AL.WeatherScreen;
		Verify.verify(WeatherOption.isDisplayed());
		
		WebElement BusinessOption = AL.BusinessScreen;
		Verify.verify(BusinessOption.isDisplayed());
		
		WebElement EntertainmentOption = AL.EntertainmentScreen;
		Verify.verify(EntertainmentOption.isDisplayed());
		
		WebElement VideosOption = AL.VideosScreen;
		Verify.verify(VideosOption.isDisplayed());
		
		WebElement NewsOption = AL.NewsScreen;
		Verify.verify(NewsOption.isDisplayed());
		reportiumClient.stepEnd();
	}
	
	@Test(description = "Verify that elements are working properly.", dependsOnMethods = "FirstCase")
	public void OptionsWorking() throws Throwable{
			
		reportiumClient.stepStart("Verify Mandatory Fields are working.");
		Thread.sleep(2000);
		AL.WeatherScreen.click();
		Thread.sleep(1000);
		AL.BusinessScreen.click();
		Thread.sleep(1000);
		AL.EntertainmentScreen.click();
		Thread.sleep(1000);
		AL.VideosScreen.click();
		Thread.sleep(1000);
		AL.NewsScreen.click();
		Thread.sleep(1000);
		reportiumClient.stepEnd();
		
	}
		
	
	@Test(description = "Verify that User is able to search anything.", dependsOnMethods = "OptionsWorking")
	public void SearchTest() throws Throwable{
		reportiumClient.stepStart("Verify Search Feild is working.");
		WebElement searchTab=AL.SearchView;
		Thread.sleep(2000);
		searchTab.click();
		Thread.sleep(2000);
		WebElement searchTab1=AL.SearchButton;
		Thread.sleep(2000);
		//ac.moveToElement(searchTab1).sendKeys("Mumbai",Keys.ENTER).build().perform();
		searchTab1.click();
		Thread.sleep(1000);
		ac.sendKeys("Mumbai");
		ac.sendKeys(Keys.ENTER);
		ac.build().perform();
		Thread.sleep(2000);
		driver.navigate().back();
		Thread.sleep(1500);
		driver.navigate().back();
		Thread.sleep(2000);
		reportiumClient.stepEnd();
	}
	
	@Test(description = "Verify that settings options are working.", dependsOnMethods = "SearchTest")
	public void SettingsTest() throws Throwable{
		reportiumClient.stepStart("Verify Setting options are working.");
		AL.SettingsOption.click();
		Thread.sleep(3000);
		AL.FirstSettingOption.click();
		Thread.sleep(2000);
		driver.navigate().back();
		Thread.sleep(2000);
		List<WebElement> IntrestPart = AL.TvProviers;
		//System.out.println("TOTAL INTEREST OPTIONS:-"+IntrestPart.size());
		dimension = AL.ProviderDimension.getSize();
		//System.out.println("TOTAL INTEREST OPTIONS:-"+apmdriver.getPageSource());
		ScrollSystem("up",1);
		Thread.sleep(1000);
		//System.out.println("TOTAL INTEREST OPTIONS:-"+IntrestPart.size());
		Thread.sleep(2000);
		reportiumClient.stepEnd();
	}
	
	@Test(description = "Select News Resource from the List.",dependsOnMethods = "SettingsTest")
	public void NewsResourceSelection()throws Throwable{
		reportiumClient.stepStart("Verify resources are removed properly.");
		List<WebElement> ProviderNames = AL.TvProviers;
		for(int i=0;i<2;i++) {
			ProviderNames.get(i).click();
			Thread.sleep(500);
		}
		Thread.sleep(1000);
		AL.Done_Button.click();
		Thread.sleep(4000);
		AL.SettingsOption.click();
		Thread.sleep(1000);
		AL.FirstSettingOption.click();
		Thread.sleep(1000);
		//SwipeSystem("left", 1);
		Thread.sleep(1000);
		AL.Selected_Provider.click();
		Thread.sleep(1000);
		
		List<WebElement> ProviderRemove = AL.Remove_Provider;
		for(int i=0;i<ProviderRemove.size();i++) {
			WebElement LocalProvider = ProviderRemove.get(i);
			Thread.sleep(500);
			LocalProvider.click();
		}
		Thread.sleep(1000);
		AL.Done_Button.click();
		Thread.sleep(2000);
		reportiumClient.stepEnd();
	}

}


