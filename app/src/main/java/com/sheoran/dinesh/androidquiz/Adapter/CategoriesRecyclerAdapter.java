package com.sheoran.dinesh.androidquiz.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sheoran.dinesh.androidquiz.R;
import com.sheoran.dinesh.androidquiz.listener.CategoryRecyclerClickListener;
import com.sheoran.dinesh.androidquiz.model.Category;

import java.util.ArrayList;

public class CategoriesRecyclerAdapter extends RecyclerView.Adapter<CategoriesRecyclerAdapter.MyViewHolder>  {
    private ArrayList<Category> categoryArrayList;
    private Context _context;
    private CategoryRecyclerClickListener _clickListener;

    public CategoriesRecyclerAdapter(Context context, CategoryRecyclerClickListener clickListener, ArrayList<Category> categories) {
        this._context = context;
        this._clickListener = clickListener;
        this.categoryArrayList = categories;
    }
    @Override
    public CategoriesRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_display, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoriesRecyclerAdapter.MyViewHolder holder, int position) {
        Category category = categoryArrayList.get(position);
        if(category == null) return;

        final String categName = category.getCategoryName();
        holder.txtCateg.setText(categName);
        holder.cardViewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _clickListener.onSingleClickListener(categName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCateg;
        public CardView cardViewContainer;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtCateg = itemView.findViewById(R.id.txt_category);
            cardViewContainer = itemView.findViewById(R.id.cardViewContainer);
        }
    }
}
