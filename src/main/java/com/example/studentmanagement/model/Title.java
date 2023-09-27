package com.example.studentmanagement.model;


public class Title {
    private String address;

    private Long status;

    public Title() {
    }

    public Title(String address, Long status) {
        this.address = address;
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
