package tong.cau.com.cautong;


import android.app.Activity;

import java.util.ArrayList;

//찾은 정보들을 다 갖고있을 클래스
//단 하나만 존재해야 하므로 싱글턴패턴 사용
public class FoundInfoCollector {

	private static ArrayList<WindowInfo> list = new ArrayList<>();


	public static WindowInfo getInfo(int index) throws NullPointerException{
		if(index < getInfoSize())
			return list.get(index);
		else return null;
	}

	public static int getInfoSize(){
		return list.size();
	}

	public static void findInfo(Activity activity){
		for(int i = 0 ; i < 200 ; i ++) {
			list.add(new WindowInfo(activity));
		}
		list.add(new WindowInfo(activity));
		list.add(new WindowInfo(activity));
		list.add(new WindowInfo(activity));
		list.add(new WindowInfo(activity));
		// TODO: 2017-10-25 여기에서 list 에 정보들을 채워 넣는다. 
	}
}
