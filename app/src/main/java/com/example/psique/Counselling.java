package com.example.psique;

public class Counselling {
    int counsellingImageId;
    String counsellingTitle;
    String counsellingArticleName;
    String counsellingLink;

    public Counselling(int counsellingImageId, String counsellingTitle,String counsellingArticleName,String src) {
        this.counsellingImageId = counsellingImageId;
        this.counsellingTitle = counsellingTitle;
        this.counsellingArticleName=counsellingArticleName;
        this.counsellingLink = src;
    }

    public int getCounsellingImageId() {
        return counsellingImageId;
    }

    public void setCounsellingImageId(int counsellingImageId) {
        this.counsellingImageId = counsellingImageId;
    }

    public String getCounsellingTitle() { return counsellingTitle; }

    public void setCounsellingTitle(String counsellingTitle) { this.counsellingTitle = counsellingTitle; }

    public String getCounsellingArticleName() { return counsellingArticleName; }

    public void setCounsellingArticleName(String counsellingArticleName) {
        this.counsellingArticleName = counsellingArticleName;
    }

    public String getCounsellingLink() {
        return counsellingLink;
    }

    public void setCounsellingLink(String counsellingLink) {
        this.counsellingLink = counsellingLink;
    }
}
