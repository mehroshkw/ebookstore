package com.example.bookstoreappt.Model;

public class Order {
    String orderId, buyerId, name, phone, address, orderNote, orderAmount, orderQty, orderStatus,
             timestamp;

    public Order() {
    }

    public Order(String orderId, String buyerId, String name, String phone, String address,
            String orderNote, String orderAmount, String orderQty, String orderStatus, String timestamp) {
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.orderNote = orderNote;
        this.orderAmount = orderAmount;
        this.orderQty = orderQty;
        this.orderStatus = orderStatus;
        this.timestamp = timestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(String orderQty) {
        this.orderQty = orderQty;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", orderNote='" + orderNote + '\'' +
                ", orderAmount='" + orderAmount + '\'' +
                ", orderQty='" + orderQty + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
