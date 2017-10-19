package com.museumbooking.model;

/**
 * Created by Aswathy_G on 10/1/2017.
 */

public class MuseumNameModel extends MuseumModel {

    public String getMuseumid() {
        return museumid;
    }

    public void setMuseumid(String museumid) {
        this.museumid = museumid;
    }

    String museum_name;
    String museumid;

    public String getMuseum_name() {
        return museum_name;
    }

    public void setMuseum_name(String museum_name) {
        this.museum_name = museum_name;
    }

    @Override
    public String toString() {
        return "ClassPojo [museum_name = " + museum_name + "]";
    }
}
