package com.example.noticeapppro;

public class Material {

    String materialTitle;
    String materialUrl;
    String materialDate;
    String materialTime;
    String materialId;
    String materialBy;

    public Material(String materialId, String materialTitle, String materialUrl, String materialDate, String materialTime, String materialBy) {
        this.materialId=materialId;
        this.materialTitle = materialTitle;
        this.materialUrl = materialUrl;
        this.materialDate = materialDate;
        this.materialTime = materialTime;
        this.materialBy = materialBy;
    }

    public Material()
    {
    }

    public String getMaterialBy() {
        return materialBy;
    }

    public void setMaterialBy(String materialBy) {
        this.materialBy = materialBy;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialTitle() {
        return materialTitle;
    }

    public void setMaterialTitle(String materialTitle) {
        this.materialTitle = materialTitle;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
    }

    public String getMaterialDate() {
        return materialDate;
    }

    public void setMaterialDate(String materialDate) {
        this.materialDate = materialDate;
    }

    public String getMaterialTime() {
        return materialTime;
    }

    public void setMaterialTime(String materialTime) {
        this.materialTime = materialTime;
    }
}
