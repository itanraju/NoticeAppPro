package com.example.noticeapppro;

public class Notice {

    String noticeImgUrl;
    String noticeTitle;
    String noticeId;
    String noticeDate;
    String noticeTime;
    String noticeby;

    public Notice(String noticeId, String noticeImgUrl, String noticeTitle, String noticeDate, String noticeTime, String noticeby) {
        this.noticeId=noticeId;
        this.noticeImgUrl = noticeImgUrl;
        this.noticeTitle = noticeTitle;
        this.noticeDate = noticeDate;
        this.noticeTime = noticeTime;
        this.noticeby = noticeby;
    }

    public Notice() {
    }

    public String getNoticeby() {
        return noticeby;
    }

    public void setNoticeby(String noticeby) {
        this.noticeby = noticeby;
    }

    public String getNoticeImgUrl() {
        return noticeImgUrl;
    }

    public void setNoticeImgUrl(String noticeImgUrl) {
        this.noticeImgUrl = noticeImgUrl;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }
}
