package com.example.noticeapppro;

public class Report {

    String reportId;
    String reportTitle;
    String reportUrl;
    String reportDate;
    String reportTime;
    String reportBy;

    public Report(String reportId, String reportTitle, String reportUrl, String reportDate, String reportTime, String reportBy) {
        this.reportId = reportId;
        this.reportTitle = reportTitle;
        this.reportUrl = reportUrl;
        this.reportDate = reportDate;
        this.reportTime = reportTime;
        this.reportBy = reportBy;
    }

    public Report(){}

    public String getReportBy() {
        return reportBy;
    }

    public void setReportBy(String reportBy) {
        this.reportBy = reportBy;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }
}
