package com.example.mephiguide.ui.telephony;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mephiguide.R;
import com.example.mephiguide.data_types.Contact;

import java.util.ArrayList;

public class TelephonyAdapter extends BaseAdapter {
    private LayoutInflater lInflater;
    private ArrayList<Contact> objects;
    private TelephonyFragment fragment;
    private Context context;

    TelephonyAdapter(Context context, TelephonyFragment frag, ArrayList<Contact> products) {
        objects = products;
        fragment = frag;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.telephonyitem, parent, false);
        }

        final Contact contact = getContact(position);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!contact.isAnnotation()){
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + Uri.encode(contact.getNumber())));
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                    }

                }
            }
        });

        TextView nameTextView = view.findViewById(R.id.tvNumberName);
        nameTextView.setText(contact.getName());
        TextView phoneTextView =  view.findViewById(R.id.tvPhone);
        if (contact.isAnnotation()) {
            phoneTextView.setVisibility(View.GONE);
            nameTextView.setPadding(0, 5, 0, 10);
        }
        else
            phoneTextView.setText(contact.getNumber());
        return view;
    }

    private Contact getContact(int position) {
        return ((Contact) getItem(position));
    }
}
