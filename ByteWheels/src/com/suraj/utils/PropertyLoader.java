package com.suraj.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * This class is responsible for loading the properties from the property file
 * @author suraj.udayashankar
 *
 */
public class PropertyLoader {
	static Logger log=Logger.getLogger(PropertyLoader.class);
	static Map<String,String> props=new HashMap<String,String>();
	private static String path=System.getProperty("user.home")+"/ByteWheels/properties.txt";
	
	
	private static void getProps(String path){
		File file=new File(path);
		BufferedReader br = null;
		try {
			br=new BufferedReader(new FileReader(file));
			String line="";
			while(null!=(line=br.readLine())){
				String[] propsLine = line.split("=");
				props.put(propsLine[0], propsLine[1]);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * This method returns the property value based on the key passed
	 * @param key
	 * @return
	 */
	public static String getProperty(String key){
		if(props.isEmpty()){
			getProps(path);
		}
		if(props.containsKey(key)){
			log.info("value of property "+key+" : "+props.get(key));
			return props.get(key);
		}else{
			log.error("No property defined for Key : "+key);
		}
		return null;
		
	}
	
	
}
