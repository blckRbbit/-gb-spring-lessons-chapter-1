package com.blck_rbbit.gbspringlessonschapter1.api.core;

public class OrderDetailsDto {
    private String address;
    private String phone;
    
    public OrderDetailsDto() {
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
