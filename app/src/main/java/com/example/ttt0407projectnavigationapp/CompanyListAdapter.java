package com.example.ttt0407projectnavigationapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ttt0407projectnavigationapp.model.entity.Company;

import java.text.NumberFormat;
import java.util.List;

public class CompanyListAdapter extends ArrayAdapter<Company> {

    // constructor
    public CompanyListAdapter(Context context, List<Company> values) {
        super(context, 0, values);
    }
    // END constructor

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Company co = getItem(position);

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.row_company, parent, false);

            convertView = rowView;
        }

        // set values
        ImageView imgCompanyLogo = (ImageView) convertView.findViewById(R.id.imgCompanyLogo);
        TextView txtCompanyInfo = (TextView) convertView.findViewById(R.id.txtCompanyInfo);
        TextView txtStockPrice = (TextView) convertView.findViewById(R.id.txtStockPrice);

        new ImageDownloader(imgCompanyLogo).execute(co.getStrImageUrl());
        txtCompanyInfo.setText(co.getStrName() +" (" + co.getStrStockTicker() + ")");

        //txtStockPrice.setText(String.valueOf(co.getDblStockPrice()));
        NumberFormat format = NumberFormat.getCurrencyInstance();
        txtStockPrice.setText(format.format(co.getDblStockPrice()));

        return convertView;
    }
}
