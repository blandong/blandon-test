package com.blandon.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
		 
		StringBuilder unmapRoutes = new StringBuilder();
	    while ((line = br.readLine()) != null ) {
	    		if(line.contains("https") && !line.contains("broker")) {
	    			String domain = line.substring(line.indexOf(".")+1);
	    			String app = domain.substring(0, domain.indexOf("."));
	    			String host = line.substring(line.lastIndexOf("/")+1, line.indexOf("."));
	    			String route = "cf map-route " + app + "-blue     "+ domain + " -n "+ host;
	    			String unMaproute = "cf unmap-route " + app + " "+ domain + " -n "+ host;
	    			routes.append(route).append("\n");
	    			unmapRoutes.append(unMaproute).append("\n");
	    		}
	    }
	    is.close();
	    isr.close();
	    br.close();
	    
	   //File file = new File(Test.class.getResource("/shellRoutes.sh").getPath());
	   File file = new File("/Users/bdong/git/blandon-test/src/main/resources/shellRoutes.sh");
	   if (!file.exists()) {
		     file.createNewFile();
		  }
	   FileWriter fileWriter = new FileWriter(file);
	   
	   BufferedWriter bw = new BufferedWriter(fileWriter);
	   bw.write("#!/bin/bash");
	   bw.write("\n\n\n");
	   bw.write(routes.toString());
	   bw.write("\n\n\n");
	   bw.write(unmapRoutes.toString());
	  
	   bw.flush();
	   bw.close();
	    
	   System.out.println(routes.toString());
	   System.out.println(); System.out.println();
	   System.out.println("=====================================");
	   System.out.println(); System.out.println();
	   System.out.println(); System.out.println();
	   System.out.println(unmapRoutes.toString());
	}
}
