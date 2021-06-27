package com.vikram.httppatchinspring.model;

public class Address {
    String district;
    String city;
    String country;

    public Address() {
    }

    public Address(String district, String city, String country) {
        this.district = district;
        this.city = city;
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
