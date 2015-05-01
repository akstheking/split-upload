package org.akhil.splitupload.uploader;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClientUploadRequest {
	String filePath = "A:/javaee/split-upload/episodes.txt";
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		HttpClientUploadRequest ur = new HttpClientUploadRequest();
		ur.upload();
		
	}

	public void upload() throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		MultipartEntity entity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		//entity.addPart("number", new StringBody("5555555555"));
		//entity.addPart("clip", new StringBody("rickroll"));
		File fileToUpload = new File(filePath);
		FileBody fileBody = new FileBody(fileToUpload,
				"application/octet-stream");
		entity.addPart("upload_file", fileBody);
		//entity.addPart("tos", new StringBody("agree"));

		HttpPost httpPost = new HttpPost("http://localhost:8888/splitupload/Upload");
		httpPost.setEntity(entity);		
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity result = response.getEntity();
	}

}
