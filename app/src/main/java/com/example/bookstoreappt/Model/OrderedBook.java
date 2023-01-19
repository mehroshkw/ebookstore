package com.example.bookstoreappt.Model;

public class OrderedBook extends Book{
    String orderedBookId, orderId, qty;

    public OrderedBook() {
    }

    public OrderedBook(String bookId, String title, String genre, String author,
                       String edition, String releaseDate, String company, String price, String sellerID,
                       String status, String orderedBookId, String orderId, String qty) {
        super(bookId, title, genre, author, edition, releaseDate, company, price, sellerID, status);
        this.orderedBookId = orderedBookId;
        this.orderId = orderId;
        this.qty = qty;
    }

    public String getOrderedBookId() {
        return orderedBookId;
    }

    public void setOrderedBookId(String orderedBookId) {
        this.orderedBookId = orderedBookId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "OrderedBook{" +
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
                ", orderedBookId='" + orderedBookId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", qty='" + qty + '\'' +
                '}';
    }
}
