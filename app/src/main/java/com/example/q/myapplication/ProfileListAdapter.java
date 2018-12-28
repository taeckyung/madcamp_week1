package com.example.q.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileListAdapter extends BaseAdapter {
    private LayoutInflater _inflater;
    private ArrayList _profiles;
    private int _layout;

    public ProfileListAdapter(Context context, int layout, ArrayList profiles) {
        _inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _profiles = profiles;
        _layout = layout;
    }

    @Override
    public int getCount() {
        return _profiles.size();
    }

    @Override
    public String getItem(int pos) {
        return _profiles.get(pos).toString();
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = _inflater.inflate(_layout, parent, false);
        }

        Profile profile = (Profile) _profiles.get(pos);

        TextView name = convertView.findViewById(R.id.name);
        name.setText(profile.getName());

        TextView phone = convertView.findViewById(R.id.phone);
        phone.setText(profile.getPhone());

        TextView email = convertView.findViewById(R.id.email);
        email.setText(profile.getEmail());

        return convertView;
    }

}
