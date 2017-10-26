package tong.cau.com.cautong;


import android.app.Activity;
import android.widget.AbsListView;

public class MainScrollListener implements AbsListView.OnScrollListener{

	Activity activity;

	public MainScrollListener(Activity activity){
		this.activity = activity;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}
}
