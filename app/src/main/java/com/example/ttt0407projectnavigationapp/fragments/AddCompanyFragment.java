package com.example.ttt0407projectnavigationapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ttt0407projectnavigationapp.ImageDownloader;
import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;


public class AddCompanyFragment extends Fragment {

    private EditText edtCompanyName;
    private EditText edtCompanyStockTicker;
    private EditText edtCompanyImageUrl;

    final DaoImpl daoImpl = DaoImpl.getInstance();

    // constructor
    public AddCompanyFragment() {
        // Required empty public constructor
    }
    // END constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // indicate correct layout
        View view =  inflater.inflate(R.layout.fragment_add_company, container, false);

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
        txtMid.setText("New Company");
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
                navigationSaveCompany();
            }
        });
        //
        //
        ////////

        // assign views
        edtCompanyName = view.findViewById(R.id.edt_company_name);
        edtCompanyStockTicker = view.findViewById(R.id.edt_company_stock_ticker);
        edtCompanyImageUrl = view.findViewById(R.id.edt_company_image_url);

        // assign action when page button clicked
        Button btn = view.findViewById(R.id.btn_add_company);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationSaveCompany();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    ////////
    // NAVIGATION METHODS
    //
    private void navigationSaveCompany(){

        Company c = new Company(
                edtCompanyName.getText().toString(),
                edtCompanyImageUrl.getText().toString(),
                edtCompanyStockTicker.getText().toString()
        );
        daoImpl.executeAddCompany(c);
        getActivity().getSupportFragmentManager().popBackStack();
    }
    private void navigationCancel(){

        getActivity().getSupportFragmentManager().popBackStack();
    }
    //
    //
    ////////


}
