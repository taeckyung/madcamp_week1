package com.example.q.myapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Tab3Game extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Button mine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3etc, container, false);

        mine = rootView.findViewById(R.id.startmine);

        mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MineSweeper.class);
                Log.e("onclick","intent");
                startActivity(i);
                Log.e("onclick","sAct");
            }

        });
        return rootView;
    }
}