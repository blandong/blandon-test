package com.blandon.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Test {
	
	public static void main(String[] args) throws IOException {
		printRoutes();
	}
	
	private static void printRoutes() throws IOException {
		 InputStream is = Test.class.getResourceAsStream("/routes");
		 InputStreamReader isr = new InputStreamReader(is);
		 //FileReader fr = new FileReader("/Users/bdong/git/blandon-test/src/main/resources/routes");
		// BufferedReader br = new BufferedReader(fr);
		 BufferedReader br = new BufferedReader(isr);
		 String line;
		 StringBuilder routes = new StringBuilder();
	    while ((line = br.readLine()) != null ) {
	    		if(line.contains("https")) {
	    			String domain = line.substring(line.indexOf(".")+1);
	    			String app = domain.substring(0, domain.indexOf("."));
	    			String host = line.substring(line.lastIndexOf("/")+1, line.indexOf("."));
	    			String route = "cf map-route " + app + " "+ domain + " -n "+ host;
	    			routes.append(route).append("\n");
	    		}
	    }
	    is.close();
	    isr.close();
	    br.close();
	   System.out.println(routes.toString());
	}
}
