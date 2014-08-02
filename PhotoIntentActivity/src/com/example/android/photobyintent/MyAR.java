package com.example.android.photobyintent;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.http.Header;

import com.loopj.android.http.*;

public class MyAR {
	
	private String fileName;
	private FillingService fillingService;
	private ImageService imageService;
	
	AsyncHttpClient client = new AsyncHttpClient();

	public MyAR(String fileName, FillingService fillingService) {
		this.fileName = fileName;
		this.fillingService = fillingService;
	}
	
	public MyAR(Object fileName2, ImageService imageService) {
		this.fileName = (String) fileName2;
		this.imageService = imageService;
	}
	
	public void callImg(String url){
		client.get(url, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				imageService.doAfterSuccess(arg2);
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public String getFileName() {
		return fileName;
	}

	public void call(String url) {
		File myFile = new File(fileName);
		RequestParams params = new RequestParams();
		try {
		    params.put("file", myFile);
		} catch(FileNotFoundException e) {}
		client.setTimeout(1000000);
		client.post(url, params, new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				fillingService.doAfterSuccess(new String(arg2));
			}
			
		});
	}
	
	
}
