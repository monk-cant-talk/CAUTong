package tong.cau.com.cautong.model;

/**
 * Created by Velmont on 2017-10-30.
 */

public class Site {
    private boolean enabled;
    private String name;
    private String id;
    private boolean ssoEnabled;
    private String ssoUrl;
    private String baseUrl;
    private String bbsBaseUrl;

    private String bbsListParams;
    private String pageParam;
    private String encode;
    private BbsInfo bbsInfo;
    private Board[] boardList;

    private String encodeType;
    private String parseType;
    private String testUrl;

    public Site() {
        this.bbsInfo = new BbsInfo();
    }

    public String getBoardUrl(String boardName, int pageNum) {
        for (Board board : boardList) {
            if (board.getName().equals(boardName)) {
                return getBaseUrl() + getBbsListParams() + board.getCategory() + getPageParam(pageNum);

            }
        }
        return null;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSsoEnabled() {
        return ssoEnabled;
    }

    public void setSsoEnabled(boolean ssoEnabled) {
        this.ssoEnabled = ssoEnabled;
    }

    public Boolean getSsoEnabled() {
        return ssoEnabled;
    }

    public void setSsoEnabled(Boolean ssoEnabled) {
        this.ssoEnabled = ssoEnabled;
    }

    public String getParseType() {
        return parseType;
    }

    public void setParseType(String parseType) {
        this.parseType = parseType;
    }

    public String getSsoUrl() {
        return ssoUrl;
    }

    public void setSsoUrl(String ssoUrl) {
        this.ssoUrl = ssoUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBbsBaseUrl() {
        return bbsBaseUrl;
    }

    public void setBbsBaseUrl(String bbsBaseUrl) {
        this.bbsBaseUrl = bbsBaseUrl;
    }

    public String getBbsListParams() {
        return bbsListParams;
    }

    public void setBbsListParams(String bbsListParams) {
        this.bbsListParams = bbsListParams;
    }

    public String getPageParam(int pageNum) {
        if (pageParam == null)
            return "";
        else if (pageNum == 0) {
            return pageParam;
        } else
            return pageParam + pageNum;
    }

    public void setPageParam(String pageParam) {
        this.pageParam = pageParam;
    }

    public String getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(String encodeType) {
        this.encodeType = encodeType;
    }

    public BbsInfo getBbsInfo() {
        return bbsInfo;
    }

    public void setBbsInfo(BbsInfo bbsInfo) {
        this.bbsInfo = bbsInfo;
    }

    public Board getBoard(int index) {
        return boardList[index];
    }

    public Board[] getBoardList() {
        return boardList;
    }

    public void setBoardList(Board[] boardList) {
        this.boardList = boardList;
    }

    public String getTestUrl() {
        return testUrl;
    }

    public void setTestUrl(String testUrl) {
        this.testUrl = testUrl;
    }
}
