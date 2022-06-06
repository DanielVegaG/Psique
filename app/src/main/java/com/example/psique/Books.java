package com.example.psique;

public class Books {
    int booksImageId;
    String booksTitle;
    String booksArticleName;
    String booksLink;

    public Books(int booksImageId, String booksTitle, String booksArticleName, String src) {
        this.booksImageId = booksImageId;
        this.booksTitle = booksTitle;
        this.booksArticleName = booksArticleName;
        this.booksLink = src;
    }

    public int getBooksImageId() {
        return booksImageId;
    }

    public void setBooksImageId(int booksImageId) {
        this.booksImageId = booksImageId;
    }

    public String getBooksTitle() {
        return booksTitle;
    }

    public void setBooksTitle(String booksTitle) {
        this.booksTitle = booksTitle;
    }

    public String getBooksArticleName() {
        return booksArticleName;
    }

    public void setBooksArticleName(String booksArticleName) {
        this.booksArticleName = booksArticleName;
    }

    public String getBooksLink() {
        return booksLink;
    }

    public void setBooksLink(String booksLink) {
        this.booksLink = booksLink;
    }
}
