package com.example.ttt0407projectnavigationapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ttt0407projectnavigationapp.model.entity.Product;

import java.text.NumberFormat;
import java.util.List;

public class ProductListAdapter extends ArrayAdapter<Product> {

    // constructor
    public ProductListAdapter(Context context, List<Product> values) {
        super(context, 0, values);
    }
    // END constructor

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Product product = getItem(position);

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.row_product, parent, false);

            convertView = rowView;
        }

        // set values
        ImageView imgProductLogo = (ImageView) convertView.findViewById(R.id.img_product_logo);
        TextView txtProductDescription = (TextView) convertView.findViewById(R.id.txt_product_description);
        TextView txtProductPrice = (TextView) convertView.findViewById(R.id.txt_product_price);

        new ImageDownloader(imgProductLogo).execute(product.getStrImageUrl());
        txtProductDescription.setText(product.getStrName() +" - " + product.getStrShortDescription());

        //txtStockPrice.setText(String.valueOf(co.getDblStockPrice()));
        NumberFormat format = NumberFormat.getCurrencyInstance();
        txtProductPrice.setText(format.format(product.getDblPrice()));

        return convertView;
    }
}
