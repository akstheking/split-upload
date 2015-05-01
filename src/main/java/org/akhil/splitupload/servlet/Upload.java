package org.akhil.splitupload.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class Upload
 */
@MultipartConfig
@WebServlet("/Upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String rootFolder = "C:/codesigntest/upload-test/uploaded";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// constructs path of the directory to save uploaded file
		String uploadFilePath = rootFolder + "/uploaded";

		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String h = headers.nextElement();
			System.out.println(h + " : " + request.getHeader(h));

		}
		// response.sendError(-5, "Error 420");

		// creates the save directory if it does not exists
		File fileSaveDir = new File(uploadFilePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
		}
		System.out.println("Upload File Directory="
				+ fileSaveDir.getAbsolutePath());

		String fileName = null;
		// Get all the parts from request and write it to the file on server
		for (Part part : request.getParts()) {
			fileName = parseFileName(part);
			System.out.println(part.getName() + " " + fileName + " "
					+ uploadFilePath + "/" + fileName);
			part.write(uploadFilePath + "/" + fileName);
		}
		//response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errrrror");

		request.setAttribute("message", fileName
				+ " File uploaded successfully!");
		// getServletContext().getRequestDispatcher("/response.jsp").forward(
		// request, response);
	}
	
	private String parseFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		//System.out.println("Part Header = {0}" + partHeader);
		for (String content : part.getHeader("content-disposition").split("; ")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}

	/**
	 * Utility method to get file name from HTTP header content-disposition
	 */
	private String getFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		System.out.println("content-disposition header= " + contentDisp);
		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				return token.substring(token.indexOf("=") + 2,
						token.length() - 1);
			}
		}
		return "";
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
