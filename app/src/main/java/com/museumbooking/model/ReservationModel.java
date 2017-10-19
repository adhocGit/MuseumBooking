package com.museumbooking.model;

/**
 * Created by Aswathy_G on 10/1/2017.
 */

public class ReservationModel extends MuseumModel{
    String paymentstatus;
    String museum_name;
    String museum_type;
    String visitstatus;
    String total_person;
    String date;
    String ticketreservationid;

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public String getMuseum_name() {
        return museum_name;
    }

    public void setMuseum_name(String museum_name) {
        this.museum_name = museum_name;
    }

    public String getMuseum_type() {
        return museum_type;
    }

    public void setMuseum_type(String museum_type) {
        this.museum_type = museum_type;
    }

    public String getVisitstatus() {
        return visitstatus;
    }

    public void setVisitstatus(String visitstatus) {
        this.visitstatus = visitstatus;
    }

    public String getTotal_person() {
        return total_person;
    }

    public void setTotal_person(String total_person) {
        this.total_person = total_person;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTicketreservationid() {
        return ticketreservationid;
    }

    public void setTicketreservationid(String ticketreservationid) {
        this.ticketreservationid = ticketreservationid;
    }
}
