package com.museumbooking.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.museumbooking.R;
import com.museumbooking.activity.AddPaymentActivity;
import com.museumbooking.activity.GoogleMapActivity;
import com.museumbooking.conf.WebserviceConstants;
import com.museumbooking.model.MuseumDetailsModel;
import com.museumbooking.model.ReservationModel;

import java.util.List;

/**
 * Created by Aswathy_G on 10/1/2017.
 */

public class ReservationListAdapter extends BaseAdapter {
    private Context mContext;
    List<ReservationModel> reservationDetailsModelList;

    public ReservationListAdapter(Activity context, List<ReservationModel> reservationDetailsModelList) {
        mContext = context;
        this.reservationDetailsModelList = reservationDetailsModelList;
    }


    @Override
    public int getCount() {
        return reservationDetailsModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ReservationListAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reservation_details, parent, false);
            holder = new ReservationListAdapter.ViewHolder();
            holder.museumName = (TextView) convertView
                    .findViewById(R.id.museumName);
            holder.museumType = (TextView) convertView
                    .findViewById(R.id.museumType);
            holder.paymentStatus = (TextView) convertView
                    .findViewById(R.id.paymentStatus);
            holder.totalPerson = (TextView) convertView
                    .findViewById(R.id.totalPerson);
            holder.date = (TextView) convertView
                    .findViewById(R.id.date);
            holder.addPaymentButton = (Button) convertView
                    .findViewById(R.id.addPaymentButton);

            convertView.setTag(holder);

        } else {
            holder = (ReservationListAdapter.ViewHolder) convertView.getTag();
        }
        holder.museumName.setText("Museum Name: "+reservationDetailsModelList.get(position).getMuseum_name());
        holder.museumType.setText("Museum Type: "+reservationDetailsModelList.get(position).getMuseum_type());
        holder.paymentStatus.setText("Payment Status: "+reservationDetailsModelList.get(position).getPaymentstatus());
        holder.date.setText("Date: "+reservationDetailsModelList.get(position).getDate());
        holder.totalPerson.setText("Total Person: "+reservationDetailsModelList.get(position).getTotal_person());
        if(reservationDetailsModelList.get(position).getPaymentstatus().equalsIgnoreCase("paid"))
        {
            holder.addPaymentButton.setVisibility(View.INVISIBLE);
        }
        holder.addPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mContext.startActivity(new Intent(mContext, AddPaymentActivity.class)
                        .putExtra("resrvation_id", reservationDetailsModelList.get(position).getTicketreservationid())
                        .putExtra("museum_name", reservationDetailsModelList.get(position).getMuseum_name())
                        .putExtra("museum_type", reservationDetailsModelList.get(position).getMuseum_type())
                        .putExtra("date", reservationDetailsModelList.get(position).getDate())

                );
            }
        });


        return convertView;
    }

    private class ViewHolder {
        TextView museumName;
        TextView museumType;
        TextView paymentStatus;
        TextView totalPerson;
        TextView date;
        Button addPaymentButton;
    }


}
