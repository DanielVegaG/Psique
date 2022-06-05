package com.example.psique;


public class Help {
    int helpImageId;
    String helpTitle;
    String helpArticleName;
    String helpLink;

    public Help(int helpImageId, String helpTitle,String helpArticleName,String src) {
        this.helpImageId = helpImageId;
        this.helpTitle = helpTitle;
        this.helpArticleName=helpArticleName;
        this.helpLink = src;
    }

    public int getHelpImageId() {
        return helpImageId;
    }

    public void setHelpImageId(int helpImageId) {
        this.helpImageId = helpImageId;
    }

    public String getHelpTitle() { return helpTitle; }

    public void setHelpTitle(String helpTitle) { this.helpTitle = helpTitle; }

    public String getHelpArticleName() { return helpArticleName; }

    public void setHelpArticleName(String helpArticleName) {
        this.helpArticleName = helpArticleName;
    }

    public String getHelpLink() {
        return helpLink;
    }

    public void setHelpLink(String helpLink) {
        this.helpLink = helpLink;
    }
}
