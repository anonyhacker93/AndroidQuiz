package com.sheoran.dinesh.androidquiz.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.CardView;
import com.sheoran.dinesh.androidquiz.R;

public class HomeFragment extends BaseFragment {

    private CardView _practiceCard;
    private CardView _learnCard;
    private CardView _notesCard;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        _practiceCard = view.findViewById(R.id.practiceCard);
        _learnCard = view.findViewById(R.id.learnCard);
        _notesCard = view.findViewById(R.id.notesCard);


        _practiceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             replaceFragment(new CategoriesFragment(),R.id.container);
            }
        });

        _learnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        _notesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }

}
