package org.akhil.splitupload.servlet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UploadPartInfo
 */
@WebServlet("/UploadPartInfo")
public class UploadPartInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String rootFolder = "C:/codesigntest/upload-test/uploaded-by-part";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadPartInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getHeader("filename");
		String fileSize = request.getHeader("filesize");
		String checkSum = request.getHeader("checksum");
		System.out.println(fileSize + " " + fileName + " " + checkSum);
		new File(rootFolder).mkdirs();
		String filePath = rootFolder + "/" + fileName;
		String checkSumFile = rootFolder + "/checksum.txt";
		RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
		long size = Long.parseLong(fileSize);
		raf.setLength(size);
		raf.close();
		System.out.println(new File(filePath).length());
		FileWriter fw = new FileWriter(checkSumFile);		
		fw.write(checkSum);
		fw.close();
		response.getWriter().write(fileSize + " " + fileName + " " + checkSum + " received");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
