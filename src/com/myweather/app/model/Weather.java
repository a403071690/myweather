package com.myweather.app.model;

public class Weather {
	
	private String cityname;
	private String weather1;
	private String wind1;
	private String temperature1;
	private int weathericon1;
	
	private String weather2;
	private String wind2;
	private String temperature2;
	private int weathericon2;
	
	private String weather3;
	private String wind3;
	private String temperature3;
	private int weathericon3;
	
	public Weather() {
		// TODO Auto-generated constructor stub
	}

	public Weather(String cityname, String weather1, String wind1,
			String temperature1, int weathericon1, String weather2,
			String wind2, String temperature2, int weathericon2,
			String weather3, String wind3, String temperature3, int weathericon3) {
		super();
		this.cityname = cityname;
		this.weather1 = weather1;
		this.wind1 = wind1;
		this.temperature1 = temperature1;
		this.weathericon1 = weathericon1;
		this.weather2 = weather2;
		this.wind2 = wind2;
		this.temperature2 = temperature2;
		this.weathericon2 = weathericon2;
		this.weather3 = weather3;
		this.wind3 = wind3;
		this.temperature3 = temperature3;
		this.weathericon3 = weathericon3;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getWeather1() {
		return weather1;
	}

	public void setWeather1(String weather1) {
		this.weather1 = weather1;
	}

	public String getWind1() {
		return wind1;
	}

	public void setWind1(String wind1) {
		this.wind1 = wind1;
	}

	public String getTemperature1() {
		return temperature1;
	}

	public void setTemperature1(String temperature1) {
		this.temperature1 = temperature1;
	}

	public int getWeathericon1() {
		return weathericon1;
	}

	public void setWeathericon1(int weathericon1) {
		this.weathericon1 = weathericon1;
	}

	public String getWeather2() {
		return weather2;
	}

	public void setWeather2(String weather2) {
		this.weather2 = weather2;
	}

	public String getWind2() {
		return wind2;
	}

	public void setWind2(String wind2) {
		this.wind2 = wind2;
	}

	public String getTemperature2() {
		return temperature2;
	}

	public void setTemperature2(String temperature2) {
		this.temperature2 = temperature2;
	}

	public int getWeathericon2() {
		return weathericon2;
	}

	public void setWeathericon2(int weathericon2) {
		this.weathericon2 = weathericon2;
	}

	public String getWeather3() {
		return weather3;
	}

	public void setWeather3(String weather3) {
		this.weather3 = weather3;
	}

	public String getWind3() {
		return wind3;
	}

	public void setWind3(String wind3) {
		this.wind3 = wind3;
	}

	public String getTemperature3() {
		return temperature3;
	}

	public void setTemperature3(String temperature3) {
		this.temperature3 = temperature3;
	}

	public int getWeathericon3() {
		return weathericon3;
	}

	public void setWeathericon3(int weathericon3) {
		this.weathericon3 = weathericon3;
	}
	
	

	
}