package com.example.hotelandroid.model;

import java.math.BigDecimal;

public class Room {
    private int roomno;
    private String roomtype;
    private BigDecimal price;
    private String status;
    private String imagename;

    public Room()
    {

    }

    public Room(int roomno, String roomtype, BigDecimal price, String status, String imagename) {
        this.roomno = roomno;
        this.roomtype = roomtype;
        this.price = price;
        this.status = status;
        this.imagename = imagename;
    }

    public int getRoomno() {
        return roomno;
    }

    public void setRoomno(int roomno) {
        this.roomno = roomno;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }
}
