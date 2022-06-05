package com.example.psique;

public class Info {
    int infoImageId;
    String infoTitle;
    String infoArticleName;
    String infoLink;

    public Info(int infoImageId, String infoTitle,String infoArticleName,String src) {
        this.infoImageId = infoImageId;
        this.infoTitle = infoTitle;
        this.infoArticleName=infoArticleName;
        this.infoLink = src;
    }

    public int getInfoImageId() {
        return infoImageId;
    }

    public void setInfoImageId(int infoImageId) {
        this.infoImageId = infoImageId;
    }

    public String getInfoTitle() { return infoTitle; }

    public void setInfoTitle(String infoTitle) { this.infoTitle = infoTitle; }

    public String getInfoArticleName() { return infoArticleName; }

    public void setInfoArticleName(String infoArticleName) {
        this.infoArticleName = infoArticleName;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }
}
