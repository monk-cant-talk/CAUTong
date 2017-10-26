package tong.cau.com.cautong;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class MainAdapter extends BaseAdapter{


	private Activity activity;
	private ArrayList<LinearLayout> layout_list;
	public ViewGroup parent = null;


	public MainAdapter(Activity activity) {
		this.activity = activity;
		layout_list = new ArrayList<>();
		for(int i = 0 ; i < 5 ; i ++){
			addWindowInfo(FoundInfoCollector.getInstance().getInfo(i));
		}
	}

	public void addWindowInfo(WindowInfo info){
		LinearLayout layout = info.getLayout(activity);
		layout_list.add(layout);
		//if(parent != null)
			//parent.addView(layout);
	}

	// ListView에서 보여줘야 할 데이터 개수 지정 (ArrayList 크기로 지정)
	@Override
	public int getCount() {
		return layout_list.size();
	}

	// ListView에서 보여줄 객체 지정
	@Override
	public Object getItem(int position) {
		return layout_list.get(position);
	}

	// getItem() 메서드가 리턴한 객체의 고유 식별자
	@Override
	public long getItemId(int position) {
		return position;
	}

	// ListView에 실제로 행을 보여줄 때 호출되는 메서드
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = layout_list.get(position);
		return convertView;
	}
}
