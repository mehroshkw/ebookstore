package com.example.bookstoreappt.Model;

public class Book {
    //data member
    String bookId, title, genre, author, edition, releaseDate,
            company, price, sellerID, status;

    public Book() {
    }

    public Book(String bookId, String title, String genre, String author, String edition,
                String releaseDate, String company, String price, String sellerID, String status) {
        this.bookId = bookId;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.edition = edition;
        this.releaseDate = releaseDate;
        this.company = company;
        this.price = price;
        this.sellerID = sellerID;
        this.status = status;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", author='" + author + '\'' +
                ", edition='" + edition + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", company='" + company + '\'' +
                ", price='" + price + '\'' +
                ", sellerID='" + sellerID + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
