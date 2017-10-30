package tong.cau.com.cautong;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {

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
        ss = format.format(new Date(numberDate));
    }

    @Override
    public String toString(){
		if(ss != null)	return ss;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int y = ret.getYear();
        if(y < 1000){
            if(y < 50) {
                y += 2000;
            }else {
                y += 1000;
            }
        }
        ret.setYear(y);
        return format.format(ret);
    }

}
