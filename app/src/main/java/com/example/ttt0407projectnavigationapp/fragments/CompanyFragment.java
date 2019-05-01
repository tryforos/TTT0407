package com.example.ttt0407projectnavigationapp.fragments;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ttt0407projectnavigationapp.CompanyListAdapter;
import com.example.ttt0407projectnavigationapp.FragmentNavigation;
import com.example.ttt0407projectnavigationapp.ImageDownloader;
import com.example.ttt0407projectnavigationapp.ProductListAdapter;
import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.IDaoObserver;
import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;

import java.util.ArrayList;
import java.util.List;


public class CompanyFragment extends Fragment implements IDaoObserver {

    private Company company;
    View view;

    ProductListAdapter adapter = null;
    List<Product> lisProducts = new ArrayList<>();

    // getContext() is empty here, but this works
    DaoImpl daoImpl = DaoImpl.getInstance(getContext());
    // this does not work here, so we need to assign in onCreateView
    //Context context = getContext();
    Context context;

    // constructor
    public CompanyFragment() {
        // Required empty public constructor
    }
    // END constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // indicate correct layout
        view =  inflater.inflate(R.layout.fragment_company, container, false);

        context = getContext();

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
        new ImageDownloader(imgLhs, getContext(), null).execute(daoImpl.getStrBackIconUrl());
        imgLhs.setScaleX(0.5f);
        imgLhs.setScaleY(0.5f);

        new ImageDownloader(imgRhs, getContext(), null).execute(daoImpl.getStrPlusIconUrl());
        imgRhs.setScaleX(0.5f);
        imgRhs.setScaleY(0.5f);

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

        ////////
        // LIST STUFF
        //
        // set ListView
        adapter = new ProductListAdapter(this.getActivity(), daoImpl.getLisProducts());
        final ListView lsv = (ListView) view.findViewById(R.id.lsv_products);
        lsv.setAdapter(adapter);

        // short-click on product row
        lsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // set selected product
                Product product = (Product) lsv.getAdapter().getItem(position);
                daoImpl.setSelectedProduct(product);

                // create instance of next Fragment Object
                ProductFragment fragment = new ProductFragment();
                // navigate
                FragmentNavigation.navigationToFragment(getActivity(), fragment);
            }
        });

        // long-click on product row
        lsv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // set selected product
                Product product = (Product) lsv.getAdapter().getItem(position);
                // display menu
                showProductPopup(view, product);
                return true;
            }
        });
        //
        //
        ////////

        // set company logo, etc
        ImageView imgCompanyLogo = (ImageView) view.findViewById(R.id.img_company_fragment_logo);
        TextView txtCompanyName = (TextView) view.findViewById(R.id.txt_company_fragment_name);

        new ImageDownloader(imgCompanyLogo, context, company.getIntId()).execute(company.getStrImageUrl());
        txtCompanyName.setText(company.getStrName() + " (" + company.getStrStockTicker() +")");

        // when company icon clicked
        imgCompanyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationEditCompany();
            }
        });

        // runs every time the Back Stack changes
        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(

            new FragmentManager.OnBackStackChangedListener() {

                public void onBackStackChanged() {
                    // getContext() method does not work here bc this is an instance of a new class
                    // hence the need for the context variable
                    update();
                }
            }
        );

        // set observer, will run on db update in DaoImpl
        daoImpl.companyCompanyObserver = this;
        daoImpl.updateSingleCompany(getViewLifecycleOwner());

        daoImpl.companyProductsObserver = this;
        daoImpl.updateProductList(getViewLifecycleOwner());


        // Inflate the layout for this fragment
        return view;
    }


    ////////
    ////////
    ////////


    //POP UP MENU
    public void showProductPopup(View view, final Product product) {

        PopupMenu popup = new PopupMenu(getActivity(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_product_delete_edit, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {

                if (item.getTitle().toString().equals("Edit")) {
                    navigationEditProduct(product);
                }
                else{
                    navigationDeleteProduct(product);
                    Toast.makeText(getActivity(),product.getStrName() + " deleted", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        popup.show();
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
    private void navigationEditProduct(Product product){

        // set product
        daoImpl.setSelectedProduct(product);
        // create instance of next Fragment Object
        EditProductFragment fragment = new EditProductFragment();
        // navigate
        FragmentNavigation.navigationToFragment(getActivity(), fragment);
    }
    private void navigationDeleteProduct(Product product){

        // delete product
        daoImpl.executeDeleteProduct(product);
    }
    //
    //
    ////////

    @Override
    public void update() {

        // if products were updated
        lisProducts = daoImpl.getLisProducts();
        adapter.notifyDataSetChanged();

        // if company was updated
        // set company
        company = daoImpl.getSelectedCompany();

        // update company info
        // set verbiage
        TextView txtMid = (TextView) view.findViewById(R.id.txt_mid);
        txtMid.setText(company.getStrName());

        // set company logo, etc
        ImageView imgCompanyLogo = (ImageView) view.findViewById(R.id.img_company_fragment_logo);
        TextView txtCompanyName = (TextView) view.findViewById(R.id.txt_company_fragment_name);

        new ImageDownloader(imgCompanyLogo, context, company.getIntId()).execute(company.getStrImageUrl());

        txtCompanyName.setText(company.getStrName() + " (" + company.getStrStockTicker() +")");
    }
}
