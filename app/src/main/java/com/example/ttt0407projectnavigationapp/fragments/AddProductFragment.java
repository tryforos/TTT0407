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

import com.example.ttt0407projectnavigationapp.ImageDownloader;
import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;


public class AddProductFragment extends Fragment {

    private EditText edtProductName;
    private EditText edtProductUrl;
    private EditText edtProductImageUrl;

    private Company company;

    final DaoImpl daoImpl = DaoImpl.getInstance();

    // constructors
    public AddProductFragment() {
        // Required empty public constructor
    }
    // END constructors

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // indicate correct layout
        View view =  inflater.inflate(R.layout.fragment_add_product, container, false);
        final DaoImpl daoImpl = DaoImpl.getInstance();

        // set company
        company = daoImpl.getSelectedCompany();

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
        txtLhs.setVisibility(View.GONE);
        txtMid.setVisibility(View.VISIBLE);
        txtRhs.setVisibility(View.VISIBLE);

        imgLhs.setVisibility(View.VISIBLE);
        imgRhs.setVisibility(View.GONE);

        // set verbiage
        txtMid.setText("Add Product");
        txtRhs.setText("Save");

        // set icons
        new ImageDownloader(imgLhs).execute(daoImpl.getStrBackIconUrl());
        imgLhs.setScaleX(0.5f);
        imgLhs.setScaleY(0.5f);

        // assign actions when toolbar buttons clicked
        imgLhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationCancel();
            }
        });
        txtRhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationSaveProduct();
            }
        });
        //
        //
        ////////

        // assign views
        edtProductName = view.findViewById(R.id.edt_product_name);
        edtProductUrl = view.findViewById(R.id.edt_product_url);
        edtProductImageUrl = view.findViewById(R.id.edt_product_image_url);

        // assign action when page button clicked
        Button btn = view.findViewById(R.id.btn_add_product);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationSaveProduct();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    ////////
    // NAVIGATION METHODS
    //
    private void navigationSaveProduct(){

        Product p = new Product(
                edtProductName.getText().toString(),
                edtProductUrl.getText().toString(),
                edtProductImageUrl.getText().toString(),
                company.getId()
        );
        daoImpl.executeAddProduct(p);
        getActivity().getSupportFragmentManager().popBackStack();
    }
    private void navigationCancel(){
        getActivity().getSupportFragmentManager().popBackStack();
    }
    //
    //
    ////////
}
