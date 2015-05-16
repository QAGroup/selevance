package org.openqa.selevance;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * @author Tanmay
 *
 */
public class Plus extends GlobalExtn{
	private WebDriver driver;
	private Properties prop = new Properties();
	private InputStream input = null;
	String browserName ="";
	
	public Plus(String propPath){
		setBrowser(propPath);
	}	
	
	public WebDriver driver(){
		return this.driver ;
	}
	
	private void setBrowser(String propPath){
		try{
			if(propPath!=null){
				input = new FileInputStream(PROPFILEPATH+propPath);
			}else{
				input = new FileInputStream(PROPFILEPATH+PROPFILENAME);
			}
			prop.load(input);
			browserName = prop.getProperty("browser") ;
			String browserPath = prop.getProperty("browserPath") ;
			String grid = prop.getProperty("grid") ;
			if(browserPath!= null){
				this.driver = theStackBrowser(browserName,browserPath);
			}else if(grid!=null){
				this.driver = theGridBrowser(browserName,grid);
			}else{
				this.driver = theBrowser(browserName);
			}
			
		}catch(Exception ex){
			//ex.printStackTrace();
			System.out.println("Property File not Declared");
			try{
				System.out.println("Loading Data From VM args");
				browserName = System.getProperty("browser") ; 
				String browserPath = System.getProperty("browserPath") ;
				String grid = System.getProperty("grid") ;
				if(browserPath!= null){
					this.driver = theStackBrowser(browserName,browserPath);
				}else if(grid!=null){
					this.driver = theGridBrowser(browserName,grid);
				}else{
					this.driver = theBrowser(browserName);
				}
			}catch(Exception e){
				System.out.println("VM Args not specified");
				this.driver = null;
			}
			
		}
	}
	public String getBrowser(){
		return browserName;
	}
	private WebDriver theBrowser(String browserName){		
		if(browserName.toUpperCase().contains(Browser.List.FIREFOX.name())){
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			return driver;
		}else if(browserName.toUpperCase().contains(Browser.List.INTERNETEXPLORER.name())){
			System.setProperty("webdriver.ie.driver", PROPFILEPATH+IEDRIVER);
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			return driver;
		}else if(browserName.toUpperCase().contains(Browser.List.ANDROIDFF.name())){
			FirefoxProfile ffp = new FirefoxProfile();
			ffp.setPreference(ffagent, ANDROID403);
			driver = new FirefoxDriver(ffp);
			driver.manage().window().setPosition(new Point(0,0));
			driver.manage().window().setSize(new Dimension(400,768));
			return driver;
		}else if(browserName.toUpperCase().contains(Browser.List.SAFARI.name())){
			driver = new SafariDriver();
			driver.manage().window().maximize();
			return driver;
		}else if(browserName.toUpperCase().contains(Browser.List.OPERA.name())){
			driver = new OperaDriver();
			driver.manage().window().maximize();
			return driver;
		}else if(browserName.toUpperCase().contains(Browser.List.CHROME.name())){
			System.setProperty("webdriver.chrome.driver", PROPFILEPATH+ChromeDRIVER);
			WebDriver driver = new ChromeDriver();
			driver.manage().window().maximize();
			return driver;
		}else{
			return null;
		}
	}
	
	private WebDriver theStackBrowser(String browserName,String path){		
		if(browserName.toUpperCase().contains(Browser.List.FIREFOX.name())){
			FirefoxBinary fb = new FirefoxBinary(new File(path));
			FirefoxProfile ffp = new FirefoxProfile();
			driver = new FirefoxDriver(fb,ffp);
			driver.manage().window().maximize();
			return driver;
		}else if(browserName.toUpperCase().contains(Browser.List.INTERNETEXPLORER.name())){
			System.setProperty("webdriver.ie.driver", PROPFILEPATH+IEDRIVER);
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			return driver;
		}else if(browserName.toUpperCase().contains(Browser.List.ANDROIDFF.name())){
			FirefoxBinary fb = new FirefoxBinary(new File(path));
			FirefoxProfile ffp = new FirefoxProfile();
			ffp.setPreference(ffagent, ANDROID403);
			driver = new FirefoxDriver(fb,ffp);
			driver.manage().window().setPosition(new Point(0,0));
			driver.manage().window().setSize(new Dimension(400,768));
			return driver;
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("static-access")
	private WebDriver theGridBrowser(String browserName,String gridURL){		
		if(browserName.toUpperCase().contains(Browser.List.FIREFOX.name())){			
			try {
				DesiredCapabilities dcp = new DesiredCapabilities().firefox();
				driver = new RemoteWebDriver(new URL(gridURL),dcp);
				driver.manage().window().maximize();
				return driver;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
			
		}else if(browserName.toUpperCase().contains(Browser.List.INTERNETEXPLORER.name())){
			try {
				//System.setProperty("webdriver.ie.driver", PROPFILEPATH+IEDRIVER);
				DesiredCapabilities dcp = new DesiredCapabilities().internetExplorer();
				driver = new RemoteWebDriver(new URL(gridURL),dcp);
				driver.manage().window().maximize();
				return driver;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}			
		}else if(browserName.toUpperCase().contains(Browser.List.ANDROIDFF.name())){
			try {
				DesiredCapabilities dcp = new DesiredCapabilities().firefox();
				FirefoxProfile ffp = new FirefoxProfile();
				ffp.setPreference(ffagent, ANDROID403);					
				dcp.setCapability(FirefoxDriver.PROFILE, ffp);
				driver = new RemoteWebDriver(new URL(gridURL),dcp);
				driver.manage().window().setPosition(new Point(0,0));
				driver.manage().window().setSize(new Dimension(400,768));
				return driver;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		}else{
			return null;
		}
	}

	public void quit() {
		this.driver.quit();		
	}
}
