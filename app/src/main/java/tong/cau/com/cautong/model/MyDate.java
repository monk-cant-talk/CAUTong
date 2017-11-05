package tong.cau.com.cautong.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
    private final String TAG = "MyDate";

    final long DATE_CRITERIA = 1000000000000L;

    private Date ret;
    private String ss = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public MyDate(SimpleDateFormat yyyy_MM_dd_HH_mm, String date) {
        try {
            ret = yyyy_MM_dd_HH_mm.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            ret = new Date();
        }
    }

    public MyDate(String test) {
        ss = test;
    }

    public MyDate(long numberDate) {
        if (numberDate < DATE_CRITERIA) {
            numberDate *= 1000;
        }
        ss = sdf.format(new Date(numberDate));
    }

    public long getDateValue() throws ParseException {
        return sdf.parse(ss).getTime();
    }

    @Override
    public String toString() {
        if (ss != null) return ss;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(ret);
    }

}
