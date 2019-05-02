package com.sheoran.dinesh.androidquiz.fragment;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sheoran.dinesh.androidquiz.Adapter.CategoriesRecyclerAdapter;
import com.sheoran.dinesh.androidquiz.R;
import com.sheoran.dinesh.androidquiz.databinding.FragmentCategoryViewBinding;
import com.sheoran.dinesh.androidquiz.firebase.CategoryFirebaseConfig;
import com.sheoran.dinesh.androidquiz.listener.CategoryRecyclerClickListener;
import com.sheoran.dinesh.androidquiz.listener.FirebaseDataChangeAdapter;
import com.sheoran.dinesh.androidquiz.listener.FirebaseDataChangeNotifier;
import com.sheoran.dinesh.androidquiz.model.Category;
import com.sheoran.dinesh.androidquiz.util.ProgressDialogUtil;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends BaseFragment implements CategoryRecyclerClickListener {
    public static final String CATEGORY_KEY = "com.sheoran.dinesh.androidquiz.fragment.CategoriesFragment";
    private RecyclerView _recyclerView;
    private ProgressDialog _progressDialog;

    private CategoriesRecyclerAdapter _recyclerAdapter;
    private ArrayList<Category> _categoryArrayList;

    private CategoryFirebaseConfig _categoryFirebaseConfig;

    private FirebaseDataChangeNotifier firebaseDataChangeNotifier = new FirebaseDataChangeAdapter() {
        @Override
        public void onLoad(boolean isSuccess, String msg) {
            _progressDialog.dismiss();
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                updateRecyclerViewAdapter(_categoryFirebaseConfig.getCategoriesList());
            }
        }
    };

    public CategoriesFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentCategoryViewBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_category_view, null, false);
        _recyclerView = binding.categoryDisplayRecycler;

        init();
        setupRecyclerViewAdapter();
        loadCategory();

        return binding.getRoot();
    }

    private void init() {
        _categoryFirebaseConfig = CategoryFirebaseConfig.getInstance(getContext());
        _categoryFirebaseConfig.setFirebaseDataChangeNotifier(firebaseDataChangeNotifier);
        _categoryArrayList = new ArrayList<>();
    }

    private void setupRecyclerViewAdapter() {
        _recyclerAdapter = new CategoriesRecyclerAdapter(this, _categoryArrayList);
        _recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        _recyclerView.setAdapter(_recyclerAdapter);
    }

    private void updateRecyclerViewAdapter(List<Category> categoryList) {
        _categoryArrayList.clear();
        _categoryArrayList.addAll(categoryList);
        _recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _categoryFirebaseConfig.unsetFirebaseDataChangeNotifier();
    }

    private void loadCategory() {
        _progressDialog = ProgressDialogUtil.getDialog(getContext(), "Please wait", "Loading...");
        _progressDialog.show();
        _categoryFirebaseConfig.requestToLoad();
    }

    @Override
    public void onSingleClickListener(String categoryName) {
        openQuestionFragment(categoryName);
    }

    private void openQuestionFragment(String categoryName){
        Bundle bundle = new Bundle();
        bundle.putSerializable(CATEGORY_KEY, new Category(categoryName));

        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setArguments(bundle);
        replaceFragment(questionFragment, R.id.container);
    }
}
