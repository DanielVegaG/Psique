package com.example.psique;

public class News {
    int newsImageId;
    String newsTitle;
    String newsArticleName;
    String newsLink;

    public News(int newsImageId, String newsTitle, String newsArticleName, String src) {
        this.newsImageId = newsImageId;
        this.newsTitle = newsTitle;
        this.newsArticleName = newsArticleName;
        this.newsLink = src;
    }

    public int getNewsImageId() {
        return newsImageId;
    }

    public void setNewsImageId(int newsImageId) {
        this.newsImageId = newsImageId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsArticleName() {
        return newsArticleName;
    }

    public void setNewsArticleName(String newsArticleName) {
        this.newsArticleName = newsArticleName;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }
}
