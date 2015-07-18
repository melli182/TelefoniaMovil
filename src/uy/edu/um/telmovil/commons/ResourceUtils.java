package uy.edu.um.telmovil.commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceUtils {
	

	public static String GMSC_TO_MSC_SENDING = "gmsc.msc.sending";
	public static String GMSC_FROM_MSC_RECEIVING = "gmsc.msc.receiving";
	public static String GMSC_TO_HLR_SENDING = "gmsc.hlr.sending";
	public static String GMSC_FROM_HLR_RECEIVING = "gmsc.hlr.receiving";
	
	public static String MSC_TO_GMSC_SENDING = "msc.gmsc.sending";
	public static String MSC_FROM_GMSC_RECEIVING = "msc.gmsc.receiving";
	public static String MSC_TO_HLR_SENDING = "msc.hlr.sending";
	public static String MSC_FROM_HLR_RECEIVING = "msc.hlr.receiving";
	
	public static String HLR_TO_MSC_SENDING = "hlr.msc.sending";
	public static String HLR_FROM_MSC_RECEIVING = "hlr.msc.receiving";
	public static String HLR_TO_GMSC_SENDING = "hlr.gmsc.sending";
	public static String HLR_FROM_GMSC_RECEIVING = "hlr.gmsc.receiving";

	public static String HLR_HOST = "hlr.host";
	public static String GMSC_HOST = "gmsc.host";
	public static String MSC_HOST = "msc.host";

	

	
	
	private static String CONFIG_PROPERTIES_RESOURCE = "config.properties";
	
	private static Properties prop; 
	
	static {
		prop = new Properties();
    	InputStream input = null;
 
    	try {
 
    		String filename = CONFIG_PROPERTIES_RESOURCE;
    		input = ResourceUtils.class.getClassLoader().getResourceAsStream(filename);
    		if(input==null){
    	            System.out.println("Sorry, unable to find " + filename);
    		}
 
    		//load a properties file from class path, inside static method
    		prop.load(input);
    	} catch (IOException ex) {
    		ex.printStackTrace();
        } finally{
        	if(input!=null){
        		try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	}
        }
	
	}
	
	public static String obtenerProperty(String propertyName){
		return prop.getProperty(propertyName);
	}
}