package com.myweather.app.db;

/*
 * Hi,a403071690
 * 403071690@qq.com
 * AppId：55119730f0590d61 
 * Private_Key：474ec2_SmartWeatherAPI_2ad10e0
 * http://open.weather.com.cn/data/?areaid=101010100&type=forecast_f&date=201212010741&appid=551197&key=474ec2_SmartWeatherAPI_2ad10e0
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.myweather.app.model.City;
import com.myweather.app.model.County;
import com.myweather.app.model.Province;

public class CoolWeatherDB {
	/**
	 * 数据库名称
	 */
	public static final String DB_NAME = "cool_weather.db";
	
	/**
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	/**
	 * 构造方法是优化
	 * @param context
	 */
	private CoolWeatherDB(Context context) {
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/**
	 * 获取CoolWeatherDB 实例
	 * @param context
	 * @return CoolWeatherDB coolWeatherDB;
	 */
	public synchronized static CoolWeatherDB getInstance(Context context)
	{
		if(coolWeatherDB == null)
		{
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	/**
	 * 将Province实例存储到数据库
	 */
	public void saveProvince(Province province)
	{
		if(province != null)
		{
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	
	/**
	 * 从数据库里读取全国所有省份信息
	 */
	public List<Province> loadProvinces()
	{
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst())
		{
			do {
				Province province =new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			} while (cursor.moveToNext());
		} 
		return list;
	}
	
	/**
	 * 将City实例存储到数据库
	 */
	public void saveCity(City city)
	{
		if(city != null){
		ContentValues contentValues = new ContentValues();
		contentValues.put("city_name", city.getCityName());
		contentValues.put("city_code", city.getCityCode());
		db.insert("City", null, contentValues);
		}
	}
	
	/**
	 * 从数据库里取出某个城市锁对应的code	 */
	public String loadCityCode(String cityname)
	{ 
		String cityCode = "";
		System.out.println("cityname:"+cityname);
		try {
			Cursor cursor = db.query("City", null, "city_name = ?", new String[]{cityname}, null, null, null);
			if(cursor != null)
			{  if(cursor.moveToFirst()){
				cityCode = cursor.getString(cursor.getColumnIndex("city_code"));
				System.out.println("cityCode1:"+cityCode);
			  }else{
				  Log.d("DBUtil.loadCityCode()", "cursor is not null,but not hava code!");
			  }
				
			 }else
			 {
				 Log.d("DBUtil.loadCityCode()", "cursor is null!");
					System.out.println("cityCode2:"+cityCode);
			 }
			return cityCode; 
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("exception ");
		}
		return "0";
	}
	
	 
	
	/**
	 * 从数据库里取出某个省所有城市信息
	 */
	public List<City> loadCitities(int provinceId)
	{
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst())
		{
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				list.add(city);
			} while (cursor.moveToNext());
		}
		return list; 
	}
	

	/**
	 * 查看city表有没有数据，或者数据够不够
	 */
	public boolean getCountBycity()
	{
		boolean b = false;
		
		Cursor cursor = db.query("City", null, null, null, null, null, null);
		if(cursor != null)
		{
			if(cursor.getCount()<2000)
			{
				b = false; 
				 
			}else
			{
				b = true; 
			}
			
		}else
		{
			b = false; 
		}
		return b;
		 
	}
	
	/**
	 * 把县区实体存到数据库里
	 */
	public void saveCounty(County county)
	{
	//准备values
		if(county != null){
		ContentValues contentValues = new ContentValues();
		contentValues.put("county_name", county.getCountyName());
		contentValues.put("county_code", county.getCountyCode());
		db.insert("County", null, contentValues);
		}
	}
	
	/**
	 * 从数据库里查询县By 城市Id
	 */
	public List<County>	loadCounties(int cityId)
	{
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			do {
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
			} while (cursor.moveToNext());
		}
		return list;
	} 
	
	
	
	
}
