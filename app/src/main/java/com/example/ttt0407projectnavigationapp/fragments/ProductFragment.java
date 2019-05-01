package com.example.ttt0407projectnavigationapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ttt0407projectnavigationapp.FragmentNavigation;
import com.example.ttt0407projectnavigationapp.ImageDownloader;
import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.WebpageDownloader;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.IDaoObserver;
import com.example.ttt0407projectnavigationapp.model.entity.Product;


public class ProductFragment extends Fragment implements IDaoObserver {

    private Product product;
    View view;

    DaoImpl daoImpl = DaoImpl.getInstance(getContext());

    // constructors
    public ProductFragment() {
        // Required empty public constructor
    }
    // END constructors

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // indicate correct layout
        view = inflater.inflate(R.layout.fragment_product, container, false);

        // set product
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
        txtLhs.setVisibility(View.GONE);
        txtMid.setVisibility(View.VISIBLE);
        txtRhs.setVisibility(View.VISIBLE);

        imgLhs.setVisibility(View.VISIBLE);
        imgRhs.setVisibility(View.GONE);

        // set verbiage
        txtMid.setText("Product Link");
        txtRhs.setText("Edit");

        // set icons
        new ImageDownloader(imgLhs, getContext(), null).execute(daoImpl.getStrBackIconUrl());
        imgLhs.setScaleX(0.5f);
        imgLhs.setScaleY(0.5f);

        // assign actions when buttons clicked
        imgLhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationCancel();
            }
        });
        txtRhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationEditProduct(product);
            }
        });
        //
        //
        ////////

        // set WebView
        WebView web = (WebView) view.findViewById(R.id.web_product_page);
        // load webpage
        WebpageDownloader.loadWeb(web, product.getStrUrl());

        // runs every time the Back Stack changes
        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(
            new FragmentManager.OnBackStackChangedListener() {

                public void onBackStackChanged() {
                    update();
                }
            }
        );

        // set observer, will run on db update in DaoImpl
        daoImpl.productObserver = this;
        daoImpl.updateSingleProduct(getViewLifecycleOwner());

        // Inflate the layout for this fragment
        return view;
    }

    ////////
    // NAVIGATION METHODS
    //
    private void navigationEditProduct(Product product){

        // create instance of next Fragment Object
        EditProductFragment fragment = new EditProductFragment();
        // navigate
        FragmentNavigation.navigationToFragment(getActivity(), fragment);
    }
    private void navigationCancel(){

        getActivity().getSupportFragmentManager().popBackStack();
    }
    //
    //
    ////////

    @Override
    public void update() {

        // set product
        product = daoImpl.getSelectedProduct();

        // set WebView
        WebView web = (WebView) view.findViewById(R.id.web_product_page);
        // load webpage
        WebpageDownloader.loadWeb(web, product.getStrUrl());
    }
}
