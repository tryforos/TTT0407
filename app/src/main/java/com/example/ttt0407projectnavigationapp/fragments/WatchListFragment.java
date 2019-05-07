package com.example.ttt0407projectnavigationapp.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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

import com.example.ttt0407projectnavigationapp.CompanyDragListAdapter;
import com.example.ttt0407projectnavigationapp.CompanyListAdapter;
import com.example.ttt0407projectnavigationapp.FragmentNavigation;
import com.example.ttt0407projectnavigationapp.ImageDownloader;
import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.StockPriceDownloader;
import com.example.ttt0407projectnavigationapp.Utilities;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.IDaoObserver;
import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.woxthebox.draglistview.DragItem;
import com.woxthebox.draglistview.DragListView;
import com.woxthebox.draglistview.swipe.ListSwipeHelper;
import com.woxthebox.draglistview.swipe.ListSwipeItem;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class WatchListFragment extends Fragment implements IDaoObserver {

    View view;

    //CompanyListAdapter adapter = null;
    CompanyDragListAdapter adapter = null;

    List<Company> lisCompanies;
    ArrayList<Pair<Long, Company>> alsCompanies;

    //ListView lsv = null;
    DragListView lsv = null;
    TextView txt = null;
    LinearLayout llv  = null;

    DaoImpl daoImpl = null;


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

        //lsv = (ListView) view.findViewById(R.id.lsv_companies);
        lsv = (DragListView) view.findViewById(R.id.lsv_companies);
        lsv.getRecyclerView().setVerticalScrollBarEnabled(true);

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
                Utilities.hideKeyboard(getActivity());
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

        // declare ArrayList
        alsCompanies = new ArrayList<>();

        adapter = new CompanyDragListAdapter(alsCompanies, R.layout.row_company, R.id.img_company_logo, false, getContext(), getActivity());
        //lsv.setAdapter(adapter);
        lsv.setAdapter(adapter, true);

        lsv.setLayoutManager(new LinearLayoutManager(getContext()));
        lsv.setCanDragHorizontally(false);
        lsv.setCustomDragItem(new MyDragItem(getContext(), R.layout.row_company));

/*
        // MOVED TO CompanyDragListAdapter

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
*/

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

        ////////
        // UPDATE LISTVIEW WHEN DATABASE CHANGES
        //

        // auto-run functions on database update are all in DAO

        //
        //
        ////////

        // runs when BackStack changes
        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(
            new FragmentManager.OnBackStackChangedListener() {
                public void onBackStackChanged() {
                    update();
                    Utilities.hideKeyboard(getActivity());
                }
            }
        );

        ////////
        // TIME-REPEATED CODE
        //
        // pull stock quotes every 60 sec
        final Handler handler=new Handler();

        Runnable runnableCode = new Runnable() {

            @Override
            public void run() {
                updateStockPrices(lisCompanies);

                //executes the same Runnable task after a delay of 2000 milliseconds.
                handler.postDelayed(this, 60000);
            }
        };

        //execute a Runnable task written inside run() method on the UIThread
        handler.post(runnableCode);
        //
        //
        ////////

        ////////
        // LIST DRAG-AND-Drop
        //
        lsv.setDragListListener(new DragListView.DragListListenerAdapter() {

            @Override
            public void onItemDragStarted(int position) {
                //mRefreshLayout.setEnabled(false);
                //Toast.makeText(lsv.getContext(), "WatchListFragment: Start - position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                //mRefreshLayout.setEnabled(true);

                if (fromPosition != toPosition) {

                    Company company = lisCompanies.get(toPosition);

                    //resetCompanyPositions();

                    String str = "";

                    for (int i = 0; i < lisCompanies.size(); i++) {

                        // for log
                        str = lisCompanies.get(i).getStrName() +": " + lisCompanies.get(i).getIntPosition();

                        // shift all intPosition variables between to & from
                        if (fromPosition < toPosition) {
                            // from low to high
                            if (lisCompanies.get(i).getIntPosition() == fromPosition) {

                                lisCompanies.get(i).setIntPosition(toPosition);
                            }
                            else if (lisCompanies.get(i).getIntPosition() > fromPosition && lisCompanies.get(i).getIntPosition() <= toPosition) {

                                lisCompanies.get(i).setIntPosition(lisCompanies.get(i).getIntPosition() - 1);
                            }
                        } else { // toPosition < fromPosition

                            // from high to low
                            if (lisCompanies.get(i).getIntPosition() == fromPosition) {

                                lisCompanies.get(i).setIntPosition(toPosition);
                            }
                            else if (lisCompanies.get(i).getIntPosition() >= toPosition && lisCompanies.get(i).getIntPosition() < fromPosition) {

                                lisCompanies.get(i).setIntPosition(lisCompanies.get(i).getIntPosition() + 1);
                            }
                        }

                        // update log
                        str = str + " -> " + lisCompanies.get(i).getIntPosition();
                        Log.i("C Position Moves", str);
                    }

                    // update in database, but don't refresh
                    daoImpl.setBooDoNotRefresh(true);
                    daoImpl.executeUpdateCompanies(lisCompanies);
                }
            }
        });

        lsv.setSwipeListener(new ListSwipeHelper.OnSwipeListenerAdapter() {

            @Override
            public void onItemSwipeStarted(ListSwipeItem item) {
                //mRefreshLayout.setEnabled(false);
                //Toast.makeText(lsv.getContext(), "WatchListFragment: onItemSwipeStarted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemSwipeEnded(ListSwipeItem item, ListSwipeItem.SwipeDirection swipedDirection) {
                //mRefreshLayout.setEnabled(true);

                if (swipedDirection == ListSwipeItem.SwipeDirection.LEFT) {
                    //Toast.makeText(lsv.getContext(), "WatchListFragment: Swipe Left", Toast.LENGTH_SHORT).show();

                    // delete swiped item
                    //Pair<Long, String> adapterItem = (Pair<Long, String>) item.getTag();
                    //int pos = mDragListView.getAdapter().getPositionForItem(adapterItem);
                    //mDragListView.getAdapter().removeItem(pos);
                } else if (swipedDirection == ListSwipeItem.SwipeDirection.RIGHT) {
                    //Toast.makeText(lsv.getContext(), "WatchListFragment: Swipe Right", Toast.LENGTH_SHORT).show();
                }
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

    ////////
    // ListView Drag-and-Drop
    //
    private void resetCompanyPositions(){
        // resets intPositions on all companies if they get messed up

        lisCompanies = daoImpl.getLisCompanies();

        String str = "";

        for (int i = 0; i < lisCompanies.size(); i++) {

            str = lisCompanies.get(i).getStrName() +": " + lisCompanies.get(i).getIntPosition();

            lisCompanies.get(i).setIntPosition(i);

            str = str + " -> " + lisCompanies.get(i).getIntPosition();
            Log.i("Reset Company Positions", str);
        }

        daoImpl.executeUpdateCompanies(lisCompanies);
    }

    private static class MyDragItem extends DragItem {

        private Context context;

        // constructors
        MyDragItem(Context context, int layoutId) {
            super(context, layoutId);
        }
        // END constructors

        @Override
        public void onBindDragView(View clickedView, View dragView) {
            // dictates the layout of the view being dragged

            // set views from clickedView
            ImageView imgCompanyLogo = clickedView.findViewById(R.id.img_company_logo);
            TextView txtCompanyInfo = clickedView.findViewById(R.id.txt_company_info);
            TextView txtStockPrice = clickedView.findViewById(R.id.txt_stock_price);

            // add to dragView
            BitmapDrawable bitmapDrawable = ((BitmapDrawable) imgCompanyLogo.getDrawable());
            Bitmap bitmap = bitmapDrawable .getBitmap();
            ((ImageView) dragView.findViewById(R.id.img_company_logo)).setImageBitmap(bitmap);
            ((TextView) dragView.findViewById(R.id.txt_company_info)).setText(txtCompanyInfo.getText());
            ((TextView) dragView.findViewById(R.id.txt_stock_price)).setText(txtStockPrice.getText());

            dragView.findViewById(R.id.lsi_company_row).setBackgroundColor(dragView.getResources().getColor(R.color.colorBackgroundOnDrag));
        }
    }
    //
    //
    ////////

    //SET CORRECT VIEW
    private void setCorrectView(){

        if(lisCompanies.size() > 0) {
            // if companies, display them
            //Toast.makeText(lsv.getContext(), "There are companies! #" + lisCompanies.size(), Toast.LENGTH_LONG).show();
            lsv.setVisibility(View.VISIBLE);
            llv.setVisibility(View.GONE);
        }
        else {
            // if no companies, display "+ Add Company" w image
            lsv.setVisibility(View.GONE);
            llv.setVisibility(View.VISIBLE);
        }
    }

/*
    // MOVED TO CompanyDragListAdapter

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
*/


    ////////
    // NAVIGATION METHODS
    //
    private void navigationAddCompany(){
        // create instance of next Fragment Object
        AddCompanyFragment fragment = new AddCompanyFragment();
        // navigate
        FragmentNavigation.navigationToFragment(getActivity(), fragment);
    }
/*
    // MOVED TO CompanyDragListAdapter

    private void navigationEditCompany(Company company){
        // set company
        daoImpl.setSelectedCompany(company);
        // create instance of next Fragment Object
        EditCompanyFragment fragment = new EditCompanyFragment();
        // navigate
        FragmentNavigation.navigationToFragment(getActivity(), fragment);
    }
    private void navigationDeleteCompany(Company company){
        // delete company
        daoImpl.executeDeleteCompany(company);
    }
*/
    //
    //
    ////////

    // from interface
    @Override
    public void update() {

        Log.i("WLFrag Update Count", "LULZ");

        updateAdapter();
    }

    public void updateAdapter(){

        lisCompanies = daoImpl.getLisCompanies();

        setCorrectView();

        // clear ArrayList and copy new Companies
        alsCompanies.clear();
        int n = lisCompanies.size();
        //alsCompanies = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            alsCompanies.add(new Pair<>((long) i, lisCompanies.get(i)));
        }

        // refresh adapter
        adapter.notifyDataSetChanged();

        if (daoImpl.getBooPostDelete()){
            // if we just deleted, intPosition has been updated in lisCompanies but not in database
            // this updates in database but does not refresh WatchListFragment again
            daoImpl.setBooPostDelete(false);
            daoImpl.setBooDoNotRefresh(true);
            daoImpl.executeUpdateCompanies(lisCompanies);
        }
    }

    public void updateStockPrices(List<Company> lisC) {

        if(lisC.size() > 0) {

            daoImpl = DaoImpl.getInstance(getContext());

            new StockPriceDownloader().execute();
            daoImpl.executeUpdateCompanies(lisC);
        }
    }
}
