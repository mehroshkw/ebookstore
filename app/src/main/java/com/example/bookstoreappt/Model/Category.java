package com.example.bookstoreappt.Model;

public class Category {
    //data members
    String catID, title, author;

    public Category() {
    }

    public Category(String catID, String title, String author) {
        this.catID = catID;
        this.title = title;
        this.author = author;
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
