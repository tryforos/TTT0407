package com.example.ttt0407projectnavigationapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;

import java.text.NumberFormat;
import java.util.List;

public class CompanyListAdapter extends ArrayAdapter<Company> {
    /*
     * NO LONGER USED
     * switched over to CompanyDragListAdapter
     * which allows for drag-and-drop on the ListView
     *
     */

    DaoImpl daoImpl = DaoImpl.getInstance(getContext());

    // constructor
    public CompanyListAdapter(Context context, List<Company> values) {
        super(context, 0, values);
    }
    // END constructor

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Company company = getItem(position);

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.row_company, parent, false);

            convertView = rowView;
        }

        // set values
        ImageView imgCompanyLogo = (ImageView) convertView.findViewById(R.id.img_company_logo);
        TextView txtCompanyInfo = (TextView) convertView.findViewById(R.id.txt_company_info);
        TextView txtStockPrice = (TextView) convertView.findViewById(R.id.txt_stock_price);

        //new ImageDownloader(imgCompanyLogo).execute(company.getStrImageUrl());
        //new ImageDownloader(imgCompanyLogo, getContext()).execute(company.getStrImageUrl());
        new ImageDownloader(imgCompanyLogo, getContext(), company.getIntId()).execute(company.getStrImageUrl());

        txtCompanyInfo.setText(company.getStrName() +" (" + company.getStrStockTicker() + ")");

        if (company.getDblStockPrice() > 0) {

            // format and set value
            NumberFormat format = NumberFormat.getCurrencyInstance();
            txtStockPrice.setText(format.format(company.getDblStockPrice()));
        }
        else {

            // if error in receiving stock price
            txtStockPrice.setText("N/A");
        }

        return convertView;
    }
}
