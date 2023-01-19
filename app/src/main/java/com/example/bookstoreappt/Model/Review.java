package com.example.bookstoreappt.Model;

public class Review {
    String reviewId, bookId, userUid, review;
    float rating, totalRating;

    public Review() {
    }

    public Review(String reviewId, String bookId, String userUid, String review,
                  float rating, float totalRating) {
        this.reviewId = reviewId;
        this.bookId = bookId;
        this.userUid = userUid;
        this.review = review;
        this.rating = rating;
        this.totalRating = totalRating;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(float totalRating) {
        this.totalRating = totalRating;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId='" + reviewId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", userUid='" + userUid + '\'' +
                ", review='" + review + '\'' +
                ", rating=" + rating +
                ", totalRating=" + totalRating +
                '}';
    }
}
