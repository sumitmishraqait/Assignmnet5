package com.qainfotech.Assignment_tatoc;



	import java.io.IOException;
	import java.util.Properties;
	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.interactions.Action;
	import org.openqa.selenium.interactions.Actions;
	public class UIOperation {
	    WebDriver driver;
		private ReadObjects objects;
		Properties prop;
		
		
		
		
	    public UIOperation(WebDriver driver){
	        this.driver = driver;
	    }
	    
	    
	    public void perform(Properties p,String operation,String objectName,String value){
	        System.out.println("");
	        switch (operation.toUpperCase()) {
	        case "CLICK":
	            //Perform click
	            driver.findElement(By.xpath(p.getProperty(objectName))).click();
	            break;
	        case "SETTEXT":
	            //Set text on control
	            driver.findElement(By.xpath(p.getProperty(objectName))).sendKeys(value);
	            break;
	            
	        case "GOTOURL":
	            //Get url of application
	            driver.get(value);
	            break;
	        case "GETTEXT":
	            //Get text of an element
	            driver.findElement(By.xpath(p.getProperty(objectName))).getText();
	            break;
	        default:
	            break;
	        }
	    }
	    
	    String gettitle() {
	    	return driver.getTitle();
	    }
	    
	    WebElement getelement(Properties p,String objectName) {
	    	return driver.findElement(By.xpath(p.getProperty(objectName)));
	    }
	    
	    String gettextofelement(Properties p,String objectName) {
	    	return driver.findElement(By.xpath(p.getProperty(objectName))).getText();
	    }
	    
	    String hovermenu() throws IOException {
	    	objects = new ReadObjects();
	    	prop=objects.getproperties();
	    	WebElement menu=getelement(prop,"menu2");
	    	Actions action=new Actions(driver);
	    	Action hover=action.moveToElement(menu).build();
	    	hover.perform();
	    	perform(prop,"click","gonext", "");
			return gettitle();
	    }
	}




