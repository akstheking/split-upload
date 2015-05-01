package org.akhil.splitupload.uploader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import java.util.List;

public class UploadRequest {

	String charset = "UTF-8";
	//String requestURL = "http://localhost:8888/splitupload/UploadByPart";
	String requestURL = "http://stcss-slc1.us.oracle.com:8080/splitupload/UploadByPart";
	RandomAccessFile rf;
	int part;
	int splitSize;
	String fileName;

	public UploadRequest(RandomAccessFile rf, int part, int splitSize,
			String fileName) {
		super();
		this.rf = rf;
		this.part = part;
		this.splitSize = splitSize;
		this.fileName = fileName;

	}
	
	public UploadRequest(File f, int part, int splitSize,
			String fileName) throws FileNotFoundException {
		super();
		this.rf = new RandomAccessFile(f, "rw");
		this.part = part;
		this.splitSize = splitSize;
		this.fileName = fileName;

	}

	public void sendRequest() {
		try {
			requestURL += "?filename=" + URLEncoder.encode(fileName, "UTF-8")
					+ "&part=" + part + "&split-size=" + splitSize;
			MultipartUtility multipart = new MultipartUtility(requestURL,
					charset);

			multipart.addHeaderField("User-Agent", "CodeJava");
			multipart.addHeaderField("Test-Header", "Header-Value");
			multipart.addHeaderField("Split-Size", splitSize + "");
			multipart.addHeaderField("Filename", fileName);
			multipart.addHeaderField("part", part + "");

			// multipart.addFormField("description", "Cool Pictures");
			// multipart.addFormField("keywords", "Java,upload,Spring");
			multipart.addFilePart("split-upload", rf, part, splitSize);

			// multipart.addFilePart("fileUpload", file);
			// multipart.addFilePart("fileUpload", uploadFile2);

			List<String> response = multipart.finish();
			rf.close();

			System.out.println("SERVER REPLIED:");

			for (String line : response) {
				System.out.println(line);
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}

	}

}
