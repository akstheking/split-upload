package org.akhil.splitupload.uploader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SendUploadRequest {

	public static void main(String[] args) {
		String charset = "UTF-8";
		//String requestURL = "http://localhost:8888/splitupload/Upload";
		String requestURL = "http://stcss-slc1.us.oracle.com:8080/splitupload/Upload";
		String filePath = "A:/javaee/split-upload/eMClient.msi";
		File file = new File(filePath);

		try {
			MultipartUtility multipart = new MultipartUtility(requestURL,
					charset);

			multipart.addHeaderField("User-Agent", "CodeJava");
			multipart.addHeaderField("Test-Header", "Header-Value");

			//multipart.addFormField("description", "Cool Pictures");
			//multipart.addFormField("keywords", "Java,upload,Spring");

			multipart.addFilePart("fileUpload", file);
			//multipart.addFilePart("fileUpload", uploadFile2);

			List<String> response = multipart.finish();

			System.out.println("SERVER REPLIED:");

			for (String line : response) {
				System.out.println(line);
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}
