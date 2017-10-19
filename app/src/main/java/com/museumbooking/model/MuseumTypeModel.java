package com.museumbooking.model;

/**
 * Created by Aswathy_G on 10/1/2017.
 */

public class MuseumTypeModel extends MuseumModel {
    public void setMuseumType(String museum_type) {
        this.museum_type = museum_type;
    }

    public String getMuseumType() {

        return museum_type;
    }

    String museum_type;
    @Override
    public String toString()
    {
        return "ClassPojo [museum_type = "+museum_type+"]";
    }
}
