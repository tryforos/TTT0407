package com.example.ttt0407projectnavigationapp.fragments;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ttt0407projectnavigationapp.CompanyListAdapter;
import com.example.ttt0407projectnavigationapp.FragmentNavigation;
import com.example.ttt0407projectnavigationapp.GestureListener;
import com.example.ttt0407projectnavigationapp.ImageDownloader;
import com.example.ttt0407projectnavigationapp.MainActivity;
import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class WatchListFragment extends Fragment {


    View view;
    DaoImpl daoImpl = null;
    CompanyListAdapter adapter = null;
    ListView lsv = null;
    TextView txt = null;
    LinearLayout llv  = null;
    List<Company> lisCompany = new ArrayList<>();
    Activity activity;




    // constructor
    public WatchListFragment() {
        // Required empty public constructor
    }
    // END constructor


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // indicate correct layout
/*
        View view =  inflater.inflate(R.layout.fragment_watch_list, container, false);
        final DaoImpl daoImpl = DaoImpl.getInstance();

        final CompanyListAdapter adapter;
        final ListView lsv = (ListView) view.findViewById(R.id.lsv_companies);
        final TextView txt = (TextView) view.findViewById(R.id.txt_empty);
        final LinearLayout llv  = (LinearLayout) view.findViewById(R.id.llv_empty);
*/

        view =  inflater.inflate(R.layout.fragment_watch_list, container, false);
        daoImpl = DaoImpl.getInstance();

        lsv = (ListView) view.findViewById(R.id.lsv_companies);
        txt = (TextView) view.findViewById(R.id.txt_empty);
        llv  = (LinearLayout) view.findViewById(R.id.llv_empty);

        activity = getActivity();

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
        new ImageDownloader(imgRhs).execute(daoImpl.getStrPlusIconUrl());
        imgRhs.setScaleX(0.5f);
        imgRhs.setScaleY(0.5f);
        //imgLhs.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_input_delete));
        //imgRhs.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_add));

        // assign actions when buttons clicked
        txtLhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // set correct views
                if(lisCompany.size() > 0) {

                    lsv.setVisibility(View.VISIBLE);
                    llv.setVisibility(View.GONE);
                }
                else {

                    lsv.setVisibility(View.GONE);
                    llv.setVisibility(View.VISIBLE);
                }
                //adapter.notifyDataSetChanged();
                //nothing
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

/*
        // set custom toolbar
        Toolbar tlb = (Toolbar) view.findViewById(R.id.tlb_watch_list);
        getContext().setSupportActionBar(tlb);
*/


        ////////
        // LIST STUFF
        //
        // set ListView
        // retrieve companies
        //List<Company> lisCompany = new ArrayList<>();
        lisCompany = daoImpl.getAllCompanies();
        adapter = new CompanyListAdapter(this.getActivity(), lisCompany);
        lsv.setAdapter(adapter);


        // assign action on ListView click
        lsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // set selected Company
                Company company = (Company) lsv.getAdapter().getItem(position);

                ////////
                // HOLLER FRAGMENT NAVIGATION
                //
                // create instance of next Fragment Object
                CompanyFragment fragment = new CompanyFragment();
                // set company
                daoImpl.setSelectedCompany(company);
                // navigate
                FragmentNavigation.navigationToFragment(getActivity(), fragment);
                //
                //
                ////////

/*
                ////////
                // HOLLER PASS OBJECT TO FRAGMENT
                // 1 of 1
                // 2 in [Next Fragment] Class
                //
                // navigation to CompanyFragment
                CompanyFragment fragment = CompanyFragment.newInstance(company);
                //
                //
                ////////
*/

            }
        });

        lsv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // set selected Company
                Company company = (Company) lsv.getAdapter().getItem(position);
                // navigate
                //navigationEditCompany(company);

                showCompanyPopup(view, company);
                return true;
            }
        });


/*
        // NEED TO DO OBSERVE WHEN CHECKING DATABASE
        // ELSE IT MAY RUN TOO FAST AND NOT INSERT VALUES
        daoImpl.daoAccess().fetchAllPersons().observe(getActivity(), new Observer<List<Company>>() {
            @Override
            public void onChanged(@Nullable List<Company> lisCompany) {

                dataList.clear();
                for (Company c : lisCompany){
                    dataList.add(c);
                }
                adapter.notifyDataSetChanged();
            }
        });
*/



        //
        //
        ////////

        ////////
        // EMPTY LISTVIEW
        //
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
        if(lisCompany.size() > 0) {

            lsv.setVisibility(View.VISIBLE);
            llv.setVisibility(View.GONE);
        }
        else {

            lsv.setVisibility(View.GONE);
            llv.setVisibility(View.VISIBLE);
        }



        // runs when BackStack changes
        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {

                        if(lisCompany.size() > 0) {

                            lsv.setVisibility(View.VISIBLE);
                            llv.setVisibility(View.GONE);
                        }
                        else {

                            lsv.setVisibility(View.GONE);
                            llv.setVisibility(View.VISIBLE);
                        }

/*
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        if (fm != null) {
                            int backStackCount = fm.getBackStackEntryCount();
                            if (backStackCount == 0) {
                                if (getSupportActionBar() != null) {
                                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
                                }
                                setToolbarTittle(R.string.app_name);
                            } else {
                                if (getSupportActionBar() != null) {
                                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
                                }
                            }
                        }
*/
                    }
                });



        // Inflate the layout for this fragment
        return view;

    }


    @Override
    public void onStart(){
        super.onStart();

        //lisCompany = daoImpl.getAllCompanies();

        lisCompany.add(new Company("Apple", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Apple_logo_black.svg/1024px-Apple_logo_black.svg.png", "APPL"));
        lisCompany.add(new Company("Google", "https://www.trainingtoyou.com/wp-content/uploads/2018/08/2000px-Google__G__Logo.svg_.png", "GOOGL"));
        lisCompany.add(new Company("Tesla", "https://pngimg.com/uploads/tesla_logo/tesla_logo_PNG19.png", "TSLA"));
        lisCompany.add(new Company("Twitter", "https://buzzhostingservices.com/images/twitter-logo-png-2.png", "TWTR"));

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
}
