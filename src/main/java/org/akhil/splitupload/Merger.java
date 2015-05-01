package org.akhil.splitupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Part {
	int no;
	File file;
	long start;
	long size;

	public Part(int no, long start, long size) {
		this.no = no;
		this.start = start;
		this.size = size;
	}

	public Part(int no, File file, long size) {
		this.no = no;
		this.file = file;
		this.size = size;
	}

	@Override
	public String toString() {
		return "Part [no=" + no + ", file=" + file + ", start=" + start
				+ ", size=" + size + "]";
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	

}

public class Merger {
	String rootFolder = "A:/javaee/split-upload";
	String mergeRoot = rootFolder + "/merge";
	String splitRoot = rootFolder + "/split";
	String mergeFile = mergeRoot + "/merge.txt";
	File splitRootFile = new File(splitRoot);

	long length;

	RandomAccessFile raf = new RandomAccessFile(mergeFile, "rw");

	Map<Integer, Part> partMap = new TreeMap<>();

	public Merger() throws FileNotFoundException {

	}

	public static void main(String[] args) throws IOException {
		Merger merger = new Merger();
		merger.fetchAllFiles();
		merger.merge();

	}

	public void fetchAllFiles() throws IOException {

		File[] fileList = splitRootFile.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.matches("\\w+\\d+");
			}
		});
		Pattern pattern = Pattern.compile("\\w+(\\d+)");
		for (File f : fileList) {
			Matcher m = pattern.matcher(f.getName());
			m.find();
			int partNo = Integer.parseInt(m.group(1));
			partMap.put(partNo, new Part(partNo, f, f.length()));
			length += f.length();
		}

		for (Map.Entry<Integer, Part> me : partMap.entrySet()) {
			System.out.println(me);
		}
		int lastEnd = 0;
		for (Part p : partMap.values()) {
			p.setStart(lastEnd);
			lastEnd += p.getSize();
		}
		for (Map.Entry<Integer, Part> me : partMap.entrySet()) {
			System.out.println(me);
		}
		raf.setLength(length);
	}
	
	public void merge() throws IOException {
		for(Part p : partMap.values()) {
			writePart(p);
		}
	}
	
	public void writePart(Part part) throws IOException {
		raf.seek(part.getStart());
		byte[] bytes = new byte[1024];
		int read;
		FileInputStream fis = new FileInputStream(part.getFile());
		while((read = fis.read(bytes)) > 0) {
			raf.write(bytes, 0, read);
		}
		fis.close();
	}

}
