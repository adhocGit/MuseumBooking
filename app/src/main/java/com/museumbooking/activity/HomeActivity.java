package com.museumbooking.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.museumbooking.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    TextView ticketReservation,informationDetails,reservationDetails,viewPayment,trackLocation,sendFeedback,viewProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }
    private void initView() {
        ticketReservation= (TextView) findViewById(R.id.ticketReservation);
        ticketReservation.setOnClickListener(this);
        informationDetails= (TextView) findViewById(R.id.informationDetails);
        informationDetails.setOnClickListener(this);
        reservationDetails= (TextView) findViewById(R.id.reservationDetails);
        reservationDetails.setOnClickListener(this);
        viewPayment= (TextView) findViewById(R.id.viewPayment);
        viewPayment.setOnClickListener(this);
        trackLocation= (TextView) findViewById(R.id.trackLocation);
        trackLocation.setOnClickListener(this);
        sendFeedback= (TextView) findViewById(R.id.sendFeedback);
        sendFeedback.setOnClickListener(this);
        viewProfile= (TextView) findViewById(R.id.viewProfile);
        viewProfile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ticketReservation:
                startActivity(new Intent(HomeActivity.this, TicketReservation.class));
                break;
            case R.id.informationDetails:
                startActivity(new Intent(HomeActivity.this, InformationDetailsActivty.class));
                break;
            case R.id.reservationDetails:
                startActivity(new Intent(HomeActivity.this, ReservationDetailsActivity.class));
                break;
            case R.id.viewPayment:
                startActivity(new Intent(HomeActivity.this, ViewPaymentActivity.class));
                break;
            case R.id.trackLocation:
                startActivity(new Intent(HomeActivity.this, TrackLocationActivity.class));
                break;
            case R.id.sendFeedback:
                startActivity(new Intent(HomeActivity.this, FeedbackActivity.class));
                break;
            case R.id.viewProfile:
                startActivity(new Intent(HomeActivity.this, RegisterActivity.class).putExtra("page","profile"));
                break;
        }
    }
}
