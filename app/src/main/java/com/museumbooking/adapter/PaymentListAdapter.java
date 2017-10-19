package com.museumbooking.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.museumbooking.R;
import com.museumbooking.activity.AddPaymentActivity;
import com.museumbooking.model.PaymentDetailsModel;
import com.museumbooking.model.ReservationModel;

import java.util.List;

/**
 * Created by Aswathy_G on 10/2/2017.
 */


public class PaymentListAdapter extends BaseAdapter {
    private Context mContext;
    List<PaymentDetailsModel> paymentDetailsModelList;

    public PaymentListAdapter(Activity context, List<PaymentDetailsModel> paymentDetailsModelList) {
        mContext = context;
        this.paymentDetailsModelList = paymentDetailsModelList;
    }


    @Override
    public int getCount() {
        return paymentDetailsModelList.size();
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
        PaymentListAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_payment_details, parent, false);
            holder = new PaymentListAdapter.ViewHolder();
            holder.museumName = (TextView) convertView
                    .findViewById(R.id.museumName);
            holder.museumType = (TextView) convertView
                    .findViewById(R.id.museumType);
            holder.amount = (TextView) convertView
                    .findViewById(R.id.amount);
            holder.paymentDate = (TextView) convertView
                    .findViewById(R.id.paymentDate);

            convertView.setTag(holder);

        } else {
            holder = (PaymentListAdapter.ViewHolder) convertView.getTag();
        }
        holder.museumName.setText("Museum Name: "+paymentDetailsModelList.get(position).getMuseumname());
        holder.museumType.setText("Museum Type: "+paymentDetailsModelList.get(position).getMuseumtype());
        holder.amount.setText(paymentDetailsModelList.get(position).getAmount());
        holder.paymentDate.setText("Date: "+paymentDetailsModelList.get(position).getPaymentdate());

        return convertView;
    }

    private class ViewHolder {
        TextView museumName;
        TextView museumType;
        TextView amount;
        TextView paymentDate;
    }

}
