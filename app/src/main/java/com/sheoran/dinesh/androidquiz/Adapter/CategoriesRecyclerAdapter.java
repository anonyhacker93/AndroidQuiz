package com.sheoran.dinesh.androidquiz.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sheoran.dinesh.androidquiz.R;
import com.sheoran.dinesh.androidquiz.databinding.RowCategoryDisplayBinding;
import com.sheoran.dinesh.androidquiz.listener.CategoryRecyclerClickListener;
import com.sheoran.dinesh.androidquiz.model.Category;

import java.util.ArrayList;

public class CategoriesRecyclerAdapter extends RecyclerView.Adapter<CategoriesRecyclerAdapter.MyViewHolder> {
    private ArrayList<Category> categoryArrayList;

    private CategoryRecyclerClickListener _clickListener;

    public CategoriesRecyclerAdapter(CategoryRecyclerClickListener clickListener, ArrayList<Category> categories) {
        this._clickListener = clickListener;
        this.categoryArrayList = categories;
    }

    @Override
    public CategoriesRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowCategoryDisplayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_category_display, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CategoriesRecyclerAdapter.MyViewHolder holder, int position) {
        Category category = categoryArrayList.get(position);
        holder.binding.setCategory(category);
        holder.binding.cardViewContainer.setOnClickListener((v) -> {
            _clickListener.onSingleClickListener(category.getCategoryName());
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RowCategoryDisplayBinding binding;

        public MyViewHolder(RowCategoryDisplayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
