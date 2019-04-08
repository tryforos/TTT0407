package com.example.ttt0407projectnavigationapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.model.Dao;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;


public class AddCompanyFragment extends Fragment {

    EditText edtName;
    EditText edtStockTicker;
    EditText edtImageUrl;
    Dao dao;
    DaoImpl daoImpl;

    public AddCompanyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_add_company, container, false);
        dao = DaoImpl.getInstance();

        edtName = (EditText) view.findViewById(R.id.edt_company_name);
        edtStockTicker = (EditText) view.findViewById(R.id.edt_company_stock_ticker);
        edtImageUrl = (EditText) view.findViewById(R.id.edt_company_image_url);

        // Inflate the layout for this fragment
        return view;
    }


    private void saveCompanyData(){

        Company c = new Company(edtName.getText().toString(), edtImageUrl.getText().toString(), edtStockTicker.getText().toString());
        daoImpl.executeAddCompany(c);
    }

}
