package org.akhil.splitupload.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.akhil.splitupload.uploader.InitRequest;

/**
 * Servlet implementation class CheckSum
 */
@WebServlet("/CheckSum")
public class CheckSum extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String rootFolder = "C:/codesigntest/upload-test/uploaded-by-part";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filename = request.getHeader("filename");
		String checkSum = request.getHeader("checksum");
		String uploadFilePath = rootFolder + "/" + filename;
		System.out.println("in-file" + uploadFilePath);
		File file = new File(uploadFilePath);
		System.out.println("request : " + checkSum);
		System.out.println("in-file chksum : "+InitRequest.getCheckSum(file));
		String checkSum2 = InitRequest.getCheckSum(file);
		response.getWriter().write(checkSum2.equals(checkSum) + "");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
