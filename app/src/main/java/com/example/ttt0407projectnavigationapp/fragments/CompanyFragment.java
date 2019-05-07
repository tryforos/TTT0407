package com.example.ttt0407projectnavigationapp.fragments;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ttt0407projectnavigationapp.CompanyDragListAdapter;
import com.example.ttt0407projectnavigationapp.CompanyListAdapter;
import com.example.ttt0407projectnavigationapp.FragmentNavigation;
import com.example.ttt0407projectnavigationapp.ImageDownloader;
import com.example.ttt0407projectnavigationapp.ProductDragListAdapter;
import com.example.ttt0407projectnavigationapp.ProductListAdapter;
import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.IDaoObserver;
import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;
import com.woxthebox.draglistview.DragItem;
import com.woxthebox.draglistview.DragListView;
import com.woxthebox.draglistview.swipe.ListSwipeHelper;
import com.woxthebox.draglistview.swipe.ListSwipeItem;

import java.util.ArrayList;
import java.util.List;


public class CompanyFragment extends Fragment implements IDaoObserver {

    private Company company;
    View view;

    //ProductListAdapter adapter = null;
    ProductDragListAdapter adapter = null;

    List<Product> lisProducts = new ArrayList<>();
    ArrayList<Pair<Long, Product>> alsProducts;
    DragListView lsv = null;

    DaoImpl daoImpl = null;
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

        daoImpl = DaoImpl.getInstance(getContext());
        context = getContext();

        // set company
        company = daoImpl.getSelectedCompany();

        lsv = (DragListView) view.findViewById(R.id.lsv_products);

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
        // retrieve products
        lisProducts = daoImpl.getLisProducts();

        // declare ArrayList
        alsProducts = new ArrayList<>();

        // set ListView
        //adapter = new ProductListAdapter(this.getActivity(), daoImpl.getLisProducts());
        adapter = new ProductDragListAdapter(alsProducts, R.layout.row_product, R.id.img_product_logo, false, getContext(), getActivity());
        lsv = (DragListView) view.findViewById(R.id.lsv_products);
        //lsv.setAdapter(adapter);
        lsv.setAdapter(adapter, true);

        lsv.setLayoutManager(new LinearLayoutManager(getContext()));
        lsv.setCanDragHorizontally(false);
        lsv.setCustomDragItem(new CompanyFragment.MyDragItem(getContext(), R.layout.row_product));

/*
        // MOVED TO ProductDragListAdapter

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
*/
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

        ////////
        // LIST DRAG-AND-Drop
        //
        lsv.setDragListListener(new DragListView.DragListListenerAdapter() {

            @Override
            public void onItemDragStarted(int position) {
                //mRefreshLayout.setEnabled(false);
                //Toast.makeText(lsv.getContext(), "CompanyFragment: Start - position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                //mRefreshLayout.setEnabled(true);

                if (fromPosition != toPosition) {

                    Product p = lisProducts.get(toPosition);

                    //resetProductPositions();

                    String str = "";
                    for (int i = 0; i < lisProducts.size(); i++) {
                        str = lisProducts.get(i).getStrName() +": " + lisProducts.get(i).getIntPostion();
                        // shift all intPosition variables between to & from
                        if (fromPosition < toPosition) {
                            // from low to high
                            if (lisProducts.get(i).getIntPostion() == fromPosition) {

                                lisProducts.get(i).setIntPostion(toPosition);
                            }
                            else if (lisProducts.get(i).getIntPostion() > fromPosition && lisProducts.get(i).getIntPostion() <= toPosition) {

                                lisProducts.get(i).setIntPostion(lisProducts.get(i).getIntPostion() - 1);
                            }
                        } else { // toPosition < fromPosition
                            // from high to low
                            if (lisProducts.get(i).getIntPostion() == fromPosition) {

                                lisProducts.get(i).setIntPostion(toPosition);
                            }
                            else if (lisProducts.get(i).getIntPostion() >= toPosition && lisProducts.get(i).getIntPostion() < fromPosition) {

                                lisProducts.get(i).setIntPostion(lisProducts.get(i).getIntPostion() + 1);
                            }
                        }

                        // set on dao
                        daoImpl.setLisProducts(lisProducts);

                        // update log
                        str = str + " -> " + lisProducts.get(i).getIntPostion();
                        Log.i("P Position Moves", str);
                    }

                    // update in database, but don't refresh
                    daoImpl.setBooDoNotRefresh(true);
                    daoImpl.executeUpdateProducts(lisProducts);
                }
            }
        });

