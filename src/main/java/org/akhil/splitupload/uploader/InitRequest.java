package org.akhil.splitupload.uploader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class InitRequest {

	File file;
	//String url = "http://localhost:8888/splitupload/UploadPartInfo";
	String url = "http://stcss-slc1.us.oracle.com:8080/splitupload/UploadPartInfo";
	
	public static void main(String[] args) throws Exception {
		InitRequest ir = new InitRequest(new File("A:/javaee/split-upload/episodes.txt"));
		ir.sendRequest();
	}

	public InitRequest(File file) {
		super();
		this.file = file;
	}

	public void sendRequest() throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		con.setRequestProperty("filename", file.getName());
		con.setRequestProperty("filesize", file.length() + "");
		String chekSum = getCheckSum(file);
		System.out.println(chekSum);
		con.setRequestProperty("checksum", chekSum);

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

	public static String getCheckSum(File file) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			FileInputStream fin = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			int bytesRead;
			while ((bytesRead = fin.read(bytes)) != -1) {
				md.update(bytes, 0, bytesRead);
			}
			fin.close();
			byte[] hash = md.digest();
			String hashString = String.format("%032x", new BigInteger(1, hash));
			return hashString;
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
