package com.example.bookstoreappt.Model;

public class Complaint {
    String complaintId, title, complaint, status, buyerId, sellerId, timestamp;
    String response;

    public Complaint() {
    }

    public Complaint(String complaintId, String title, String complaint, String status,
                     String buyerId, String sellerId, String timestamp, String response) {
        this.complaintId = complaintId;
        this.title = title;
        this.complaint = complaint;
        this.status = status;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.timestamp = timestamp;
        this.response = response;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "complaintId='" + complaintId + '\'' +
                ", title='" + title + '\'' +
                ", complaint='" + complaint + '\'' +
                ", status='" + status + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
