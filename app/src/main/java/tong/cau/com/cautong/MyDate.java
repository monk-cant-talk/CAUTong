package tong.cau.com.cautong;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {

    private enum dateType {
        str, etc
    }

    private String year = null;
    private String month = null;
    private String date = null;
    private String hour = null;
    private String minute = null;
    private String fullT = null;
    private dateType type;

    public MyDate(String year, String month, String date, String hour, String minute) throws NumberFormatException {
        this.year = year;
        int y = Integer.parseInt(year);
        if (y < 1000) y += 2000;
        this.year = y + "";

        this.month = month;
        if (month.length() == 1) this.month = "0" + this.month;
        this.date = date;
        if (date.length() == 1) this.date = "0" + this.date;
        this.hour = hour;
        if (hour.length() == 1) this.hour = "0" + this.hour;
        this.minute = minute;
        if (minute.length() == 1) this.minute = "0" + this.minute;
        type = dateType.str;
    }

    public MyDate(int year, int month, int date, int hour, int minute) throws NumberFormatException {
        this(year + "", month + "", date + "", hour + "", minute + "");
    }

    public MyDate(String year, String month, String date) throws NumberFormatException {
        this.year = year;
        int y = Integer.parseInt(year);
        if (y < 1000) y += 2000;
        this.year = y + "";

        this.month = month;
        if (month.length() == 1) this.month = "0" + this.month;
        this.date = date;
        if (date.length() == 1) this.date = "0" + this.date;
        type = dateType.str;
    }

    public MyDate(int year, int month, int date) throws NumberFormatException {
        this(year + "", month + "", date + "");
    }

    //입력을 넣는대로 출력댐
    public MyDate(String YYYY_MM_dd_HH_mm) {
        fullT = YYYY_MM_dd_HH_mm;
        type = dateType.etc;
    }

    public MyDate(long numberDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        fullT = sdf.format(new Date(numberDate));
        type = dateType.etc;
    }

    @Override
    public String toString() {
        switch (type) {
            case etc:
                return fullT;
            case str:
                StringBuilder sb = new StringBuilder();
                sb.append(year);
                sb.append("-");
                sb.append(month);
                sb.append("-");
                sb.append(date);
                if (hour != null) {
                    sb.append("-");
                    sb.append(hour);
                }
                if (minute != null) {
                    sb.append("-");
                    sb.append(minute);
                }
                return sb.toString();
        }
        return "noDate";
    }


}
