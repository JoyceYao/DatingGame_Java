package game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyService {
	InputStream inputStream;
	Properties prop;
	
	public PropertyService(String propFileName){
		try{
			prop = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try{
				inputStream.close();
			}catch (Exception e){
				System.out.println("Exception: " + e);
			}
		}
	}
	 
	public String getPropValues(String propName) {
		System.out.println("prop=" + prop);
		System.out.println("prop=" + prop);
		return prop.getProperty(propName);
	}
}