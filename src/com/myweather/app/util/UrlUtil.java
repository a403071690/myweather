package com.myweather.app.util;

import javax.crypto.Mac;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import android.util.Log;
 
public class UrlUtil {
 
    private static final char last2byte = (char) Integer.parseInt("00000011", 2);
    private static final char last4byte = (char) Integer.parseInt("00001111", 2);
    private static final char last6byte = (char) Integer.parseInt("00111111", 2);
    private static final char lead6byte = (char) Integer.parseInt("11111100", 2);
    private static final char lead4byte = (char) Integer.parseInt("11110000", 2);
    private static final char lead2byte = (char) Integer.parseInt("11000000", 2);
    private static final char[] encodeTable = new char[] { 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'
    };
 
    public static String standardURLEncoder(String data, String key) {
        byte[] byteHMAC = null;
        String urlEncoder = "";
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            mac.init(spec);
            byteHMAC = mac.doFinal(data.getBytes());
            if (byteHMAC != null) {
                String oauth = encode(byteHMAC);
                if (oauth != null) {
                    urlEncoder = URLEncoder.encode(oauth, "utf8");
                }
            }
        } catch (InvalidKeyException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return urlEncoder;
    }
 
    public static String encode(byte[] from) {
        StringBuffer to = new StringBuffer((int) (from.length * 1.34) + 3);
        int num = 0;
        char currentByte = 0;
        for (int i = 0; i < from.length; i++) {
            num = num % 8;
            while (num < 8) {
                switch (num) {
                case 0:
                    currentByte = (char) (from[i] & lead6byte);
                    currentByte = (char) (currentByte >>> 2);
                    break;
                case 2:
                    currentByte = (char) (from[i] & last6byte);
                    break;
                case 4:
                    currentByte = (char) (from[i] & last4byte);
                    currentByte = (char) (currentByte << 2);
                    if ((i + 1) < from.length) {
                        currentByte |= (from[i + 1] & lead2byte) >>> 6;
                    }
                    break;
                case 6:
                    currentByte = (char) (from[i] & last2byte);
                    currentByte = (char) (currentByte << 4);
                    if ((i + 1) < from.length) {
                        currentByte |= (from[i + 1] & lead4byte) >>> 4;
                    }
                    break;
                }
                to.append(encodeTable[currentByte]);
                num += 6;
            }
        }
        if (to.length() % 4 != 0) {
            for (int i = 4 - to.length() % 4; i > 0; i--) {
                to.append("=");
            }
        }
        return to.toString();
    }
     
    /**
     * 获得url（中国气象网请求连接 访问此url返回json格式天气信息）  
     * @param appid
     * @param type
     * @param key
     * @return Url
     */
    public static String getUrl(String id,  String type, String appid,String key)
    {
    	//获取系统时间
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");       
    	Date curDate  = new Date(System.currentTimeMillis());//获取当前时间       
    	String date = formatter.format(curDate);
    	String data ="http://open.weather.com.cn/data/?areaid="+id+"&type="+type+"&date="+date+"&appid="+appid;
    	//得到加密后的data
        String str = standardURLEncoder(data, key);
    	String url ="http://open.weather.com.cn/data/?areaid="+id+"&type="+type+"&date="+date+"&appid="+appid.substring(0, 6)+"&key="+str;
    	Log.d("URLUtil", "url:"+url);
		return url;
    }
    
    public static void main(String[] args) {
        try {
        	/*
        	 * Hi,a403071690
        	 * 403071690@qq.com
        	 * AppId：55119730f0590d61 
        	 * Private_Key：474ec2_SmartWeatherAPI_2ad10e0
        	 * http://open.weather.com.cn/data/?areaid=101010100&type=forecast_f&date=201212010741&appid=551197&key=474ec2_SmartWeatherAPI_2ad10e0
        	 */
            //需要加密的数据  
            String data = "http://open.weather.com.cn/data/?areaid=101010100&type=forecast_f&date=201511101110&appid=55119730f0590d61";  
            //密钥  
            String key = "474ec2_SmartWeatherAPI_2ad10e0";  
             
            String str = standardURLEncoder(data, key);
 
            System.out.println(str);
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}