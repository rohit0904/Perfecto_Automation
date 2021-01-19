package com.perfecto.BaseCases;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ApplicationLocators {
	public WebDriver wd;
	
	@FindBy(xpath = "//*[@resource-id='android:id/button_always']")
	public WebElement FinalThemeSelection;
	
	@FindBy(xpath = "//*[@resource-id='android:id/text1']")
	public List<WebElement> ThemeOptions;
	
	@FindBy(id="fl_container_go_to_fb")
	public WebElement NotchSelection;
	
	@FindBy(id="searchView")
	 public WebElement SearchView;
	 
	@FindBy(xpath = "//*[@resource-id='com.homepage.news.android:id/search']")
	 public WebElement SearchButton;
	 
	@FindBy(id="rvHotTopics")
	 public WebElement HotTopics;
	 
	@FindBy(xpath="//*[@resource-id='com.homepage.news.android:id/btnSettings']")
	 public WebElement SettingsOption;
	 
	@FindBy(xpath="//*[@text='Manage Interests']")
	 public WebElement FirstSettingOption;
	 
	@FindBy(id="btnToggleDrawer")
	 public WebElement HumburgerMenu;
	 
	@FindBy(xpath="//*[@text='Weather']")
	 public WebElement WeatherScreen;
	 
	@FindBy(xpath="//*[@text='Business']")
	 public WebElement BusinessScreen;
	 
	@FindBy(xpath="//*[@text='Entertainment']")
	 public WebElement EntertainmentScreen;
	 
	@FindBy(xpath="//*[@text='Videos']")
	 public WebElement VideosScreen;
	 
	@FindBy(xpath="//*[@text='News']")
	 public WebElement NewsScreen;
	 
	@FindBy(id="tvProviderName")
	 public List<WebElement> TvProviers;
	 
	@FindBy(id="viewpager")
	 public WebElement ProviderDimension;
	
	@FindBy(xpath="//*[@resource-id = 'android:id/contentPanel']")
	public WebElement ChooserDimension;
	 
	@FindBy(id="cl_weather_info")
	 public WebElement WeatherInformation;
	 
	@FindBy(id="rv_hourly_forecast")
	 public WebElement ForecastInformationHourly;
	 
	@FindBy(id="rv_daily_forecast")
	 public WebElement ForecastInformationDaily;
	 
	@FindBy(id="tv_news_category")
	 public List<WebElement> NewsCategoryCount;
	 
	@FindBy(id="tv_news_category")
	 public WebElement NewsCategoryInfo;
	 
	@FindBy(id="news_list")
	 public WebElement NewsTabDimension;
	 
	@FindBy(xpath="//*[@text='Use HomePage News as Home']")
	 public WebElement AppChooser;
	 
	@FindBy(id="button_always")
	 public WebElement FinalChooser;
	 
	@FindBy(id="btn_done")
	 public WebElement Done_Button; 
	 
	@FindBy(xpath ="//*[@resource-id = 'com.homepage.news.android:id/ivRemove']")
	 public List<WebElement> Remove_Provider;
	
	@FindBy(xpath="//*[@text='My Sources']")
	 public WebElement Selected_Provider;
	
	public ApplicationLocators(WebDriver rwd) {
        this.wd = rwd;
        PageFactory.initElements(rwd,this);
    }
}