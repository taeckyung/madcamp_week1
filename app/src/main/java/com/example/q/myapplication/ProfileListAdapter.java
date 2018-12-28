package com.example.q.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileListAdapter extends BaseAdapter {
    private LayoutInflater _inflater;
    private ArrayList<Profile> _profiles;
    private int _layout;
    private final Context _context;

    public ProfileListAdapter(Context context, int layout, ArrayList profiles) {
        _inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _profiles = profiles;
        _layout = layout;
        _context = context;
    }

    @Override
    public int getCount() {
        return _profiles.size();
    }

    @Override
    public String getItem(int pos) {
        return _profiles.get(pos).getName();
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

        final Profile profile = _profiles.get(pos);

        TextView name = convertView.findViewById(R.id.name);
        name.setText(profile.getName());

        TextView phone = convertView.findViewById(R.id.phone);
        phone.setText(profile.getPhone());

        TextView email = convertView.findViewById(R.id.email);
        email.setText(profile.getEmail());

        LinearLayout make_call = convertView.findViewById(R.id.phone_layout);
        LinearLayout make_mail = convertView.findViewById(R.id.email_layout);
        TextView modify_contact = convertView.findViewById(R.id.name);

        make_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri phone_number = Uri.parse("tel:" + profile.getPhone());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, phone_number);
                callIntent.putExtra("finishActivityOnSaveCompleted", true);
                _context.startActivity(callIntent);
            }
        });
        make_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {profile.getEmail()});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                emailIntent.putExtra("finishActivityOnSaveCompleted", true);
                _context.startActivity(Intent.createChooser(emailIntent, "Send email to " + profile.getName()));
            }
        });
        modify_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactIntent = new Intent(Intent.ACTION_EDIT);
                contactIntent.setDataAndType(profile.getContactUri(), ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                contactIntent.putExtra("finishActivityOnSaveCompleted", true);
                _context.startActivity(contactIntent);

            }
        });

        return convertView;
    }

}
