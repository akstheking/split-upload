package org.akhil.splitupload.uploader;

import java.awt.Checkbox;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EndCheckSum {
	
	File file;
	//String url = "http://localhost:8888/splitupload/CheckSum";
	String url = "http://stcss-slc1.us.oracle.com:8080/splitupload/CheckSum";
	
	public static void main(String[] args) throws Exception {
		InitRequest ir = new InitRequest(new File("A:/javaee/split-upload/episodes.txt"));
		ir.sendRequest();
	}

	public EndCheckSum(File file) {
		super();
		this.file = file;
	}

	public void sendRequest() throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		con.setRequestProperty("filename", file.getName());
		String checkSum = InitRequest.getCheckSum(file);
		con.setRequestProperty("checksum", checkSum);
		System.out.println(checkSum);
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

	}

}
