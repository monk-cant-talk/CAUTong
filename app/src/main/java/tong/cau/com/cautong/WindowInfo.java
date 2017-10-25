package tong.cau.com.cautong;


//전체 뷰 중에서 기사를 하나 찾게 되면 기사에 관련한 윈도우를 띄워야 하는데 그 정보를 아래에 채워 넣는다.
public class WindowInfo {

	//윈도우에 띄울 로고 이미지
	public Logo logo;

	//윈도우에 띄울 제목
	public String title;

	//윈도우에 띄울 컨텐츠
	public String content;

	//소스정보 (ex. https://www.cau.ac.kr/)
	public String link;

	public enum Logo {
		main, unknown
	}
}
