package com.example.ttt0407projectnavigationapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ttt0407projectnavigationapp.fragments.CompanyFragment;
import com.example.ttt0407projectnavigationapp.fragments.EditCompanyFragment;
import com.example.ttt0407projectnavigationapp.fragments.EditProductFragment;
import com.example.ttt0407projectnavigationapp.fragments.ProductFragment;
import com.example.ttt0407projectnavigationapp.fragments.WatchListFragment;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;
import com.woxthebox.draglistview.DragItemAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductDragListAdapter extends DragItemAdapter<Pair<Long, Product>, ProductDragListAdapter.ViewHolder> {
    // for drag-and-drop of ListView items

    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;
    private Context context;

    private FragmentActivity fa;

    DaoImpl daoImpl = null;
    List<Product> lisProducts;

    // constructors
    public ProductDragListAdapter(ArrayList<Pair<Long, Product>> list, int layoutId, int grabHandleId, boolean dragOnLongPress, Context context, FragmentActivity fa) {
        super();
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;

        this.context = context;
        this.fa = fa;

        daoImpl = DaoImpl.getInstance(context);
        lisProducts = daoImpl.getLisProducts();
        setItemList(list);
    }
    // END constructors

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    // updates info on row XML
    @Override
    public void onBindViewHolder(@NonNull ProductDragListAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Product p = mItemList.get(position).second;

        new ImageDownloader(holder.imgProductLogo, context, p.getIntId()).execute(p.getStrImageUrl());

        holder.txtProductDescription.setText(p.getStrName());

        holder.itemView.setTag(mItemList.get(position));
    }

    @Override
    public long getUniqueItemId(int position) {
        return mItemList.get(position).first;
    }

    class ViewHolder extends DragItemAdapter.ViewHolder {

        ImageView imgProductLogo;
        TextView txtProductDescription;
        TextView txtProductPrice;

        ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);

            imgProductLogo = (ImageView) itemView.findViewById(R.id.img_product_logo);
            txtProductDescription = (TextView) itemView.findViewById(R.id.txt_product_description);
            txtProductPrice = (TextView) itemView.findViewById(R.id.txt_product_price);
        }

        // onClick of item in ListView on CompanyFragment
        @Override
        public void onItemClicked(View view) {
            // navigate to Product page

            Product p = lisProducts.get(this.getAdapterPosition());
            daoImpl.setSelectedProduct(p);

            // create instance of next Fragment Object
            ProductFragment fragment = new ProductFragment();
            // navigate
            FragmentNavigation.navigationToFragment(fa, fragment);
        }

        // onLongClick of item in ListView on WatchListFragment
        @Override
        public boolean onItemLongClicked(View view) {

            Product p = lisProducts.get(this.getAdapterPosition());
            showProductPopup(view, p);

            return true;
        }

        //POP UP MENU
        public void showProductPopup(View view, final Product p) {
            // Edit & Delete pop-up menu
            // appears onLongClick of list item

            PopupMenu popup = new PopupMenu(fa, view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_product_delete_edit, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                public boolean onMenuItemClick(MenuItem item) {

                    if (item.getTitle().toString().equals("Edit")) {
                        // set product
                        daoImpl.setSelectedProduct(p);
                        // create instance of next Fragment Object
                        EditProductFragment fragment = new EditProductFragment();
                        // navigate
                        FragmentNavigation.navigationToFragment(fa, fragment);
                    }
                    else{
                        // delete product
                        daoImpl.executeDeleteProduct(p);
                        Toast.makeText(fa,p.getStrName() + " deleted", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
            });
            popup.show();
        }
    }
}
