package tong.cau.com.cautong.model;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;

import tong.cau.com.cautong.WindowMenuStarDialog;
import tong.cau.com.cautong.main.MainActivity;
import tong.cau.com.cautong.R;
import tong.cau.com.cautong.WindowMenuDialog;
import tong.cau.com.cautong.utility.StarHelper;

//전체 뷰 중에서 기사를 하나 찾게 되면 기사에 관련한 윈도우를 띄워야 하는데 그 정보를 아래에 채워 넣는다.
public class WindowInfo {

    public WindowInfo() {
    }

    //즐겨찾기인가
	public boolean star = false;

    //윈도우에 띄울 로고 이미지
    private Logo logo;

    //윈도우에 띄울 제목
    private String title;

    //윈도우에 띄울 컨텐츠
    private String content;

    //소스정보 (ex. https://www.cau.ac.kr/)
    private String link;

    //날짜
    private MyDate date;

    //작성자
    private String author;

    //게시글이 속한 사이트
    private String siteId;


    transient Activity activity;

    transient LinearLayout info_window;
    transient RelativeLayout info_logo;
    transient RelativeLayout info_title_board;
    transient TextView info_writer;
    transient TextView info_title;
    transient TextView info_date;
    transient Button info_menu;
    transient LinearLayout ret;

    @Override
    public boolean equals(Object o){
        WindowInfo windowInfo = (WindowInfo)o;
        return windowInfo.getTitle().equals(this.getTitle()) && windowInfo.getDate().toString().equals(this.getDate().toString());
    }


    public Logo getLogo() {
        return logo;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getLink() {
        return link;
    }

    public MyDate getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public void rePrint(Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info_logo.setBackgroundResource(getLogoImage());
                info_title.setText(WindowInfo.this.title);
                info_date.setText(WindowInfo.this.date.toString());
                info_writer.setText(WindowInfo.this.author);
            }
        });
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public void updateLogo(Activity activity, Logo logo) {
        this.logo = logo;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info_logo.setBackgroundResource(getLogoImage());
            }
        });
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void updateTitle(Activity activity, String title) {
        this.title = title;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info_title.setText(WindowInfo.this.title);
            }
        });
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Activity activity, MyDate date) {
        this.date = date;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info_date.setText(WindowInfo.this.date.toString());
            }
        });
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void updateAuthor(Activity activity, String author) {
        this.author = author;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info_writer.setText(WindowInfo.this.author);
            }
        });
    }

    public void init(Logo logo, String title, String content, String link, MyDate date, String author) {
        this.logo = logo;
        this.title = title;
        this.content = content;
        this.link = link;
        this.date = date;
        this.author = author;
    }


    //변수값이 변했으면 적용한다.
    public void print() {
        info_title.setText(title);
        info_writer.setText(author);
        info_date.setText(date.toString());
        info_logo.setBackgroundResource(getLogoImage());
        info_title_board.setBackgroundResource(getLogoColor());
    }
    public LinearLayout getLayout(Activity activity) {
        this.activity = activity;
        ret = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.main_window_info, null);
        info_window = ret.findViewById(R.id.window_info_window);
        info_logo = ret.findViewById(R.id.window_info_logo);
        info_title_board = ret.findViewById(R.id.window_info_title_board);
        info_writer = ret.findViewById(R.id.window_info_writer);
        info_title = ret.findViewById(R.id.window_info_title);
        info_date = ret.findViewById(R.id.window_info_date);
        info_menu = ret.findViewById(R.id.window_info_menu);

        info_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (WindowInfo.this.link != null) {
                    if (WindowInfo.this.activity != null) {
                        WindowInfo.this.activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WindowInfo.this.link));
                                WindowInfo.this.activity.startActivity(intent);
                            }
                        });
                    }
                }
            }
        });

        info_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				if(star)
					ShowWindowMenuStarDialog();
				else
					ShowWindowMenuDialog();
            }
        });

        print();
        return ret;
    }

	private void ShowWindowMenuStarDialog(){
		WindowMenuStarDialog dialog = new WindowMenuStarDialog(WindowInfo.this.activity,
				new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//임시 삭제 로직
				StarHelper.removedStarredWindowInfo(WindowInfo.this);

				//TODO 즐겨찾기 갱신 필요
				MainActivity.instance.adapter.star.layout.removeAllViewsInLayout();
				List<WindowInfo> windowInfoList = StarHelper.getStarredWindowInfo();
				for(WindowInfo windowInfo : windowInfoList) {
					windowInfo.star = true;
					MainActivity.instance.adapter.star.layout.addView(windowInfo.getLayout(MainActivity.instance));
				}
			}
		});
		dialog.show();
	}

	private void ShowWindowMenuDialog(){
		WindowMenuDialog dialog = new WindowMenuDialog(WindowInfo.this.activity,
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(StarHelper.starWindowInfo(WindowInfo.this))
							Toast.makeText(WindowInfo.this.activity, "즐겨찾기에 추가되었습니다", Toast.LENGTH_SHORT).show();
						else
							Toast.makeText(WindowInfo.this.activity, "이미 즐겨찾기에 있는 게시물입니다", Toast.LENGTH_SHORT).show();
					}
				}, new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(WindowInfo.this.activity, "더이상 알림을 받지 않습니다", Toast.LENGTH_SHORT).show();
			}
		}, new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(WindowInfo.this.activity, "더이상 같은 게시판의 알림을 받지 않습니다", Toast.LENGTH_SHORT).show();
				MainActivity.siteMap.get(WindowInfo.this.siteId).setEnabled(false);
				MainActivity.instance.refreshWindowInfo();
			}
		});
		dialog.show();
	}

    public enum Logo {
        notice, unknown, social, caucse, ict, cauie
    }

    private int getLogoImage() {
        switch (logo) {
            case notice:
                return R.drawable.logo_cau;
            case unknown:
                return R.drawable.c;
            case social:
                return R.drawable.logo_social;
            case caucse:
                return R.drawable.logo_cse;
            case cauie:
                return R.drawable.logo_cauie;
            case ict:
                return R.drawable.logo_ict;
        }
        return R.drawable.c;
    }

    private int getLogoColor() {
        switch (logo) {
            case notice:
                return R.drawable.window_board_title_caunotice;
            case unknown:
                return R.drawable.window_board_title_unknown;
            case social:
                return R.drawable.window_board_title_social;
            case caucse:
                return R.drawable.window_board_title_caucse;
            case cauie:
                return R.drawable.window_board_title_cauie;
            case ict:
                return R.drawable.window_board_title_ict;
        }
        return R.drawable.c;
    }

    public long getDateValue() {
        try {
            return getDate().getDateValue();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }


}
