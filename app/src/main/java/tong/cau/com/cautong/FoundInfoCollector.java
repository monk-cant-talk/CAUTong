package tong.cau.com.cautong;


import java.util.ArrayList;

//찾은 정보들을 다 갖고있을 클래스
//단 하나만 존재해야 하므로 싱글턴패턴 사용
public class FoundInfoCollector {

	private ArrayList<WindowInfo> list;

	private static FoundInfoCollector instance = null;
	public static FoundInfoCollector getInstance(){
		if(instance == null){
			instance = new FoundInfoCollector();
		}
		return instance;
	}

	private FoundInfoCollector(){
		list = new ArrayList<>();
		findInfo();
	}

	public WindowInfo getInfo(int index) throws NullPointerException{
		if(index < getInfoSize())
			return list.get(index);
		else return null;
	}

	public int getInfoSize(){
		return list.size();
	}

	public void findInfo(){
		for(int i = 0 ; i < 2000 ; i ++) {
			list.add(new WindowInfo());
		}
		list.add(new WindowInfo());
		list.add(new WindowInfo());
		list.add(new WindowInfo());
		list.add(new WindowInfo());
		// TODO: 2017-10-25 여기에서 list 에 정보들을 채워 넣는다. 
	}

}
