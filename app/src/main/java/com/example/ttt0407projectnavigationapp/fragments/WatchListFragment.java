package com.example.ttt0407projectnavigationapp.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ttt0407projectnavigationapp.CompanyListAdapter;
import com.example.ttt0407projectnavigationapp.FragmentNavigation;
import com.example.ttt0407projectnavigationapp.ImageDownloader;
import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.StockPriceDownloader;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.IDaoObserver;
import com.example.ttt0407projectnavigationapp.model.entity.Company;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class WatchListFragment extends Fragment implements IDaoObserver {

    View view;
    CompanyListAdapter adapter = null;
    List<Company> lisCompanies;

    ListView lsv = null;
    TextView txt = null;
    LinearLayout llv  = null;

    DaoImpl daoImpl = null;
    //DaoImpl daoImpl = DaoImpl.getInstance();
    //DaoImpl daoImpl = DaoImpl.getInstance(getContext());


    // constructor
    public WatchListFragment() {
        // Required empty public constructor
    }
    // END constructor


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // indicate correct layout
        view =  inflater.inflate(R.layout.fragment_watch_list, container, false);
        //daoImpl = DaoImpl.getInstance();
        daoImpl = DaoImpl.getInstance(getContext());

        lsv = (ListView) view.findViewById(R.id.lsv_companies);
        txt = (TextView) view.findViewById(R.id.txt_empty);
        llv  = (LinearLayout) view.findViewById(R.id.llv_empty);

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
        txtRhs.setVisibility(View.GONE);

        imgLhs.setVisibility(View.GONE);
        imgRhs.setVisibility(View.VISIBLE);

        // set verbiage
        txtLhs.setText("");
        txtMid.setText("Watch List");

        // set icons
        //imgRhs.setImageResource(android.R.drawable.btn_star);
        new ImageDownloader(imgRhs, getContext(), null).execute(daoImpl.getStrPlusIconUrl());
        imgRhs.setScaleX(0.5f);
        imgRhs.setScaleY(0.5f);

        // assign actions when buttons in toolbar are clicked
        txtLhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nothing
            }
        });
        txtMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nothing
            }
        });
        imgRhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationAddCompany();
            }
        });
        //
        //
        ////////

        ////////
        // LIST STUFF
        //
        // set ListView
        // retrieve companies
        lisCompanies = daoImpl.getLisCompanies();
        adapter = new CompanyListAdapter(this.getActivity(), lisCompanies);
        lsv.setAdapter(adapter);

        // short-click on company row
        lsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // set selected Company
                Company company = (Company) lsv.getAdapter().getItem(position);
                daoImpl.setSelectedCompany(company);

                // update stock prices
                updateStockPrices();

                // create instance of next Fragment Object
                CompanyFragment fragment = new CompanyFragment();
                // navigate
                FragmentNavigation.navigationToFragment(getActivity(), fragment);
            }
        });

        // long-click on company row
        lsv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // set selected Company
                Company company = (Company) lsv.getAdapter().getItem(position);
                // display menu
                showCompanyPopup(view, company);
                return true;
            }
        });
        //
        //
        ////////

        ////////
        // PLACEHOLDER TEXTVIEW WHEN LISTVIEW IS EMPTY
        //
        // click on  "+ Add Company"
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationAddCompany();
            }
        });
        //
        //
        ////////

        // set correct views
        setCorrectView();

        // set observer, will run on db update in DaoImpl
        daoImpl.watchListObserver = this;
        daoImpl.updateCompanyList(getViewLifecycleOwner());

        ////////
        // UPDATE LISTVIEW WHEN DATABASE CHANGES
        //

        // auto-run functions when database updates are all in DAO

        //
        //
        ////////

        // runs when BackStack changes
        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(
            new FragmentManager.OnBackStackChangedListener() {
                public void onBackStackChanged() {
                    update();
                }
            }
        );

        ////////
        //HANDLER PERIOD TASK
        //
        // pull stock quotes every 60 sec
        final Handler handler=new Handler();

        Runnable runnableCode = new Runnable() {

            @Override
            public void run() {
                updateStockPrices();

                //executes the same Runnable task after a delay of 2000 milliseconds.
                handler.postDelayed(this, 60000);
            }
        };

        //execute a Runnable task written inside run() method on the UIThread
        handler.post(runnableCode);
        //
        //
        ////////


        // Inflate the layout for this fragment
        return view;
    }


    ////////
    ////////
    ////////


    ////////
    // BUILT-IN METHODS THAT AUTO-RUN @CERTAIN POINTS
    //
    @Override
    public void onStart(){
        super.onStart();
    }
    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onPause(){
        super.onPause();
    }
    @Override
    public void onStop(){
        super.onStop();
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
    //
    //
    ////////

    //SET CORRECT VIEW
    private void setCorrectView(){

        if(lisCompanies.size() > 0) {
            // if no companies, display "+ Add Company" w image
            lsv.setVisibility(View.VISIBLE);
            llv.setVisibility(View.GONE);
        }
        else {
            // if companies, display them
            lsv.setVisibility(View.GONE);
            llv.setVisibility(View.VISIBLE);
        }
    }

    //POP UP MENU
    public void showCompanyPopup(View view, final Company company) {

        PopupMenu popup = new PopupMenu(getActivity(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_company_delete_edit, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {

                if (item.getTitle().toString().equals("Edit")) {
                    navigationEditCompany(company);
                }
                else{
                    navigationDeleteCompany(company);
                    Toast.makeText(getActivity(),company.getStrName() + " deleted", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getActivity(),"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popup.show();
    }


    ////////
    // NAVIGATION METHODS
    //
    private void navigationEditCompany(Company company){
        // set company
        daoImpl.setSelectedCompany(company);
        // create instance of next Fragment Object
        EditCompanyFragment fragment = new EditCompanyFragment();
        // navigate
        FragmentNavigation.navigationToFragment(getActivity(), fragment);
    }
    private void navigationAddCompany(){
        // create instance of next Fragment Object
        AddCompanyFragment fragment = new AddCompanyFragment();
        // navigate
        FragmentNavigation.navigationToFragment(getActivity(), fragment);
    }
    private void navigationDeleteCompany(Company company){
        // delete company
        daoImpl.executeDeleteCompany(company);
    }
    //
    //
    ////////

    // from interface
    @Override
    public void update() {

        updateAdapter();
    }

    public void updateAdapter(){

        lisCompanies = daoImpl.getLisCompanies();
        setCorrectView();
        adapter.notifyDataSetChanged();
    }

    public void updateStockPrices() {

        if(lisCompanies.size() > 0) {

            new StockPriceDownloader().execute();
        }
    }
}
