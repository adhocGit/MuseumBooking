package com.museumbooking.model;

/**
 * Created by Aswathy_G on 10/2/2017.
 */

public class PaymentDetailsModel {
    String amount;
    String museumtype;
    String paymentdate;
    String museumname;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMuseumtype() {
        return museumtype;
    }

    public void setMuseumtype(String museumtype) {
        this.museumtype = museumtype;
    }

    public String getPaymentdate() {
        return paymentdate;
    }

    public void setPaymentdate(String paymentdate) {
        this.paymentdate = paymentdate;
    }

    public String getMuseumname() {
        return museumname;
    }

    public void setMuseumname(String museumname) {
        this.museumname = museumname;
    }
}
