package com.example.navbar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Academics extends Fragment {

   private  GridLayout mainGird;

    public Academics() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_academics, container, false);
        mainGird = (GridLayout) v.findViewById(R.id.mainGrid);
        setSingleEvent(mainGird);



        return v;
    }

    private void setSingleEvent(GridLayout mainGird) {
        for(int i=0;i<mainGird.getChildCount();i++){
            CardView cardView = (CardView)mainGird.getChildAt(i);
            final int f = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.cardviewLaw:
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.root_layout,new Law())
                                    .commit();
                            break;
                        case R.id.cardviewengineering:
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.root_layout,new Engineering())
                                    .commit();
                            break;
                        case R.id.cardvieweducation:
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.root_layout,new Education())
                                    .commit();
                            break;
                        case R.id.cardviewbusiness:
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.root_layout,new Business())
                                    .commit();
                            break;

                    }
                }
            });
        }
    }




}
