package com.blandon.test.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;

public class IOTest {
	public static void main(String[] args) throws IOException, URISyntaxException {
		
		//bufferedReader();
		
		//apacheIOUtils();
		
		//apacheIOUtilsToString();
		
		readInputStream();
	}
	
	private static void readFileIntoByteArray() throws IOException {
		//Read file into input stream.
		InputStream is = IOTest.class.getClassLoader().getResourceAsStream("testFile.txt");
		
		//input stream to byte array with apache commons-io
		byte[] bytes = IOUtils.toByteArray(is);
		
		//input stream to byte array with Guava.
		byte[] eventBytes = ByteStreams.toByteArray(is);
		
		//byte array to string
		String byteArrayString = new String(bytes);
		
		
	}
	
	private static void bufferedReader() throws URISyntaxException, IOException{
		BufferedReader bd = null;
		try{
			
			URI uri = IOTest.class.getClassLoader().getResource("testFile.txt").toURI();
			
			File file = new File(uri);
			
			InputStream is = new FileInputStream(file);
			
			InputStreamReader isr = new InputStreamReader(is);
			
			bd = new BufferedReader(isr);	
			
			StringBuilder sb = new StringBuilder();
			
			String line = null;
			
			while((line =bd.readLine()) != null){
				sb.append(line).append("\n");
			}
			
			System.out.println(sb.toString());
		}finally{
			if(bd != null){
				bd.close();
			}
		}
	}
	
	////Method 1 IOUtils.copy()
	private static void apacheIOUtils() throws FileNotFoundException, IOException, URISyntaxException{
		
		URI uri = IOTest.class.getClassLoader().getResource("testFile.txt").toURI();
		
		File file = new File(uri);
		
        StringWriter writer = new StringWriter();
        
        //Method 1 IOUtils.copy()
        IOUtils.copy(new FileInputStream(file), writer, "UTF-8");
        
        String theString = writer.toString();
        
        System.out.println(theString);
       
	}
	
	////Method 2 IOUtils.toString()
	private static void apacheIOUtilsToString() throws URISyntaxException, FileNotFoundException, IOException{
		
		URI uri = IOTest.class.getClassLoader().getResource("testFile.txt").toURI();
		File file = new File(uri);
		
		String theString2 = IOUtils.toString(new FileInputStream(file), "UTF-8");
		
        System.out.println(theString2);
	}
	
	//use google guawa
	private static void readInputStream() throws URISyntaxException, UnsupportedEncodingException, IOException{
		
		try{
			URI uri = IOTest.class.getClassLoader().getResource("testFile.txt").toURI();
			File file = new File(uri);
			
			FileInputStream fis = new FileInputStream(file);
			
			String stringFromStream = CharStreams.toString(new InputStreamReader(fis, "UTF-8"));
			
			System.out.println(stringFromStream);
			
		}finally{
			
		}
		
	}
	
	
	
	
	
	
	
}
