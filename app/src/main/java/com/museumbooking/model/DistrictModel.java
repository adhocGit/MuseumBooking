package com.museumbooking.model;

/**
 * Created by Aswathy_G on 9/30/2017.
 */

public class DistrictModel extends MuseumModel  {
    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {

        return district;
    }

    String district;
    @Override
    public String toString()
    {
        return "ClassPojo [district = "+district+"]";
    }
}