        lsv.setSwipeListener(new ListSwipeHelper.OnSwipeListenerAdapter() {

            @Override
            public void onItemSwipeStarted(ListSwipeItem item) {
                //mRefreshLayout.setEnabled(false);
                //Toast.makeText(lsv.getContext(), "CompanyFragment: onItemSwipeStarted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemSwipeEnded(ListSwipeItem item, ListSwipeItem.SwipeDirection swipedDirection) {
                //mRefreshLayout.setEnabled(true);

                if (swipedDirection == ListSwipeItem.SwipeDirection.LEFT) {
                    //Toast.makeText(lsv.getContext(), "CompanyFragment: Swipe Left", Toast.LENGTH_SHORT).show();

                    // delete swiped item
                    //Pair<Long, String> adapterItem = (Pair<Long, String>) item.getTag();
                    //int pos = mDragListView.getAdapter().getPositionForItem(adapterItem);
                    //mDragListView.getAdapter().removeItem(pos);
                } else if (swipedDirection == ListSwipeItem.SwipeDirection.RIGHT) {
                    //Toast.makeText(lsv.getContext(), "CompanyFragment: Swipe Right", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //
        //
        ////////


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

    ////////
    // ListView Drag-and-Drop
    //
    private void resetProductPositions(){
        // resets intPositions on all companies

        lisProducts = daoImpl.getLisProducts();

        String str = "";

        for (int i = 0; i < lisProducts.size(); i++) {

            str = lisProducts.get(i).getStrName() +": " + lisProducts.get(i).getIntPostion();

            lisProducts.get(i).setIntPostion(i);

            str = str + " -> " + lisProducts.get(i).getIntPostion();
            Log.i("Reset Product Positions", str);
        }

        daoImpl.executeUpdateProducts(lisProducts);
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
            ImageView imgProductLogo = clickedView.findViewById(R.id.img_product_logo);
            TextView txtProductDescription = clickedView.findViewById(R.id.txt_product_description);
            TextView txtProductPrice = clickedView.findViewById(R.id.txt_product_price);

            // add to dragView
            BitmapDrawable bitmapDrawable = ((BitmapDrawable) imgProductLogo.getDrawable());
            Bitmap bitmap = bitmapDrawable .getBitmap();
            ((ImageView) dragView.findViewById(R.id.img_product_logo)).setImageBitmap(bitmap);
            ((TextView) dragView.findViewById(R.id.txt_product_description)).setText(txtProductDescription.getText());
            //((TextView) dragView.findViewById(R.id.txt_stock_price)).setText(txtProductPrice.getText());

            dragView.findViewById(R.id.lsi_product_row).setBackgroundColor(dragView.getResources().getColor(R.color.colorBackgroundOnDrag));
        }
    }
    //
    //
    ////////

/*
    // MOVED TO ProductDragListAdapter

    //POP UP MENU
    public void showProductPopup(View view, final Product product) {
        // Edit & Delete pop-up menu
        // appears onLongClick of list item

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
*/

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
/*
    // MOVED TO ProductDragListAdapter

    private void navigationEditProduct(Product product){

        // MOVED TO ProductDragListAdapter

        // set product
        daoImpl.setSelectedProduct(product);
        // create instance of next Fragment Object
        EditProductFragment fragment = new EditProductFragment();
        // navigate
        FragmentNavigation.navigationToFragment(getActivity(), fragment);
    }
    private void navigationDeleteProduct(Product product){

        // MOVED TO ProductDragListAdapter

        // delete product
        daoImpl.executeDeleteProduct(product);
    }
*/
    //
    //
    ////////

    @Override
    public void update() {

        Log.i("CFrag Update Count", "LULZ");

        updateAdapter();

        updateCompanyInfo();
    }
    public void updateAdapter(){

        // if products were updated
        lisProducts = daoImpl.getLisProducts();

        // clear ArrayList and copy new Products
        alsProducts.clear();
        int n = lisProducts.size();
        for (int i = 0; i < n; i++) {
            alsProducts.add(new Pair<>((long) i, lisProducts.get(i)));
        }

        // refresh adapter
        adapter.notifyDataSetChanged();

        if (daoImpl.getBooPostDelete()){
            // if we just deleted, intPosition has been updated in lisProducts but not in database
            // this updates in database but does not refresh CompanyFragment again
            daoImpl.setBooPostDelete(false);
            daoImpl.setBooDoNotRefresh(true);
            daoImpl.executeUpdateProducts(lisProducts);
        }
    }
    public void updateCompanyInfo() {

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

        txtCompanyName.setText(company.getStrName() + " (" + company.getStrStockTicker() + ")");
    }
}
