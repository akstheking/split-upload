package org.akhil.splitupload.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class UploadByPart
 */
@MultipartConfig
@WebServlet("/UploadByPart")
public class UploadByPart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String rootFolder = "C:/codesigntest/upload-test/uploaded-by-part";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// constructs path of the directory to save uploaded file

		/*
		 * Enumeration<String> headers = request.getHeaderNames(); while
		 * (headers.hasMoreElements()) { String h = headers.nextElement();
		 * System.out.println(h + " : " + request.getHeader(h));
		 * 
		 * }
		 */

		System.out.println(request.getParameterMap());
		System.out.println(request.getParameter("filename"));
		int pt = Integer.parseInt(request.getParameter("part"));
		long splitSize = Long.parseLong(request.getParameter("split-size"));

		String uploadFilePath = rootFolder + "/"
				+ request.getParameter("filename");
		System.out.println(uploadFilePath);

		// creates the save directory if it does not exists
		File fileSaveDir = new File(uploadFilePath);
		RandomAccessFile raf = new RandomAccessFile(uploadFilePath, "rw");
		if (!fileSaveDir.getParentFile().exists()) {
			fileSaveDir.getParentFile().mkdirs();
		}
		System.out.println("Upload File Directory="
				+ fileSaveDir.getAbsolutePath());
		Part part = request.getPart("split-upload");
		System.out.println(part.getName() + " " + parseFileName(part));
		raf.seek(pt * splitSize);
		InputStream is = part.getInputStream();
		byte[] bytes = new byte[4096];
		int byteRead;
		System.out.println(raf.getFilePointer());
		while ((byteRead = is.read(bytes)) != -1) {
			raf.write(bytes, 0, byteRead);
		}
		System.out.println(raf.getFilePointer());
		raf.close();
		/*
		 * for (Part part : request.getParts()) {
		 * System.out.println(part.getName() + " " + parseFileName(part)); }
		 */

		// request.setAttribute("message", fileName
		// + " File uploaded successfully!");
		// getServletContext().getRequestDispatcher("/response.jsp").forward(
		// request, response);
	}

	private String parseFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		// System.out.println("Part Header = {0}" + partHeader);
		for (String content : part.getHeader("content-disposition").split("; ")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
