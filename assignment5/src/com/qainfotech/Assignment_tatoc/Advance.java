package com.qainfotech.Assignment_tatoc;
import java.io.File;
import java.io.IOException;
import static com.jayway.restassured.RestAssured.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

public class Advance {
	WebDriver driver;
	Properties prop;
	ReadObjects objects;
	UIOperation operation;
	String BASEURL = "http://10.0.1.86/tatoc/advanced/hover/menu";

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
		String hover = operation.hovermenu();
		System.out.println(hover);
		//assertEquals(hover, "Frame Dungeon - Basic Course - T.A.T.O.C");
	}
	
	@Test(priority=2)
	void querygate() throws ClassNotFoundException, SQLException, IOException {
	     objects = new ReadObjects();
	     prop=objects.getproperties();
	     String symbol=operation.gettextofelement(prop,"symbol");
	     Connection connection;
	     String dbUrl="jdbc:mysql://10.0.1.86/tatoc";
	     String userName="tatocuser";
	     String password="tatoc01";
	     Class.forName("com.mysql.jdbc.Driver");
	     
	     connection = DriverManager.getConnection(dbUrl, userName, password);
	  Statement st = connection.createStatement();
	  
	  
	  String sqlStr = "select * from identity where symbol='"+symbol+"';";
	  ResultSet rs = st.executeQuery(sqlStr);
	  rs.next();
	  String id=rs.getString(1);
	  System.out.println(id);
	  String sqlstr2= "select * from credentials where id='"+id+"'";
	  rs = st.executeQuery(sqlstr2);
	  rs.next();
	  String name=rs.getString(2);
	  String pass=rs.getString(3);
	  operation.perform(prop,"settext","name",name);
	  operation.perform(prop,"settext","passkey",pass);
	  operation.perform(prop,"click","btnsubmit","");
	  //return driver.getTitle();
	    }
	
	@Test(priority=3)
	 void restful() throws IOException, InterruptedException {
  	  prop = objects.getproperties();
  	  operation.perform(prop, "gotourl", "", "http://10.0.1.86/tatoc/advanced/rest/#");
  	  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
  	  String sessionid = operation.gettextofelement(prop, "sessionid").substring(12);
  	  Response res = given().when().get("http://10.0.1.86/tatoc/advanced/rest/service/token/{sessionid}", sessionid);
  	  String token = res.path("token");
  	  given().contentType("application/json").when()
  	    .post("http://10.0.1.86/tatoc/advanced/rest/service/register?signature=" + token + "&id=" + sessionid
  	      + "&allow_access=1")
  	    .then().assertThat().statusCode(200);
  	  operation.perform(prop, "click", "proceed", "");
  	  Thread.sleep(2000);
  	 }
	
	@Test(priority=4)
	void filehandle() throws IOException, InterruptedException {
		  prop = objects.getproperties();
		  operation.perform(prop, "click", "download", "");
		  Thread.sleep(3000);
		  File file= new File("C:\\Users\\sumitmishra.QAIT\\workspace\\assignment5\\src\\file_handle_test.dat");
		  Scanner sc=new Scanner(file);
		  String data= null;
		  while(sc.hasNext()) {
		    data=sc.next();
		  }
		  sc.close();
		  operation.perform(prop, "settext", "sign", data);
		  operation.perform(prop, "click", "proc", "");
		  file.delete();
		 }
}
