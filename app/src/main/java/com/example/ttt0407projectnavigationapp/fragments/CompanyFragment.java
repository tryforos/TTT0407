package com.example.ttt0407projectnavigationapp.fragments;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ttt0407projectnavigationapp.CompanyListAdapter;
import com.example.ttt0407projectnavigationapp.FragmentNavigation;
import com.example.ttt0407projectnavigationapp.ImageDownloader;
import com.example.ttt0407projectnavigationapp.ProductListAdapter;
import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;

import java.util.ArrayList;
import java.util.List;


public class CompanyFragment extends Fragment {

/*
    ////////
    // HOLLER PASS OBJECT TO COMPANY
    //
    private static final String COMPANY_KEY = "Company";
    //
    //
    ////////
*/

    private Company company;

    // constructor
    public CompanyFragment() {
        // Required empty public constructor
    }
    // END constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // indicate correct layout
        View view =  inflater.inflate(R.layout.fragment_company, container, false);
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
        txtRhs.setVisibility(View.GONE);

        imgLhs.setVisibility(View.VISIBLE);
        imgRhs.setVisibility(View.VISIBLE);

        // set verbiage
        txtMid.setText(company.getStrName());

        // set icons
        new ImageDownloader(imgLhs).execute(daoImpl.getStrBackIconUrl());
        imgLhs.setScaleX(0.5f);
        imgLhs.setScaleY(0.5f);

        new ImageDownloader(imgRhs).execute(daoImpl.getStrPlusIconUrl());
        imgRhs.setScaleX(0.5f);
        imgRhs.setScaleY(0.5f);
        //imgLhs.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_input_delete));
        //imgRhs.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_add));

        // assign actions when buttons clicked
        imgLhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        imgRhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationAddProduct();
            }
        });
        //
        //
        ////////


/*

        ////////
        // HOLLER PASS OBJECT TO COMPANY
        // 1 of 2
        // 1 in [Previous Fragment] class
        //
        // navigation from WatchListFragment
        company = (Company) getArguments().getSerializable(COMPANY_KEY);
        //
        //
        ////////
*/

        // retrieve products
        List<Product> lisProducts = new ArrayList<>();
        lisProducts = daoImpl.getAllProducts(company);

        // set ListView
        ProductListAdapter adapter = new ProductListAdapter(this.getActivity(), lisProducts);
        final ListView lsv = (ListView) view.findViewById(R.id.lsv_products);
        lsv.setAdapter(adapter);

        // assign action on ListView click
        lsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // set selected product
                Product product = (Product) lsv.getAdapter().getItem(position);

                ////////
                // HOLLER FRAGMENT NAVIGATION
                //
                // create instance of next Fragment Object
                ProductFragment fragment = new ProductFragment();
                // set product
                daoImpl.setSelectedProduct(product);
                // navigate
                FragmentNavigation.navigationToFragment(getActivity(), fragment);
                //
                //
                ////////
            }
        });


        // set company logo, etc
        ImageView imgCompanyLogo = (ImageView) view.findViewById(R.id.img_company_logo);
        TextView txtCompanyName = (TextView) view.findViewById(R.id.txt_company_name);

        new ImageDownloader(imgCompanyLogo).execute(company.getStrImageUrl());
        txtCompanyName.setText(company.getStrName() + " (" + company.getStrStockTicker() +")");

        // when company icon clicked
        imgCompanyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationEditCompany();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }


    ////////
    // NAVIGATION METHODS
    //
    private void navigationEditCompany(){

        // create instance of next Fragment Object
        EditCompanyFragment fragment = new EditCompanyFragment();
        // navigate
        FragmentNavigation.navigationToFragment(getActivity(), fragment);
    }

    private void navigationAddProduct(){

        // create instance of next Fragment Object
        AddProductFragment fragment = new AddProductFragment();
        // navigate
        FragmentNavigation.navigationToFragment(getActivity(), fragment);
    }
    //
    //
    ////////


/*
    ////////
    // HOLLER PASS OBJECT TO COMPANY
    // 2 of 2
    // 1 in [Previous Fragment] class
    //
    // navigation from WatchListFragment
    public static CompanyFragment newInstance(Company c) {
        CompanyFragment fragment = new CompanyFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(COMPANY_KEY, c);
        fragment.setArguments(bundle);
        return fragment;
    }
    //
    //
    ////////
*/

}
