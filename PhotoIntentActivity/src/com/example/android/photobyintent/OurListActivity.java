package com.example.android.photobyintent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import static com.example.android.photobyintent.BinderData.*;

public class OurListActivity extends Activity {

	String imageName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("e", "f");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_products);
		
		imageName = getIntent().getExtras().getString("filename");
		final Activity activity = this;
		final ListView listView = (ListView) findViewById(R.id.list);
		final ImageView imageView = (ImageView) findViewById(R.id.imgLogo);
		final TextView textView = (TextView) findViewById(R.id.loading);
		
		final List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		
		final String []SELLERS = new String[]{"amazon", "jabong", "flipkart", "snapdeal"};

		MyAR myAR = new MyAR(imageName, new FillingService() {
			
			@Override
			public void doAfterSuccess(String string) {
				try{
					JSONArray array = new JSONArray(string);
					for(int i=0;i<array.length();i++) {
						JSONObject obj = array.getJSONObject(i);
						JSONArray sellers = obj.getJSONArray("sellers");
						for(int j=0;j<sellers.length();j++) {
							HashMap<String, String> hash = new HashMap<String, String>();
							hash.put(KEY_NAME, obj.getString(KEY_NAME));
							hash.put(KEY_SELLER, SELLERS[j]);
							hash.put(KEY_PRICE, sellers.getInt(j)+"");
							hash.put(KEY_URL, obj.getString(KEY_URL));
							list.add(hash);
						}
					}
				} catch(Exception ex){
					ex.printStackTrace();
				} 
				imageView.setVisibility(View.GONE);
				textView.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
				BinderData binderData = new BinderData(activity, list, getApplicationContext());
				listView.setAdapter(binderData);
			}

		});
		myAR.call("http://192.168.1.148:5000/upload");
		
//		while(true) {
//			int i = 0;
//			String load = "Loading";
//			String dots = "";
//			for(int j=0; j<=i%3; j++)
//				dots += ".";
//			i += 1;
//			try {
//				Thread.sleep(900);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			textView.setText(load+dots);
//		}
//		
	}
	
}
