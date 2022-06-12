package com.example.psique;


public class News {
    //atributos
    int newsImageId;
    String newsTitle;
    String newsArticleName;
    String newsLink;
    String newsDate;

    //constructor
    public News(int newsImageId, String newsTitle, String newsArticleName, String src, String newsDate) {
        this.newsImageId = newsImageId;
        this.newsTitle = newsTitle;
        this.newsArticleName = newsArticleName;
        this.newsLink = src;
        this.newsDate = newsDate;
    }

    //getters y setters
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

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }
}
