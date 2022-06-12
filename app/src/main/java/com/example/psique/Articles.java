package com.example.psique;

public class Articles {
    //atributos
    int articlesImageId;
    String articlesTitle;
    String articlesArticleName;
    String articlesLink;

    //constructor
    public Articles(int articlesImageId, String articlesTitle, String articlesArticleName, String src) {
        this.articlesImageId = articlesImageId;
        this.articlesTitle = articlesTitle;
        this.articlesArticleName = articlesArticleName;
        this.articlesLink = src;
    }

    //getters y setters
    public int getArticlesImageId() {
        return articlesImageId;
    }

    public void setArticlesImageId(int articlesImageId) {
        this.articlesImageId = articlesImageId;
    }

    public String getArticlesTitle() {
        return articlesTitle;
    }

    public void setArticlesTitle(String articlesTitle) {
        this.articlesTitle = articlesTitle;
    }

    public String getArticlesArticleName() {
        return articlesArticleName;
    }

    public void setArticlesArticleName(String articlesArticleName) {
        this.articlesArticleName = articlesArticleName;
    }

    public String getArticlesLink() {
        return articlesLink;
    }

    public void setArticlesLink(String articlesLink) {
        this.articlesLink = articlesLink;
    }
}
