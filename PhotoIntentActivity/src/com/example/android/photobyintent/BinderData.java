package com.example.android.photobyintent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BinderData extends BaseAdapter {
	
	static final String KEY_NAME = "name";
	static final String KEY_DESC = "description";
	static final String KEY_SELLER = "seller";
	static final String KEY_URL = "url";
	static final String KEY_PRICE = "price";
	static final String KEY_ICON = "image";
	
	LayoutInflater inflater;
	ImageView thumb_image;
	List<HashMap<String,String>> weatherDataCollection;
	HashMap<String, List<ImageView>> imgViews = new HashMap<String, List<ImageView>>();
	final HashMap<String, byte[]>list2 = new HashMap<String, byte[]>();
	
	Context context;
	
	ViewHolder holder;
	
	
	
	public BinderData(Activity act, List<HashMap<String, String>> weatherDataCollection,
			Context context) {
		super();
		this.weatherDataCollection = weatherDataCollection;
		
		inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
	}

	public BinderData() {
	}
	
	public BinderData(Activity act, List<HashMap<String,String>> map) {
		
		this.weatherDataCollection = map;
		
		inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d("size", weatherDataCollection.size()+"");
	}
	

	public int getCount() {
		// TODO Auto-generated method stub
//		return idlist.size();
		return weatherDataCollection.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		 
		View vi=convertView;
	    if(convertView==null){
	     
	      vi = inflater.inflate(R.layout.list_row, null);
	      holder = new ViewHolder();
	      holder.layout = (RelativeLayout) vi.findViewById(R.id.parentLayout);
	      holder.tvCity = (TextView)vi.findViewById(R.id.tvCity); // city name
	      holder.tvWeather = (TextView)vi.findViewById(R.id.tvCondition); // city weather overview
	      holder.tvWeatherImage =(ImageView)vi.findViewById(R.id.list_image); // thumb image
	 
	      vi.setTag(holder);
	    }
	    else{
	    	
	    	holder = (ViewHolder)vi.getTag();
	    }
	      
	      holder.tvCity.setText(weatherDataCollection.get(position).get(KEY_NAME));
	      holder.tvWeather.setText("₹ "+weatherDataCollection.get(position).get(KEY_PRICE)+" ("+weatherDataCollection.get(position).get(KEY_SELLER)+".com)");
	      String url = weatherDataCollection.get(position).get(KEY_URL);

	      List<ImageView> ls = new ArrayList<ImageView>();
	      if(imgViews.containsKey(url)){
	    	  ls = imgViews.get(url);
	      }
	      ls.add(holder.tvWeatherImage);
	      imgViews.put(url, ls);
	      
	      if(position == weatherDataCollection.size()-1){
	    	  setImages();
	      }
	      holder.layout.setOnClickListener(
	    	new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, WebViewActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("url", "http://"+weatherDataCollection.get(position).get(KEY_SELLER)+
							".com/search?q="+weatherDataCollection.get(position).get(KEY_NAME).toLowerCase().replace(" ", "+"));
					Toast.makeText(context, "clicked", Toast.LENGTH_LONG).show();
					context.startActivity(intent);
				}
			}	  
	      );
	      return vi;
	}
	
	public void setImages(){
		for(final String url: imgViews.keySet()){
			if(list2.containsKey(url)){
				byte[] bs = list2.get(url);
				Bitmap bmp = BitmapFactory.decodeByteArray(bs, 0, bs.length);
				List<ImageView> ls = imgViews.get(url);
				for(int i=0;i<ls.size();i++){
					ls.get(i).setImageBitmap(bmp);
				}
				return;
			}
				
			MyAR myAR = new MyAR(null, new ImageService() {
				
				@Override
				public void doAfterSuccess(byte[] bs) {
					list2.put(url, bs);
					Bitmap bmp = BitmapFactory.decodeByteArray(bs, 0, bs.length);
					List<ImageView> ls = imgViews.get(url);
					for(int i=0;i<ls.size();i++){
						ls.get(i).setImageBitmap(bmp);
					}
				}
			});
		    myAR.callImg("http://192.168.1.148:5000/image/"+url);   	  
		}
	}
	/*
	 * 
	 * */
	static class ViewHolder{
		RelativeLayout layout;
		TextView tvCity;
		TextView tvTemperature;
		TextView tvWeather;
		ImageView tvWeatherImage;
	}
	public void sesettMap(List<HashMap<String, String>> list) {
		this.weatherDataCollection = list;
		
	}
	
}
