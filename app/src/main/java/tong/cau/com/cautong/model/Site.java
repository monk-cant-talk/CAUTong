package tong.cau.com.cautong.model;

/**
 * Created by Velmont on 2017-10-30.
 */

public class Site {
    private String siteUrl;
    private String ssoUrl;
    private String noticeUrl;
    private String noticeBbsUrl;
    private String encodeType;
    private BbsInfo bbsInfo;
    private String parseType;

    public Boolean getSsoEnabled() {
        return ssoEnabled;
    }

    public void setSsoEnabled(Boolean ssoEnabled) {
        this.ssoEnabled = ssoEnabled;
    }

    private Boolean ssoEnabled;

    public String getParseType() { return parseType; }

    public void setParseType(String parseType) {this.parseType = parseType;}

    public Site(){
        this.bbsInfo = new BbsInfo();
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getSsoUrl() {
        return ssoUrl;
    }

    public void setSsoUrl(String ssoUrl) {
        this.ssoUrl = ssoUrl;
    }

    public String getNoticeUrl() {
        return noticeUrl;
    }

    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl;
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

    public String getNoticeBbsUrl() {
        return noticeBbsUrl;
    }

    public void setNoticeBbsUrl(String noticeBbsUrl) {
        this.noticeBbsUrl = noticeBbsUrl;
    }


}
