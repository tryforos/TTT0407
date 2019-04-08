package com.example.ttt0407projectnavigationapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ttt0407projectnavigationapp.CompanyListAdapter;
import com.example.ttt0407projectnavigationapp.ImageDownloader;
import com.example.ttt0407projectnavigationapp.ProductListAdapter;
import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.model.Dao;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;

import java.util.ArrayList;
import java.util.List;


public class CompanyFragment extends Fragment {

    public CompanyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_company, container, false);
        List<Product> lisProducts = new ArrayList<>();
        Company company = new Company();
        Dao dao = DaoImpl.getInstance();

        // get products for company
        lisProducts = dao.getAllProducts(company);

        // set ListView
        ProductListAdapter adapter = new ProductListAdapter(this.getActivity(), lisProducts);
        ListView lsv = (ListView) view.findViewById(R.id.lsvProducts);
        lsv.setAdapter(adapter);


        ImageView imgCompanyLogo = (ImageView) view.findViewById(R.id.imgCompanyLogo);
        TextView txtCompanyName = (TextView) view.findViewById(R.id.txtCompanyName);

        new ImageDownloader(imgCompanyLogo).execute(company.getStrImageUrl());
        txtCompanyName.setText(company.getStrName() + " (" + company.getStrStockTicker() +")");

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


/*
        //text = new TextView(getActivity());
        text = new TextView(getActivity());
        text.setText("holleration");

        //getView().set
        getView().setBackgroundColor(Color.rgb(255, 204, 153));
*/

    }

}
