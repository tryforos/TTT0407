package com.example.ttt0407projectnavigationapp.fragments;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.Random;

import static android.text.TextUtils.isEmpty;
import static java.lang.Math.random;


public class AddCompanyFragment extends Fragment implements IStockPriceFetcher {

    private EditText edtCompanyName;
    private EditText edtCompanyStockTicker;
    private EditText edtCompanyImageUrl;

    //final DaoImpl daoImpl = DaoImpl.getInstance();
    DaoImpl daoImpl = DaoImpl.getInstance(getContext());
    Fragment fraPrevious;

    List<Company> lisCompanies = daoImpl.getLisCompanies();

    // constructor
    public AddCompanyFragment() {
        // Required empty public constructor
    }
    // END constructor

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

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

        // assign actions when toolbar buttons clicked
        txtLhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationCancel();
            }
        });

        // TODO: remove
        txtMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random rdm = new Random();
                Integer i = rdm.nextInt(5);
                Log.i("holler", "i: " + i);

                if(i == 0){
                    edtCompanyName.setText("Apple Inc");
                    edtCompanyStockTicker.setText("AAPL");
                    edtCompanyImageUrl.setText("https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Apple_logo_black.svg/1024px-Apple_logo_black.svg.png");
                }
                else if (i == 1){
                        edtCompanyName.setText("Google Inc");
                    edtCompanyStockTicker.setText("GOOGL");
                    edtCompanyImageUrl.setText("https://www.trainingtoyou.com/wp-content/uploads/2018/08/2000px-Google__G__Logo.svg_.png");
                }
                else if (i == 2){
                    edtCompanyName.setText("Pepsi Co");
                    edtCompanyStockTicker.setText("PEP");
                    edtCompanyImageUrl.setText("https://cdn4.iconfinder.com/data/icons/social-media-logos-6/512/49-pepsi-128.png");
                }
                else if (i == 3) {
                    edtCompanyName.setText("Tesla Corp");
                    edtCompanyStockTicker.setText("TSLA");
                    edtCompanyImageUrl.setText("https://pngimg.com/uploads/tesla_logo/tesla_logo_PNG19.png");
                }
                else { // if (i == 4)
                    edtCompanyName.setText("Twitter Inc");
                    edtCompanyStockTicker.setText("TWTR");
                    edtCompanyImageUrl.setText("https://buzzhostingservices.com/images/twitter-logo-png-2.png");
                }
            }
        });
        // END TODO: remove

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
            Company c = new Company(
                    edtCompanyName.getText().toString(),
                    edtCompanyStockTicker.getText().toString(),
                    edtCompanyImageUrl.getText().toString()
            );
            c.setDblStockPrice(dbl);
            c.setIntPosition(lisCompanies.size());

            // add to database
            // also updates in dao via observe functions in DaoImpl
            daoImpl.executeAddCompany(c);

            // moved to observe
            getActivity().getSupportFragmentManager().popBackStack();

            // notify UI success
            Toast.makeText(getActivity(),"Company " + c.getStrName() + " (" + c.getStrStockTicker() + ") successfully added!", Toast.LENGTH_LONG).show();
        }
        else if(dbl == -1.0) {
            // received an exception error while trying to retrieve ticker data

            // add company, but warn
            Company c = new Company(
                    edtCompanyName.getText().toString(),
                    edtCompanyStockTicker.getText().toString(),
                    edtCompanyImageUrl.getText().toString()
            );
            c.setDblStockPrice(dbl);
            c.setIntPosition(lisCompanies.size());

            // add to database
            // also updates in dao via observe functions in DaoImpl
            daoImpl.executeAddCompany(c);

            // moved to observe
            getActivity().getSupportFragmentManager().popBackStack();

            // notify UI success
            Toast.makeText(getActivity(),"Company " + c.getStrName() + " (" + c.getStrStockTicker() + ") successfully added!\n" +
                    "\n" +
                    "WARNING: Could not confirm ticker is correct.", Toast.LENGTH_LONG).show();
        }
        else { // dbl == 0.0

            Toast.makeText(getActivity(),"ERROR: Double-check ticker.", Toast.LENGTH_SHORT).show();
        }
    }
}
