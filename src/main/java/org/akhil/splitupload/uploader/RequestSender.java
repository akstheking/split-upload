package org.akhil.splitupload.uploader;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RequestSender {
	
//private static final int SPLIT_SIZE = 10000;
	
	public static void main(String... args) throws Exception{
		String rootFolder = "A:/javaee/split-upload";
		String fileLoc = rootFolder + "/eMClient.msi";
		InitRequest ir = new InitRequest(new File(fileLoc));
		ir.sendRequest();		
		//FileInputStream fis = new FileInputStream(fileLoc);		
		File file = new File(fileLoc);
		//System.out.println(file.getName() + " " + InitRequest.getCheckSum(file));
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		long length = file.length();
		int SPLIT_SIZE = (int)length/10;
		//System.out.println(length);
		float floatParts = (float)length/SPLIT_SIZE;
		//System.out.println(floatParts);
		int parts = (int)Math.ceil(floatParts);
		//System.out.println(parts);
		//float lastPartsize = length - (parts-1)*SPLIT_SIZE;
		//System.out.println(lastPartsize);
		//byte bytes[] = new byte[SPLIT_SIZE];
		//int read;
		ExecutorService ex = Executors.newFixedThreadPool(parts);
		List<Future> list = new ArrayList<>();
		
		for(int i=0; i<parts; i++) {
			//UploadRequest ur = new UploadRequest(raf, i, SPLIT_SIZE, file.getName());
			UploadRequest ur = new UploadRequest(file, i, SPLIT_SIZE, file.getName());
			//ur.sendRequest();
			Future ft = ex.submit(new RequestThread(ur));
			list.add(ft);
		}
		for(Future f : list) {
			System.out.println(f.get());
		}
		ex.shutdown();
		raf.close();
		EndCheckSum ec = new EndCheckSum(file);
		ec.sendRequest();
	} 

}
