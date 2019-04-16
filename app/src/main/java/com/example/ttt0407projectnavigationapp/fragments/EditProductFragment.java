package com.example.ttt0407projectnavigationapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;


public class EditProductFragment extends Fragment {

    private Product product;

    private EditText edtProductName;
    private EditText edtProductUrl;
    private EditText edtProductImageUrl;

    final DaoImpl daoImpl = DaoImpl.getInstance();

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

/*
        // set icons
        new ImageDownloader(imgLhs).execute(daoImpl.getStrBackIconUrl());
        imgLhs.setScaleX(0.5f);
        imgLhs.setScaleY(0.5f);
*/

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
    private void setProductNewData(){

        // set new values
        product.setStrName(edtProductName.getText().toString());
        product.setStrUrl(edtProductUrl.getText().toString());
        product.setStrImageUrl(edtProductImageUrl.getText().toString());
        //add to data
        daoImpl.executeUpdateProduct(product);
    }
    private void navigationUpdateProduct(){

        setProductNewData();
        getActivity().getSupportFragmentManager().popBackStack();
    }
    private void navigationCancel(){

        getActivity().getSupportFragmentManager().popBackStack();
    }
    //
    //
    ////////

}
