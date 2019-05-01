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

    DaoImpl daoImpl = DaoImpl.getInstance(getContext());

    // constructors
    public AddProductFragment() {
        // Required empty public constructor
    }
    // END constructors

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // indicate correct layout
        View view =  inflater.inflate(R.layout.fragment_add_product, container, false);

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
        new ImageDownloader(imgLhs, getContext(), null).execute(daoImpl.getStrBackIconUrl());
        imgLhs.setScaleX(0.5f);
        imgLhs.setScaleY(0.5f);

        // assign actions when toolbar buttons clicked
        imgLhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationCancel();
            }
        });

        // TODO: remove
        txtMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtProductName.setText("Hollerator 5000");
                edtProductUrl.setText("https://dayoftheshirt.com/");
                edtProductImageUrl.setText("https://dayoftheshirt.com/assets/emojis/heart_eyes-b8268d9f4d08100cde0cec9e0b372da2b21385244a3174b704c95976029f1598.png");
            }
/*
            // TODO: pull correct info
            // DUMMY
            lisProducts.add(new Product(
                    "Hollerator #1",
                    "Do stuff!",
                    "https://www.journaldev.com/16813/dao-design-pattern",
                    "https://buzzhostingservices.com/images/twitter-logo-png-2.png",
                    123.45));
            lisProducts.add(new Product(
                    "Hollerator #2",
                    "Do other stuff!",
                    "https://en.wikipedia.org/wiki/Battle_of_Thermopylae",
                    "https://www.freelogodesign.org/Content/img/logo-ex-7.png",
                    123.46));
            lisProducts.add(new Product(
                    "Hollerator #3",
                    "Do stuff faster!",
                    "https://dayoftheshirt.com/",
                    "https://dayoftheshirt.com/assets/emojis/heart_eyes-b8268d9f4d08100cde0cec9e0b372da2b21385244a3174b704c95976029f1598.png",
                    123.47));
            // END DUMMY
*/


        });
        // END TODO: remove

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
                company.getIntId()
        );

        daoImpl.setSelectedProduct(p);
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
