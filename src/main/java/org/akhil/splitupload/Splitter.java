package org.akhil.splitupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Splitter {
	private static final int SPLIT_SIZE = 1000;
	
	public static void main(String... args) throws IOException{
		String rootFolder = "A:/javaee/split-upload";
		String fileLoc = rootFolder + "/episodes.txt";
		FileInputStream fis = new FileInputStream(fileLoc);
		
		File file = new File(fileLoc);
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		long length = file.length();
		System.out.println(length);
		float floatParts = (float)length/SPLIT_SIZE;
		System.out.println(floatParts);
		int parts = (int)Math.ceil(floatParts);
		System.out.println(parts);
		float lastPartsize = length - (parts-1)*SPLIT_SIZE;
		System.out.println(lastPartsize);
		byte bytes[] = new byte[SPLIT_SIZE];
		int read;
		for(int i=1; i<=parts; i++) {
			FileOutputStream fos = new FileOutputStream(rootFolder + "/split/part" + i);			
			read = raf.read(bytes);
			fos.write(bytes, 0, read);
			fos.close();
		}
	} 

}
