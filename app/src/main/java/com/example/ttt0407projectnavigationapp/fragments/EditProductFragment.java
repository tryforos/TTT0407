package com.example.ttt0407projectnavigationapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.StockPriceDownloader;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;


public class EditProductFragment extends Fragment {

    private Product product;

    private EditText edtProductName;
    private EditText edtProductUrl;
    private EditText edtProductImageUrl;

    DaoImpl daoImpl = DaoImpl.getInstance(getContext());

    List<Product> lisProducts = daoImpl.getLisProducts();
    Integer intPosition;

    // constructors
    public EditProductFragment() {
        // Required empty public constructor
    }
    // END constructors

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // indicate correct layout
        View view =  inflater.inflate(R.layout.fragment_edit_product, container, false);

        // get product
        product = daoImpl.getSelectedProduct();

        // find position
        for (int i = 0; i < lisProducts.size(); i++) {
            if(lisProducts.get(i).getIntId() == product.getIntId()){
                intPosition = i;
                i = lisProducts.size();
            }
            i++;
        }

        ////////
        // TOOLBAR
        //
        // set views
        TextView txtLhs = (TextView) view.findViewById(R.id.txt_lhs);
        TextView txtMid = (TextView) view.findViewById(R.id.txt_mid);
        TextView txtRhs = (TextView) view.findViewById(R.id.txt_rhs);

        ImageView imgLhs = (ImageView) view.findViewById(R.id.img_lhs);
        ImageView imgRhs = (ImageView) view.findViewById(R.id.img_rhs);

        // set visibility
        txtLhs.setVisibility(View.VISIBLE);
        txtMid.setVisibility(View.VISIBLE);
        txtRhs.setVisibility(View.VISIBLE);

        imgLhs.setVisibility(View.GONE);
        imgRhs.setVisibility(View.GONE);

        // set verbiage
        txtLhs.setText("Cancel");
        txtMid.setText("Edit Product");
        txtRhs.setText("Save");

        // assign actions when toolbar buttons clicked
        txtLhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationCancel();
            }
        });
        txtRhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationUpdateProduct();
            }
        });
        //
        //
        ////////

        // assign views
        edtProductName = view.findViewById(R.id.edt_product_name);
        edtProductUrl = view.findViewById(R.id.edt_product_url);
        edtProductImageUrl = view.findViewById(R.id.edt_product_image_url);

        // set current values
        edtProductName.setText(product.getStrName());
        edtProductUrl.setText(product.getStrUrl());
        edtProductImageUrl.setText(product.getStrImageUrl());

        // assign action when page button clicked
        Button btn = view.findViewById(R.id.btn_edit_product);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationUpdateProduct();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    ////////
    // NAVIGATION METHODS
    //
    private void navigationUpdateProduct(){

        if(!isEmpty(edtProductName.getText()) && !isEmpty(edtProductUrl.getText()) && !isEmpty(edtProductImageUrl.getText())) {

            // set new values
            product.setStrName(edtProductName.getText().toString());
            product.setStrUrl(edtProductUrl.getText().toString());
            product.setStrImageUrl(edtProductImageUrl.getText().toString());

/*
            // update in list
            lisProducts.get(intPosition).setStrName(edtProductName.getText().toString());
            lisProducts.get(intPosition).setStrUrl(edtProductUrl.getText().toString());
            lisProducts.get(intPosition).setStrImageUrl(edtProductImageUrl.getText().toString());

            // update in dao
            daoImpl.setSelectedProduct(product);
            daoImpl.setLisProducts(lisProducts);
*/

            // update in db
            daoImpl.executeUpdateProduct(product);

            // return to previous
            //getActivity().getSupportFragmentManager().popBackStack();
            getActivity().getSupportFragmentManager().popBackStack("CompanyFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);


            // notify UI success
            Toast.makeText(getActivity(),"Product " + product.getStrName() + " successfully updated!", Toast.LENGTH_SHORT).show();
        }
        else {

            Toast.makeText(getActivity(),"All fields must be filled in to save.", Toast.LENGTH_SHORT).show();
        }
    }
    private void navigationCancel(){

        getActivity().getSupportFragmentManager().popBackStack();
    }
    //
    //
    ////////
}
