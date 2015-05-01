package org.akhil.splitupload.uploader;

public class RequestThread implements Runnable{
	
	private UploadRequest ur;

	public RequestThread(UploadRequest ur) {
		this.ur = ur;
	}
	
	@Override
	public void run() {
		System.out.println("Sending Request " + Thread.currentThread().getId());
		ur.sendRequest();		
	}

}
