package com.qainfotech.Assignment_tatoc;


	import org.testng.annotations.Test;

	import static org.testng.Assert.assertEquals;

	import java.util.ArrayList;
	import java.io.IOException;
	import java.util.Properties;
	import java.util.concurrent.TimeUnit;

	import org.openqa.selenium.Cookie;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.interactions.Action;
	import org.openqa.selenium.interactions.Actions;
	import org.testng.annotations.BeforeTest;

	public class Basic {
		WebDriver driver;
		Properties prop;
		ReadObjects objects;
		UIOperation operation;
		String BASEURL = "http://10.0.1.86/tatoc/basic/grid/gate";

		@BeforeTest
		void initialize() {
			driver = Setup.chromesetup(driver);
			objects = new ReadObjects();
			operation = new UIOperation(driver);
		}

		@Test(priority = 1)
		void gridgate() throws IOException, InterruptedException {
			prop = objects.getproperties();
			operation.perform(prop, "gotourl", "", BASEURL);
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			operation.perform(prop, "click", "greenbox", "");
			String title = operation.gettitle();
			assertEquals(title, "Frame Dungeon - Basic Course - T.A.T.O.C");
		}

		@Test(priority = 2)
		void frame_dungeon() throws IOException, InterruptedException {
			prop = objects.getproperties();
			WebElement box1, box2;
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			driver.switchTo().frame("main");
			box1 = operation.getelement(prop, "box1");
			String box1class = box1.getAttribute("class");

			driver.switchTo().frame("child");
			box2 = operation.getelement(prop, "box2");
			String box2class = box2.getAttribute("class");

			while (!box1class.equals(box2class)) {
				driver.switchTo().defaultContent();
				driver.switchTo().frame("main");
				operation.perform(prop, "click", "repaint", "");
				driver.switchTo().frame("child");
				box2 = operation.getelement(prop, "box2");
				box2class = box2.getAttribute("class");
			}
			driver.switchTo().defaultContent();
			driver.switchTo().frame("main");
			operation.perform(prop, "click", "proceed", "");
			String title = operation.gettitle();
			assertEquals(title, "Drag - Basic Course - T.A.T.O.C");
		}
		
		@Test(priority=3)
		void dragaround() throws IOException, InterruptedException{
			WebElement drag,drop;
			Thread.sleep(2000);
			prop = objects.getproperties();
			drag=operation.getelement(prop,"dragme");
			drop=operation.getelement(prop, "dropbox");
			Actions builder = new Actions(driver);
			Action dragAndDrop = builder.clickAndHold(drag)
					.moveToElement(drop).release(drop).build();
			dragAndDrop.perform();
			operation.perform(prop, "click", "proceed", "");
			
			String title = operation.gettitle();
			assertEquals(title, "Windows - Basic Course - T.A.T.O.C");

		}
		
		@Test(priority=4)
		void launchpopup() throws IOException {
			prop = objects.getproperties();
			operation.perform(prop, "click", "popup", "");

			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(1));
			operation.perform(prop, "settext", "popuptext", "shubham");
			operation.perform(prop, "click", "popupsubmit", "");
			driver.switchTo().window(tabs2.get(0));

			operation.perform(prop, "click", "proceed", "");

			String title = operation.gettitle();
			assertEquals(title, "Cookie Handling - Basic Course - T.A.T.O.C");
		}
		
		@Test(priority=5)
		void cookiehandling() throws IOException {
			prop = objects.getproperties();
			operation.perform(prop, "click", "generatetoken", "");
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			String token=operation.gettextofelement(prop,"token");
			String token2=token.substring(7);
			Cookie cookie = new Cookie("Token",token2);
			driver.manage().addCookie(cookie);
			operation.perform(prop, "click", "proceed", "");
			
			String title = operation.gettitle();
			assertEquals(title, "End - T.A.T.O.C");
		}
	}



