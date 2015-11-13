package com.myweather.app.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class Utility {
	/**
	 * 解析服务器返回的JSON数据，并将解析出的数据存储在本地
	 */
	
	/*
	   {
	   "c":
		   {
		   "c1":"101010100",
		   "c2":"beijing",
		   "c3":"北京",
		   "c4":"beijing",
		   "c5":"北京",
		   "c6":"beijing",
		   "c7":"北京",
		   "c8":"china",
		   "c9":"中国",
		   "c10":"1",
		   "c11":"010",
		   "c12":"100000",
		   "c13":116.391000,
		   "c14":39.904000,
		   "c15":"33",
		   "c16":"AZ9010",
		   "c17":"+8"
		   },
	  "f":{
		   "f1":
			   [
				   {"fa":"53","fb":"18","fc":"8","fd":"4","fe":"0","ff":"0","fg":"0","fh":"0","fi":"06:53|17:03"},
				   {"fa":"02","fb":"01","fc":"8","fd":"4","fe":"0","ff":"0","fg":"0","fh":"0","fi":"06:54|17:02"},
				   {"fa":"01","fb":"02","fc":"9","fd":"5","fe":"0","ff":"0","fg":"0","fh":"0","fi":"06:55|17:01"}
			   ],
		   "f0":"201511101100"
	   	   }
	   }
	 */
	public static void handleWeatherResponse(Context context,String response)
	{
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
			 
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
