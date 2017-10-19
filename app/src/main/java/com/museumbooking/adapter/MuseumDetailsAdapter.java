package com.museumbooking.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.androidquery.AQuery;
import com.museumbooking.R;
import com.museumbooking.activity.BaseActivity;
import com.museumbooking.activity.GoogleMapActivity;
import com.museumbooking.conf.WebserviceConstants;
import com.museumbooking.model.MuseumDetailsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aswathy_G on 9/22/2017.
 */

public class MuseumDetailsAdapter extends BaseAdapter {
    private Context mContext;
    List<MuseumDetailsModel> museumDetailsModelList;

    public MuseumDetailsAdapter(Activity context, List<MuseumDetailsModel> museumDetailsModelList) {
        mContext = context;
        this.museumDetailsModelList = museumDetailsModelList;
    }


    @Override
    public int getCount() {
        return museumDetailsModelList.size();
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_museum_details, parent, false);
            holder = new ViewHolder();
            holder.museumName = (TextView) convertView
                    .findViewById(R.id.museumName);
            holder.museumType = (TextView) convertView
                    .findViewById(R.id.museumType);
            holder.museumAddress = (TextView) convertView
                    .findViewById(R.id.museumAddress);
            holder.museumImage = (ImageView) convertView.findViewById(R.id.museumImage);
            holder.trackButton = (Button) convertView
                    .findViewById(R.id.trackButton);
            holder.callButton = (Button) convertView
                    .findViewById(R.id.callButton);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.museumName.setText(museumDetailsModelList.get(position).getMuseum_name());
        holder.museumType.setText(museumDetailsModelList.get(position).getMuseum_type());
        holder.museumAddress.setText(museumDetailsModelList.get(position).getAddress());

        holder.trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mContext.startActivity(new Intent(mContext, GoogleMapActivity.class)
                        .putExtra("latitude", museumDetailsModelList.get(position).getLatitude())
                        .putExtra("longitude", museumDetailsModelList.get(position).getLongitude())

                );
            }
        });
        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallButtonClick(museumDetailsModelList.get(position).getPhoneno());

            }
        });
        if (null != getImageUrl(museumDetailsModelList.get(position).getPhoto()) && !getImageUrl(museumDetailsModelList.get(position).getPhoto()).isEmpty()) {
            new AQuery(holder.museumImage).image(getImageUrl(museumDetailsModelList.get(position).getPhoto()), false, false, 0,
                    R.mipmap.ic_launcher, null, AQuery.FADE_IN);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView museumName;
        TextView museumType;
        TextView museumAddress;
        ImageView museumImage;
        Button trackButton;
        Button callButton;
    }

    private String getImageUrl(String imageName) {
        String url = "http";
        url = (new StringBuilder(url).append("://")
                .append(BaseActivity.getServerAddress()).append("/").append(WebserviceConstants.IMAGE_PATH).append("/").append(imageName)).toString();
        return url;
    }

    private void onCallButtonClick(String phoneNumber)
    {

        Intent dialIntent = new Intent();
        dialIntent.setAction(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:"
                + phoneNumber));
        mContext.startActivity(dialIntent);
    }
}
