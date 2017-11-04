package tong.cau.com.cautong.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class MyDate {
    private final String TAG = "MyDate";

    private Date ret;
	private String ss = null;

    public MyDate(SimpleDateFormat yyyy_MM_dd_HH_mm, String date){
        try{
            ret = yyyy_MM_dd_HH_mm.parse(date);
        }catch (ParseException e){
            e.printStackTrace();
            ret = new Date();
        }
    }

    public MyDate(String test){
		ss = test;
	}

	public MyDate(long numberDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ss = format.format(new Date(numberDate*1000));
    }

    @Override
    public String toString(){
		if(ss != null)	return ss;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(ret);
    }

}
