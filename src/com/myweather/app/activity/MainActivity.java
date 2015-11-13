package com.myweather.app.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.myweather.app.db.CoolWeatherDB;
import com.myweather.app.db.CoolWeatherOpenHelper;
import com.myweather.app.model.City;
import com.myweather.app.model.Weather;
import com.myweather.app.util.HttpCallbackListener;
import com.myweather.app.util.HttpUtil;
import com.myweather.app.util.UrlUtil;
import com.myweather.app.util.Utility;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	public static final String KEY ="474ec2_SmartWeatherAPI_2ad10e0";
	public static final String APPID = "55119730f0590d61";
	public static final String TYPE = "forecast_f";//常规数据，只有市的
	public String id = "";
	public static final String TGA = "MainActivity";
	private final static String fileName = "codecity.json";
    //需要加密的数据  
    String data = "http://open.weather.com.cn/data/?areaid=101010100&type=forecast_f&date=201511101110&appid=55119730f0590d61";  
    //密钥  
    String key = "474ec2_SmartWeatherAPI_2ad10e0";  
	private EditText EditTextInputCity;
	private Button ButtonQueryWeather;
	private Button ButtonQueryWeather1;
	private Button ButtonQueryWeather2;
	private Button ButtonQueryWeather3;
	private String inputcity ;
	private TextView tv_city;
	
	private TextView TextViewUr1_1;
	private TextView TextViewUr2_1;
	private TextView TextViewUr3_1;
	private TextView TextViewUr1_2;
	private TextView TextViewUr2_2;
	private TextView TextViewUr3_2;	
	private TextView TextViewUr1_3;
	private TextView TextViewUr2_3;
	private TextView TextViewUr3_3;	
	private TextView TextViewUr1_4;
	private TextView TextViewUr2_4;
	private TextView TextViewUr3_4;	
	private TextView TextViewUr1_5;
	private TextView TextViewUr2_5;
	private TextView TextViewUr3_5;	
	private TextView TextViewUr1_6;
	private TextView TextViewUr2_6;
	private TextView TextViewUr3_6;	
	private TextView TextViewUr1_7;
	private TextView TextViewUr2_7;
	private TextView TextViewUr3_7;	
	private TextView TextViewUr1_8;
	private TextView TextViewUr2_8;
	private TextView TextViewUr3_8;	
	private TextView TextViewUr1_9;
	private TextView TextViewUr2_9;
	private TextView TextViewUr3_9;
	
	Handler handler = new Handler(){@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case 1:
			Weather weather = (Weather) msg.obj;
			tv_city.setText("当前城市："+weather.getCityname());
			TextViewUr1_1.setText("今天");
			TextViewUr1_2.setBackgroundResource(weather.getWeathericon1());
			TextViewUr1_3.setText(weather.getWeather1());
			TextViewUr1_4.setText(weather.getTemperature1()+"℃");
			TextViewUr1_5.setText(weather.getWind1());
			
			TextViewUr2_1.setText("明天");
			TextViewUr2_2.setBackgroundResource(weather.getWeathericon2());
			TextViewUr2_3.setText(weather.getWeather2());
			TextViewUr2_4.setText(weather.getTemperature2()+"℃");
			TextViewUr2_5.setText(weather.getWind2());

			
			TextViewUr3_1.setText("后天");
			TextViewUr3_2.setBackgroundResource(weather.getWeathericon3());
			TextViewUr3_3.setText(weather.getWeather3());
			TextViewUr3_4.setText(weather.getTemperature3()+"℃");
			TextViewUr3_5.setText(weather.getWind3());

			break;
			
		case 2:
			Toast.makeText(MainActivity.this, "Sorry！您输入的城市不存在！", Toast.LENGTH_SHORT).show();
			//ButtonQueryWeather.performClick();

		default:
			break;
		}
	}};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		findAllViewById();
		addListener(); 
		initData();
		
		ButtonQueryWeather.performClick();
	}
	private void initData() {
		Log.d(TGA, "=========="+CoolWeatherDB.getInstance(MainActivity.this).getCountBycity()+"-=========");
		if(CoolWeatherDB.getInstance(MainActivity.this).getCountBycity())
		{
			Log.d(TGA, "有数据，不需要更新数据了");
		}else
		{
			//InputStream in = getClass().getClassLoader().getResourceAsStream("codecity.json"); 
			//创建Thread 读取文件 
			 final StringBuilder stringBuilder = new StringBuilder();  
		        try {  
		            AssetManager assetManager = this.getAssets();  
		            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));  
		            String line;  
		            while ((line = bf.readLine()) != null) {  
		                stringBuilder.append(line);  
		            }  
		            
		            if(!TextUtils.isEmpty(stringBuilder.toString()))
					{
						runOnUiThread(new Runnable() { 
							@Override
							public void run() {
								 
								Log.d(TGA, stringBuilder.toString());
								try {
								      JSONArray jsonArray = new JSONArray(stringBuilder.toString()); 
								      for (int i=0;i<jsonArray.length();i++)
								      {
								    	JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i);  
								    	City city = new City();
								  		city.setCityName(jsonObject2.getString("name"));
								  		city.setCityCode(jsonObject2.getString("code"));
								  		CoolWeatherDB.getInstance(MainActivity.this).saveCity(city); 
								      } 
								      Log.d(TGA, "city.length:"+jsonArray.length());
								     } catch (JSONException e) { 
								      e.printStackTrace();
								     }
							} 
						});
					} 
		            
		        } catch (Exception e) {  
		            Log.d(TGA, "初始化错误");
		        }  
		        
			
			/*
			 Log.d(TGA, "数据少于2000或者没有数据");

				String addr = "http://dailiangkang.6655.la/codecity.json";
				HttpUtil.sendHttpResquest(addr, new HttpCallbackListener() {
					
					@Override
					public void onFinish(final String response) {
						if(!TextUtils.isEmpty(response))
						{
							runOnUiThread(new Runnable() { 
								@Override
								public void run() {
									 
									Log.d(TGA, response);
									try {
									      JSONArray jsonArray = new JSONArray(response); 
									      for (int i=0;i<jsonArray.length();i++)
									      {
									    	JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i);  
									    	City city = new City();
									  		city.setCityName(jsonObject2.getString("name"));
									  		city.setCityCode(jsonObject2.getString("code"));
									  		CoolWeatherDB.getInstance(MainActivity.this).saveCity(city); 
									      } 
									      Log.d(TGA, "city.length:"+jsonArray.length());
									     } catch (JSONException e) { 
									      e.printStackTrace();
									     }
								} 
							});
						} 
					} 
					@Override
					public void onError(Exception e) {
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() { 
								 Log.d(TGA, "error");
							} 
						});
					}
				}); 
		*/}  
	}
	//添加监听方法
	private void addListener() {
		ButtonQueryWeather.setOnClickListener(this);//查询天气
		ButtonQueryWeather1.setOnClickListener(this);// 
		ButtonQueryWeather2.setOnClickListener(this);// 
		ButtonQueryWeather3.setOnClickListener(this);// 
		
		
	}
	//设置界面显示
	private void findAllViewById() {
		EditTextInputCity = (EditText) findViewById(R.id.ed_inputcity);
		ButtonQueryWeather = (Button) findViewById(R.id.bt_queryweather);
		ButtonQueryWeather1 = (Button) findViewById(R.id.bt_queryweather1);
		ButtonQueryWeather2 = (Button) findViewById(R.id.bt_queryweather2);
		ButtonQueryWeather3 = (Button) findViewById(R.id.bt_queryweather3);
		tv_city = (TextView) findViewById(R.id.tv_city);
		TextViewUr1_1= (TextView) findViewById(R.id.textView1_1);
		TextViewUr2_1= (TextView) findViewById(R.id.textView2_1);
		TextViewUr3_1= (TextView) findViewById(R.id.textView3_1);
		TextViewUr1_2= (TextView) findViewById(R.id.textView1_2);
		TextViewUr2_2= (TextView) findViewById(R.id.textView2_2);
		TextViewUr3_2= (TextView) findViewById(R.id.textView3_2);
		TextViewUr1_3= (TextView) findViewById(R.id.textView1_3);
		TextViewUr2_3= (TextView) findViewById(R.id.textView2_3);
		TextViewUr3_3= (TextView) findViewById(R.id.textView3_3);
		TextViewUr1_4= (TextView) findViewById(R.id.textView1_4);
		TextViewUr2_4= (TextView) findViewById(R.id.textView2_4);
		TextViewUr3_4= (TextView) findViewById(R.id.textView3_4);
		TextViewUr1_5= (TextView) findViewById(R.id.textView1_5);
		TextViewUr2_5= (TextView) findViewById(R.id.textView2_5);
		TextViewUr3_5= (TextView) findViewById(R.id.textView3_5);
		TextViewUr1_6= (TextView) findViewById(R.id.textView1_6);
		TextViewUr2_6= (TextView) findViewById(R.id.textView2_6);
		TextViewUr3_6= (TextView) findViewById(R.id.textView3_6);
		TextViewUr1_7= (TextView) findViewById(R.id.textView1_7);
		TextViewUr2_7= (TextView) findViewById(R.id.textView2_7);
		TextViewUr3_7= (TextView) findViewById(R.id.textView3_7);
		TextViewUr1_8= (TextView) findViewById(R.id.textView1_8);
		TextViewUr2_8= (TextView) findViewById(R.id.textView2_8);
		TextViewUr3_8= (TextView) findViewById(R.id.textView3_8);
		TextViewUr1_9= (TextView) findViewById(R.id.textView1_9);
		TextViewUr2_9= (TextView) findViewById(R.id.textView2_9);
		TextViewUr3_9= (TextView) findViewById(R.id.textView3_9);

	 
	}
	
 
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_queryweather: 
			new Thread(new Runnable() {
				
				@TargetApi(19)
				@Override
				public void run() {
					Log.d(TGA, "点了");
					HttpURLConnection connection = null; 
					try {
						inputcity = EditTextInputCity.getText().toString();
						if("".equals(inputcity))
						{
							inputcity = "蚌埠";
						}
						Log.d(TGA, "输入的城市名称："+ inputcity);
						String code = CoolWeatherDB.getInstance(MainActivity.this).loadCityCode(inputcity);
						Log.d(TGA, "输入的城市名称："+ inputcity+"获取的code:"+code);
						String urlString = UrlUtil.getUrl(code, TYPE, APPID, KEY);
						URL url = new URL(urlString);
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("GET");
						connection.setConnectTimeout(8000);
						connection.setReadTimeout(8000);
						InputStream in = connection.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(in));
						StringBuilder response = new StringBuilder();
						String line;
						while((line = reader.readLine()) != null)
						{
							response.append(line);
						} 
						
						Log.d(TGA, response.toString()); 
						//解析天气内容
						try {
							 JSONObject jsonObject =new JSONObject(response.toString()); 
							 JSONObject jsonObject1 = new JSONObject(jsonObject.getString("f")); 
							 JSONObject jsonObject1_1 = new JSONObject(jsonObject.getString("c")); 
							 String cityname = jsonObject1_1.getString("c3"); 
							 JSONArray jsonArray = jsonObject1.getJSONArray("f1"); 
							 Log.d("jsonArray.length():", jsonArray.length()+"");
							 Weather weathers = new Weather();
							 weathers.setCityname(cityname);
						     for (int i=0;i<jsonArray.length();i++)
						      {
						    	JSONObject jsonObject3 = (JSONObject)jsonArray.opt(i); 
						    	
						    	if(i==0){
						    		if("".equals(getChineseWeather(jsonObject3.getString("fa")))){
						    			String weather = getChineseWeather(jsonObject3.getString("fb"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fb"));
							    		String temperature = jsonObject3.getString("fd");
							    		String wind = getChinesewind(jsonObject3.getString("ff"))+""+getChinesewindLevel(jsonObject3.getString("fh"));
							    		weathers.setWeather1(weather);
							    		weathers.setTemperature1(temperature);
							    		weathers.setWind1(wind);
							    		weathers.setWeathericon1(weathericon);
						    		}else
						    		{ 
							    		String weather = getChineseWeather(jsonObject3.getString("fa"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fa"));
							    		String temperature = jsonObject3.getString("fd")+"-"+jsonObject3.getString("fc");
							    		String wind = getChinesewind(jsonObject3.getString("fe"))+""+getChinesewindLevel(jsonObject3.getString("fg"));
							    		weathers.setWeather1(weather);
							    		weathers.setTemperature1(temperature);
							    		weathers.setWind1(wind);
							    		weathers.setWeathericon1(weathericon);
						    		}  
						    	}
						    	else if(i==1){
						    		if("".equals(getChineseWeather(jsonObject3.getString("fa")))){
						    			String weather = getChineseWeather(jsonObject3.getString("fb"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fb"));
							    		String temperature = jsonObject3.getString("fd");
							    		String wind = getChinesewind(jsonObject3.getString("ff"))+""+getChinesewindLevel(jsonObject3.getString("fh"));
							    		weathers.setWeather2(weather);
							    		weathers.setTemperature2(temperature);
							    		weathers.setWind2(wind);
							    		weathers.setWeathericon2(weathericon);
						    		}else
						    		{ 
							    		String weather = getChineseWeather(jsonObject3.getString("fa"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fa"));
							    		String temperature = jsonObject3.getString("fd")+"-"+jsonObject3.getString("fc");
							    		String wind = getChinesewind(jsonObject3.getString("fe"))+""+getChinesewindLevel(jsonObject3.getString("fg"));
							    		weathers.setWeather2(weather);
							    		weathers.setTemperature2(temperature);
							    		weathers.setWind2(wind);
							    		weathers.setWeathericon2(weathericon);
						    		}  
						    	}else if(i==2){
						    		if("".equals(getChineseWeather(jsonObject3.getString("fa")))){
						    			String weather = getChineseWeather(jsonObject3.getString("fb"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fb"));
							    		String temperature = jsonObject3.getString("fd");
							    		String wind = getChinesewind(jsonObject3.getString("ff"))+""+getChinesewindLevel(jsonObject3.getString("fh"));
							    		weathers.setWeather3(weather);
							    		weathers.setTemperature3(temperature);
							    		weathers.setWind3(wind);
							    		weathers.setWeathericon3(weathericon);
						    		}else
						    		{ 
							    		String weather = getChineseWeather(jsonObject3.getString("fa"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fa"));
							    		String temperature = jsonObject3.getString("fd")+"-"+jsonObject3.getString("fc");
							    		String wind = getChinesewind(jsonObject3.getString("fe"))+""+getChinesewindLevel(jsonObject3.getString("fg"));
							    		weathers.setWeather3(weather);
							    		weathers.setTemperature3(temperature);
							    		weathers.setWind3(wind);
							    		weathers.setWeathericon3(weathericon);
						    		}  
						    	}
						    	
						    	
						      }   
						     	Message msg = new Message();
					    		msg.what = 1;
					    		msg.obj = weathers;
					    		handler.sendMessage(msg);
						     } catch (JSONException e) { 
						      e.printStackTrace();
						     }
						
						
						
					} catch (Exception e) { 
						Log.d(TGA, "输入有误！");
						Message msg = new Message();
						msg.what = 2;
						handler.sendMessage(msg);
						//e.printStackTrace();
						
					}finally
					{
						if(connection != null)
						{
							connection.disconnect();
						}
						
					}
				}
			}).start();
		
			break;

		case R.id.bt_queryweather1: 
			new Thread(new Runnable() {
				
				@TargetApi(19)
				@Override
				public void run() {
					Log.d(TGA, "点了");
					HttpURLConnection connection = null; 
					try {
						inputcity = ButtonQueryWeather1.getText().toString();//EditTextInputCity.getText().toString();
								
						Log.d(TGA, "输入的城市名称："+ inputcity);
						String code = CoolWeatherDB.getInstance(MainActivity.this).loadCityCode(inputcity);
						Log.d(TGA, "输入的城市名称："+ inputcity+"获取的code:"+code);
						String urlString = UrlUtil.getUrl(code, TYPE, APPID, KEY);
						URL url = new URL(urlString);
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("GET");
						connection.setConnectTimeout(8000);
						connection.setReadTimeout(8000);
						InputStream in = connection.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(in));
						StringBuilder response = new StringBuilder();
						String line;
						while((line = reader.readLine()) != null)
						{
							response.append(line);
						} 
						
						Log.d(TGA, response.toString()); 
						//解析天气内容
						try {
							 JSONObject jsonObject =new JSONObject(response.toString()); 
							 JSONObject jsonObject1 = new JSONObject(jsonObject.getString("f")); 
							 JSONObject jsonObject1_1 = new JSONObject(jsonObject.getString("c")); 
							 String cityname = jsonObject1_1.getString("c3"); 
							 JSONArray jsonArray = jsonObject1.getJSONArray("f1"); 
							 Log.d("jsonArray.length():", jsonArray.length()+"");
							 Weather weathers = new Weather();
							 weathers.setCityname(cityname);
						     for (int i=0;i<jsonArray.length();i++)
						      {
						    	JSONObject jsonObject3 = (JSONObject)jsonArray.opt(i); 
						    	
						    	if(i==0){
						    		if("".equals(getChineseWeather(jsonObject3.getString("fa")))){
						    			String weather = getChineseWeather(jsonObject3.getString("fb"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fb"));
							    		String temperature = jsonObject3.getString("fd");
							    		String wind = getChinesewind(jsonObject3.getString("ff"))+""+getChinesewindLevel(jsonObject3.getString("fh"));
							    		weathers.setWeather1(weather);
							    		weathers.setTemperature1(temperature);
							    		weathers.setWind1(wind);
							    		weathers.setWeathericon1(weathericon);
						    		}else
						    		{ 
							    		String weather = getChineseWeather(jsonObject3.getString("fa"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fa"));
							    		String temperature = jsonObject3.getString("fd")+"-"+jsonObject3.getString("fc");
							    		String wind = getChinesewind(jsonObject3.getString("fe"))+""+getChinesewindLevel(jsonObject3.getString("fg"));
							    		weathers.setWeather1(weather);
							    		weathers.setTemperature1(temperature);
							    		weathers.setWind1(wind);
							    		weathers.setWeathericon1(weathericon);
						    		}  
						    	}
						    	else if(i==1){
						    		if("".equals(getChineseWeather(jsonObject3.getString("fa")))){
						    			String weather = getChineseWeather(jsonObject3.getString("fb"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fb"));
							    		String temperature = jsonObject3.getString("fd");
							    		String wind = getChinesewind(jsonObject3.getString("ff"))+""+getChinesewindLevel(jsonObject3.getString("fh"));
							    		weathers.setWeather2(weather);
							    		weathers.setTemperature2(temperature);
							    		weathers.setWind2(wind);
							    		weathers.setWeathericon2(weathericon);
						    		}else
						    		{ 
							    		String weather = getChineseWeather(jsonObject3.getString("fa"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fa"));
							    		String temperature = jsonObject3.getString("fd")+"-"+jsonObject3.getString("fc");
							    		String wind = getChinesewind(jsonObject3.getString("fe"))+""+getChinesewindLevel(jsonObject3.getString("fg"));
							    		weathers.setWeather2(weather);
							    		weathers.setTemperature2(temperature);
							    		weathers.setWind2(wind);
							    		weathers.setWeathericon2(weathericon);
						    		}  
						    	}else if(i==2){
						    		if("".equals(getChineseWeather(jsonObject3.getString("fa")))){
						    			String weather = getChineseWeather(jsonObject3.getString("fb"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fb"));
							    		String temperature = jsonObject3.getString("fd");
							    		String wind = getChinesewind(jsonObject3.getString("ff"))+""+getChinesewindLevel(jsonObject3.getString("fh"));
							    		weathers.setWeather3(weather);
							    		weathers.setTemperature3(temperature);
							    		weathers.setWind3(wind);
							    		weathers.setWeathericon3(weathericon);
						    		}else
						    		{ 
							    		String weather = getChineseWeather(jsonObject3.getString("fa"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fa"));
							    		String temperature = jsonObject3.getString("fd")+"-"+jsonObject3.getString("fc");
							    		String wind = getChinesewind(jsonObject3.getString("fe"))+""+getChinesewindLevel(jsonObject3.getString("fg"));
							    		weathers.setWeather3(weather);
							    		weathers.setTemperature3(temperature);
							    		weathers.setWind3(wind);
							    		weathers.setWeathericon3(weathericon);
						    		}  
						    	}
						    	
						    	
						      }   
						     	Message msg = new Message();
					    		msg.what = 1;
					    		msg.obj = weathers;
					    		handler.sendMessage(msg);
						     } catch (JSONException e) { 
						      e.printStackTrace();
						     }
						
						
						
					} catch (Exception e) { 
						Log.d(TGA, "输入有误！");
						//e.printStackTrace();
						
					}finally
					{
						if(connection != null)
						{
							connection.disconnect();
						}
						
					}
				}
			}).start();
		
			break;

		case R.id.bt_queryweather2: 
			new Thread(new Runnable() {
				
				@TargetApi(19)
				@Override
				public void run() {
					Log.d(TGA, "点了");
					HttpURLConnection connection = null; 
					try {
						inputcity = ButtonQueryWeather2.getText().toString();//EditTextInputCity.getText().toString();
						Log.d(TGA, "输入的城市名称："+ inputcity);
						String code = CoolWeatherDB.getInstance(MainActivity.this).loadCityCode(inputcity);
						Log.d(TGA, "输入的城市名称："+ inputcity+"获取的code:"+code);
						String urlString = UrlUtil.getUrl(code, TYPE, APPID, KEY);
						URL url = new URL(urlString);
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("GET");
						connection.setConnectTimeout(8000);
						connection.setReadTimeout(8000);
						InputStream in = connection.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(in));
						StringBuilder response = new StringBuilder();
						String line;
						while((line = reader.readLine()) != null)
						{
							response.append(line);
						} 
						
						Log.d(TGA, response.toString()); 
						//解析天气内容
						try {
							 JSONObject jsonObject =new JSONObject(response.toString()); 
							 JSONObject jsonObject1 = new JSONObject(jsonObject.getString("f")); 
							 JSONObject jsonObject1_1 = new JSONObject(jsonObject.getString("c")); 
							 String cityname = jsonObject1_1.getString("c3"); 
							 JSONArray jsonArray = jsonObject1.getJSONArray("f1"); 
							 Log.d("jsonArray.length():", jsonArray.length()+"");
							 Weather weathers = new Weather();
							 weathers.setCityname(cityname);
						     for (int i=0;i<jsonArray.length();i++)
						      {
						    	JSONObject jsonObject3 = (JSONObject)jsonArray.opt(i); 
						    	
						    	if(i==0){
						    		if("".equals(getChineseWeather(jsonObject3.getString("fa")))){
						    			String weather = getChineseWeather(jsonObject3.getString("fb"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fb"));
							    		String temperature = jsonObject3.getString("fd");
							    		String wind = getChinesewind(jsonObject3.getString("ff"))+""+getChinesewindLevel(jsonObject3.getString("fh"));
							    		weathers.setWeather1(weather);
							    		weathers.setTemperature1(temperature);
							    		weathers.setWind1(wind);
							    		weathers.setWeathericon1(weathericon);
						    		}else
						    		{ 
							    		String weather = getChineseWeather(jsonObject3.getString("fa"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fa"));
							    		String temperature = jsonObject3.getString("fd")+"-"+jsonObject3.getString("fc");
							    		String wind = getChinesewind(jsonObject3.getString("fe"))+""+getChinesewindLevel(jsonObject3.getString("fg"));
							    		weathers.setWeather1(weather);
							    		weathers.setTemperature1(temperature);
							    		weathers.setWind1(wind);
							    		weathers.setWeathericon1(weathericon);
						    		}  
						    	}
						    	else if(i==1){
						    		if("".equals(getChineseWeather(jsonObject3.getString("fa")))){
						    			String weather = getChineseWeather(jsonObject3.getString("fb"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fb"));
							    		String temperature = jsonObject3.getString("fd");
							    		String wind = getChinesewind(jsonObject3.getString("ff"))+""+getChinesewindLevel(jsonObject3.getString("fh"));
							    		weathers.setWeather2(weather);
							    		weathers.setTemperature2(temperature);
							    		weathers.setWind2(wind);
							    		weathers.setWeathericon2(weathericon);
						    		}else
						    		{ 
							    		String weather = getChineseWeather(jsonObject3.getString("fa"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fa"));
							    		String temperature = jsonObject3.getString("fd")+"-"+jsonObject3.getString("fc");
							    		String wind = getChinesewind(jsonObject3.getString("fe"))+""+getChinesewindLevel(jsonObject3.getString("fg"));
							    		weathers.setWeather2(weather);
							    		weathers.setTemperature2(temperature);
							    		weathers.setWind2(wind);
							    		weathers.setWeathericon2(weathericon);
						    		}  
						    	}else if(i==2){
						    		if("".equals(getChineseWeather(jsonObject3.getString("fa")))){
						    			String weather = getChineseWeather(jsonObject3.getString("fb"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fb"));
							    		String temperature = jsonObject3.getString("fd");
							    		String wind = getChinesewind(jsonObject3.getString("ff"))+""+getChinesewindLevel(jsonObject3.getString("fh"));
							    		weathers.setWeather3(weather);
							    		weathers.setTemperature3(temperature);
							    		weathers.setWind3(wind);
							    		weathers.setWeathericon3(weathericon);
						    		}else
						    		{ 
							    		String weather = getChineseWeather(jsonObject3.getString("fa"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fa"));
							    		String temperature = jsonObject3.getString("fd")+"-"+jsonObject3.getString("fc");
							    		String wind = getChinesewind(jsonObject3.getString("fe"))+""+getChinesewindLevel(jsonObject3.getString("fg"));
							    		weathers.setWeather3(weather);
							    		weathers.setTemperature3(temperature);
							    		weathers.setWind3(wind);
							    		weathers.setWeathericon3(weathericon);
						    		}  
						    	}
						    	
						    	
						      }   
						     	Message msg = new Message();
					    		msg.what = 1;
					    		msg.obj = weathers;
					    		handler.sendMessage(msg);
						     } catch (JSONException e) { 
						      e.printStackTrace();
						     }
						
						
						
					} catch (Exception e) { 
						Log.d(TGA, "输入有误！");
						//e.printStackTrace();
						
					}finally
					{
						if(connection != null)
						{
							connection.disconnect();
						}
						
					}
				}
			}).start();
		
			break;
			

		case R.id.bt_queryweather3: 
			new Thread(new Runnable() {
				
				@TargetApi(19)
				@Override
				public void run() {
					Log.d(TGA, "点了");
					HttpURLConnection connection = null; 
					try {
						inputcity = ButtonQueryWeather3.getText().toString();//EditTextInputCity.getText().toString();
						Log.d(TGA, "输入的城市名称："+ inputcity);
						String code = CoolWeatherDB.getInstance(MainActivity.this).loadCityCode(inputcity);
						Log.d(TGA, "输入的城市名称："+ inputcity+"获取的code:"+code);
						String urlString = UrlUtil.getUrl(code, TYPE, APPID, KEY);
						URL url = new URL(urlString);
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("GET");
						connection.setConnectTimeout(8000);
						connection.setReadTimeout(8000);
						InputStream in = connection.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(in));
						StringBuilder response = new StringBuilder();
						String line;
						while((line = reader.readLine()) != null)
						{
							response.append(line);
						} 
						
						Log.d(TGA, response.toString()); 
						//解析天气内容
						try {
							 JSONObject jsonObject =new JSONObject(response.toString()); 
							 JSONObject jsonObject1 = new JSONObject(jsonObject.getString("f")); 
							 JSONObject jsonObject1_1 = new JSONObject(jsonObject.getString("c")); 
							 String cityname = jsonObject1_1.getString("c3"); 
							 JSONArray jsonArray = jsonObject1.getJSONArray("f1"); 
							 Log.d("jsonArray.length():", jsonArray.length()+"");
							 Weather weathers = new Weather();
							 weathers.setCityname(cityname);
						     for (int i=0;i<jsonArray.length();i++)
						      {
						    	JSONObject jsonObject3 = (JSONObject)jsonArray.opt(i); 
						    	
						    	if(i==0){
						    		if("".equals(getChineseWeather(jsonObject3.getString("fa")))){
						    			String weather = getChineseWeather(jsonObject3.getString("fb"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fb"));
							    		String temperature = jsonObject3.getString("fd");
							    		String wind = getChinesewind(jsonObject3.getString("ff"))+""+getChinesewindLevel(jsonObject3.getString("fh"));
							    		weathers.setWeather1(weather);
							    		weathers.setTemperature1(temperature);
							    		weathers.setWind1(wind);
							    		weathers.setWeathericon1(weathericon);
						    		}else
						    		{ 
							    		String weather = getChineseWeather(jsonObject3.getString("fa"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fa"));
							    		String temperature = jsonObject3.getString("fd")+"-"+jsonObject3.getString("fc");
							    		String wind = getChinesewind(jsonObject3.getString("fe"))+""+getChinesewindLevel(jsonObject3.getString("fg"));
							    		weathers.setWeather1(weather);
							    		weathers.setTemperature1(temperature);
							    		weathers.setWind1(wind);
							    		weathers.setWeathericon1(weathericon);
						    		}  
						    	}
						    	else if(i==1){
						    		if("".equals(getChineseWeather(jsonObject3.getString("fa")))){
						    			String weather = getChineseWeather(jsonObject3.getString("fb"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fb"));
							    		String temperature = jsonObject3.getString("fd");
							    		String wind = getChinesewind(jsonObject3.getString("ff"))+""+getChinesewindLevel(jsonObject3.getString("fh"));
							    		weathers.setWeather2(weather);
							    		weathers.setTemperature2(temperature);
							    		weathers.setWind2(wind);
							    		weathers.setWeathericon2(weathericon);
						    		}else
						    		{ 
							    		String weather = getChineseWeather(jsonObject3.getString("fa"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fa"));
							    		String temperature = jsonObject3.getString("fd")+"-"+jsonObject3.getString("fc");
							    		String wind = getChinesewind(jsonObject3.getString("fe"))+""+getChinesewindLevel(jsonObject3.getString("fg"));
							    		weathers.setWeather2(weather);
							    		weathers.setTemperature2(temperature);
							    		weathers.setWind2(wind);
							    		weathers.setWeathericon2(weathericon);
						    		}  
						    	}else if(i==2){
						    		if("".equals(getChineseWeather(jsonObject3.getString("fa")))){
						    			String weather = getChineseWeather(jsonObject3.getString("fb"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fb"));
							    		String temperature = jsonObject3.getString("fd");
							    		String wind = getChinesewind(jsonObject3.getString("ff"))+""+getChinesewindLevel(jsonObject3.getString("fh"));
							    		weathers.setWeather3(weather);
							    		weathers.setTemperature3(temperature);
							    		weathers.setWind3(wind);
							    		weathers.setWeathericon3(weathericon);
						    		}else
						    		{ 
							    		String weather = getChineseWeather(jsonObject3.getString("fa"));
							    		int weathericon = getChineseWeatherIcon(jsonObject3.getString("fa"));
							    		String temperature = jsonObject3.getString("fd")+"-"+jsonObject3.getString("fc");
							    		String wind = getChinesewind(jsonObject3.getString("fe"))+""+getChinesewindLevel(jsonObject3.getString("fg"));
							    		weathers.setWeather3(weather);
							    		weathers.setTemperature3(temperature);
							    		weathers.setWind3(wind);
							    		weathers.setWeathericon3(weathericon);
						    		}  
						    	}
						    	
						    	
						      }   
						     	Message msg = new Message();
					    		msg.what = 1;
					    		msg.obj = weathers;
					    		handler.sendMessage(msg);
						     } catch (JSONException e) { 
						      e.printStackTrace();
						     }
						
						
						
					} catch (Exception e) { 
						Log.d(TGA, "输入有误！"); 
						//e.printStackTrace();
						
					}finally
					{
						if(connection != null)
						{
							connection.disconnect();
						}
						
					}
				}
			}).start();
		
			break;
 
		default:
			break;
		}
	}
	  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public static String getChineseWeather(String code)
	{
		String weather = "";
		if("00".equals(code))
		{
			weather="晴";
		}else if("01".equals(code))
		{
			weather="多云";
		}else if("02".equals(code))
		{
			weather="阴";
		}else if("03".equals(code))
		{
			weather="阵雨";
		}else if("04".equals(code))
		{
			weather="雷阵雨";
		}else if("05".equals(code))
		{
			weather="雷阵雨伴有冰雹";
		}else if("06".equals(code))
		{
			weather="雨夹雪";
		}else if("07".equals(code))
		{
			weather="小雨";
		}else if("08".equals(code))
		{
			weather="中雨";
		}else if("09".equals(code))
		{
			weather="大雨";
		}else if("10".equals(code))
		{
			weather="暴雨";
		}else if("11".equals(code))
		{
			weather="大暴雨";
		}else if("12".equals(code))
		{
			weather="特大暴雨";
		}else if("13".equals(code))
		{
			weather="阵雪";
		}else if("14".equals(code))
		{
			weather="小雪";
		}else if("15".equals(code))
		{
			weather="中雪";
		}else if("16".equals(code))
		{
			weather="大雪";
		}else if("17".equals(code))
		{
			weather="暴雪";
		}else if("18".equals(code))
		{
			weather="雾";
		}else if("19".equals(code))
		{
			weather="冻雨";
		}else if("20".equals(code))
		{
			weather="沙尘暴";
		}else if("21".equals(code))
		{
			weather="小到中雨";
		}else if("22".equals(code))
		{
			weather="中到大雨";
		}else if("23".equals(code))
		{
			weather="大到暴雨";
		}else if("24".equals(code))
		{
			weather="暴雨到大暴雨";
		}else if("25".equals(code))
		{
			weather="大暴雨到特大暴雨";
		}else if("26".equals(code))
		{
			weather="小到中雪";
		}else if("27".equals(code))
		{
			weather="中到大雪";
		}else if("28".equals(code))
		{
			weather="大到暴雪";
		}else if("29".equals(code))
		{
			weather="浮尘";
		}else if("30".equals(code))
		{
			weather="扬尘";
		}else if("31".equals(code))
		{
			weather="强沙尘暴";
		}else if("53".equals(code))
		{
			weather="霾";
		}else if("99".equals(code))
		{
			weather="无";
		}
		return weather;
	}
	
	public static int getChineseWeatherIcon(String code)
	{
		String weather = "";
		int weatherIcon = R.drawable.a1;
		if("00".equals(code))
		{
			weather="晴";
			weatherIcon=R.drawable.b1;
		}else if("01".equals(code))
		{
			weather="多云";
			weatherIcon=R.drawable.b5;
		}else if("02".equals(code))
		{
			weather="阴";
			weatherIcon=R.drawable.b3;
		}else if("03".equals(code))
		{
			weather="阵雨";
			weatherIcon=R.drawable.b14;
		}else if("04".equals(code))
		{
			weather="雷阵雨";
			weatherIcon=R.drawable.b14;
		}else if("05".equals(code))
		{
			weather="雷阵雨伴有冰雹";
			weatherIcon=R.drawable.b31;
		}else if("06".equals(code))
		{
			weather="雨夹雪";
			weatherIcon=R.drawable.b16;
		}else if("07".equals(code))
		{
			weather="小雨";
			weatherIcon=R.drawable.b8;
		}else if("08".equals(code))
		{
			weather="中雨";
			weatherIcon=R.drawable.b9;
		}else if("09".equals(code))
		{
			weather="大雨";
			weatherIcon=R.drawable.b10;
		}else if("10".equals(code))
		{
			weather="暴雨";
			weatherIcon=R.drawable.b11;
		}else if("11".equals(code))
		{
			weather="大暴雨";
			weatherIcon=R.drawable.b12;
		}else if("12".equals(code))
		{
			weather="特大暴雨";
			weatherIcon=R.drawable.b13;
		}else if("13".equals(code))
		{
			weather="阵雪";
			weatherIcon=R.drawable.b17;
		}else if("14".equals(code))
		{
			weather="小雪";
			weatherIcon=R.drawable.b17;
		}else if("15".equals(code))
		{
			weather="中雪";
			weatherIcon=R.drawable.b18;
		}else if("16".equals(code))
		{
			weather="大雪";
			weatherIcon=R.drawable.b19;
		}else if("17".equals(code))
		{
			weather="暴雪";
			weatherIcon=R.drawable.b20;
		}else if("18".equals(code))
		{
			weather="雾";
			weatherIcon=R.drawable.b4;
		}else if("19".equals(code))
		{
			weather="冻雨";
			weatherIcon=R.drawable.b9;
		}else if("20".equals(code))
		{
			weather="沙尘暴";
			weatherIcon=R.drawable.b23;
		}else if("21".equals(code))
		{
			weather="小到中雨";
			weatherIcon=R.drawable.b8;
		}else if("22".equals(code))
		{
			weather="中到大雨";
			weatherIcon=R.drawable.b10;
		}else if("23".equals(code))
		{
			weather="大到暴雨";
			weatherIcon=R.drawable.b11;
		}else if("24".equals(code))
		{
			weather="暴雨到大暴雨";
			weatherIcon=R.drawable.b12;
		}else if("25".equals(code))
		{
			weather="大暴雨到特大暴雨";
			weatherIcon=R.drawable.b13;
		}else if("26".equals(code))
		{
			weather="小到中雪";
			weatherIcon=R.drawable.b17;
		}else if("27".equals(code))
		{
			weather="中到大雪";
			weatherIcon=R.drawable.b18;
		}else if("28".equals(code))
		{
			weather="大到暴雪";
			weatherIcon=R.drawable.b19;
		}else if("29".equals(code))
		{
			weather="浮尘";
			weatherIcon=R.drawable.b22;
		}else if("30".equals(code))
		{
			weather="扬尘";
			weatherIcon=R.drawable.b23;
		}else if("31".equals(code))
		{
			weather="强沙尘暴";
			weatherIcon=R.drawable.b24;
		}else if("53".equals(code))
		{
			weather="霾";
			weatherIcon=R.drawable.b7;
		}else if("99".equals(code))
		{
			weather="无";
			weatherIcon=R.drawable.b3;
		}
		return weatherIcon;
	}
	
	
	public static String getChinesewind(String code)
	{
		String wind = "";
		if("0".equals(code))
		{
			wind="";
		}else if("1".equals(code))
		{
			wind="东北风";
		}else if("2".equals(code))
		{
			wind="东风";
		}else if("3".equals(code))
		{
			wind="东南风";
		}else if("4".equals(code))
		{
			wind="南风";
		}else if("5".equals(code))
		{
			wind="西南风";
		}else if("6".equals(code))
		{
			wind="西风";
		}else if("7".equals(code))
		{
			wind="西北风";
		}else if("8".equals(code))
		{
			wind="北风";
		}else if("9".equals(code))
		{
			wind="旋转风";
		}
		return wind;
	}
	
	public static String getChinesewindLevel(String code)
	{
		String windLevel = "";
		if("0".equals(code))
		{
			windLevel="微风";
		}else if("1".equals(code))
		{
			windLevel="3-4级";
		}else if("2".equals(code))
		{
			windLevel="4-5级";
		}else if("3".equals(code))
		{
			windLevel="5-6级";
		}else if("4".equals(code))
		{
			windLevel="6-7级";
		}else if("5".equals(code))
		{
			windLevel="7-8级";
		}else if("6".equals(code))
		{
			windLevel="8-9级";
		}else if("7".equals(code))
		{
			windLevel="9-10级";
		}else if("8".equals(code))
		{
			windLevel="10-11级";
		}else if("9".equals(code))
		{
			windLevel="11-12级";
		}
		return windLevel;
	}

	
	
 
}
