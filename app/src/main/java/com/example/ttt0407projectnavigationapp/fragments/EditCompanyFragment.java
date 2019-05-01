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
import android.widget.Toast;

import com.example.ttt0407projectnavigationapp.IStockPriceFetcher;
import com.example.ttt0407projectnavigationapp.ImageDownloader;
import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.StockPriceDownloader;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;


public class EditCompanyFragment extends Fragment implements IStockPriceFetcher {

    private Company company;

    private EditText edtCompanyName;
    private EditText edtCompanyStockTicker;
    private EditText edtCompanyImageUrl;

    //final DaoImpl daoImpl = DaoImpl.getInstance();
    DaoImpl daoImpl = DaoImpl.getInstance(getContext());

    List<Company> lisCompanies = daoImpl.getLisCompanies();
    Integer intPosition;

    // constructor
    public EditCompanyFragment() {
        // Required empty public constructor
    }
    // END constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // indicate correct layout
        View view =  inflater.inflate(R.layout.fragment_edit_company, container, false);

        // set company
        // editing company will edit daoImpl.selectedCompany
        // bc daoImpl is static
        company = daoImpl.getSelectedCompany();

        // find position
        for (int i = 0; i < lisCompanies.size(); i++) {
            if(lisCompanies.get(i).getIntId() == company.getIntId()){
                intPosition = i;
                i = lisCompanies.size();
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
        txtMid.setText("Edit Company");
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
                navigationUpdateCompany();
            }
        });
        //
        //
        ////////

        // assign views
        edtCompanyName = view.findViewById(R.id.edt_company_name);
        edtCompanyStockTicker = view.findViewById(R.id.edt_company_stock_ticker);
        edtCompanyImageUrl = view.findViewById(R.id.edt_company_image_url);

        // set current values
        edtCompanyName.setText(company.getStrName());
        edtCompanyStockTicker.setText(company.getStrStockTicker());
        edtCompanyImageUrl.setText(company.getStrImageUrl());

        // assign action when page button clicked
        Button btn = view.findViewById(R.id.btn_edit_company);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationUpdateCompany();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    ////////
    // NAVIGATION METHODS
    //
    private void navigationUpdateCompany(){

        if(!isEmpty(edtCompanyName.getText()) && !isEmpty(edtCompanyStockTicker.getText()) && !isEmpty(edtCompanyImageUrl.getText())) {

            // notify UI of ticker check
            Toast.makeText(getActivity(),"Checking data...", Toast.LENGTH_SHORT).show();

            // check single stock
            Company c = new Company(
                    edtCompanyName.getText().toString(),
                    edtCompanyStockTicker.getText().toString(),
                    edtCompanyImageUrl.getText().toString()
            );

            List<Company> lisAddedCompany = new ArrayList<>();
            lisAddedCompany.add(c);

            // call correct constructor to check ticker
            new StockPriceDownloader(this, c.getStrStockTicker()).execute();
            // StockPriceDownloader calls next function once complete
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


    // from IStockPriceFetcher interface
    // called by StockPriceDownloader once complete
    @Override
    public void stockPriceFetched(Double dbl) {

        if(dbl > 0.0){
            // if > 0.0, ticker is good

            // if ticker is good, set variables of company
            // set new values
            // updating company also updates daoImpl.selectedCompany
            company.setStrName(edtCompanyName.getText().toString());
            company.setStrImageUrl(edtCompanyImageUrl.getText().toString());
            company.setStrStockTicker(edtCompanyStockTicker.getText().toString());
            company.setDblStockPrice(dbl);

            // update in db
            // also updates in dao via observe functions in DaoImpl
            daoImpl.executeUpdateCompany(company);

            // return to previous
            getActivity().getSupportFragmentManager().popBackStack();

            // notify UI success
            Toast.makeText(getActivity(),"Company " + company.getStrName() + " (" + company.getStrStockTicker() + ") successfully updated!", Toast.LENGTH_SHORT).show();
        }
        else if(dbl == -1.0){
            // received an exception error while trying to retrieve ticker data

            // update company, but warn
            company.setStrName(edtCompanyName.getText().toString());
            company.setStrImageUrl(edtCompanyImageUrl.getText().toString());
            company.setStrStockTicker(edtCompanyStockTicker.getText().toString());
            company.setDblStockPrice(dbl);

            // update in db
            // also updates in dao via observe functions in DaoImpl
            daoImpl.executeUpdateCompany(company);

            // return to previous
            getActivity().getSupportFragmentManager().popBackStack();

            // notify UI success
            Toast.makeText(getActivity(),"Company " + company.getStrName() + " (" + company.getStrStockTicker() + ") successfully added!\n" +
                    "\n" +
                    "WARNING: Could not confirm ticker is correct.", Toast.LENGTH_LONG).show();
        }
        else { // dbl == 0.0

            Toast.makeText(getActivity(),"ERROR: Double-check ticker.", Toast.LENGTH_SHORT).show();
        }
    }
}
