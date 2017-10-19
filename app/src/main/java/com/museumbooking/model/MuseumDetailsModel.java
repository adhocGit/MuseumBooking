package com.museumbooking.model;

/**
 * Created by Aswathy_G on 10/1/2017.
 */

public class MuseumDetailsModel extends MuseumModel{
    String museumid;
    String museum_name;
    String address;
    String museum_type;
    String photo;
    String phoneno;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    String latitude;

    public String getMuseumid() {
        return museumid;
    }

    public void setMuseumid(String museumid) {
        this.museumid = museumid;
    }

    public String getMuseum_name() {
        return museum_name;
    }

    public void setMuseum_name(String museum_name) {
        this.museum_name = museum_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMuseum_type() {
        return museum_type;
    }

    public void setMuseum_type(String museum_type) {
        this.museum_type = museum_type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
