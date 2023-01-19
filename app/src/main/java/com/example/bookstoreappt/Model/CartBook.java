package com.example.bookstoreappt.Model;

public class CartBook extends Book{
    String cartBookId, buyerId, qty;

    public CartBook() {
    }
    public CartBook(String bookId, String title, String genre, String author, String edition,
                String releaseDate, String company, String price, String sellerID, String status,
                String cartBookId, String buyerId, String qty) {

        super(bookId, title, genre, author, edition, releaseDate, company, price, sellerID, status);

        this.cartBookId = cartBookId;
        this.buyerId = buyerId;
        this.qty = qty;
    }


    public String getCartBookId() {
        return cartBookId;
    }

    public void setCartBookId(String cartBookId) {
        this.cartBookId = cartBookId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "CartBook{" +
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
                ", cartBookId='" + cartBookId + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", qty='" + qty + '\'' +
                '}';
    }
}
