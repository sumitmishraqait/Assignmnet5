package com.qainfotech.Assignment_tatoc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadObjects {

	Properties p = new Properties();
	public Properties getproperties() throws IOException {
		InputStream inputStream=new FileInputStream(new File("C:\\Users\\sumitmishra.QAIT\\workspace\\assignment5\\src\\object.properties"));
		p.load(inputStream);
		return p;
	}
}
