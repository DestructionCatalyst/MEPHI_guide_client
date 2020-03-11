package com.example.mephiguide.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.mephiguide.R;
import com.example.mephiguide.data_types.News;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<News> objects;
    CheckBox cbBuy;
    HomeFragment fragment;

    MyAdapter(Context context, HomeFragment frag, ArrayList<News> products) {
        ctx = context;
        objects = products;
        fragment = frag;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.newsitem, parent, false);
        }

        News pnews = getNews(position);


        ((TextView) view.findViewById(R.id.tvName)).setText(pnews.name);
        ((TextView) view.findViewById(R.id.tvTime)).setText(pnews.t);
        ((TextView) view.findViewById(R.id.tvPlace)).setText(pnews.place);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fragment.ReplaceMeDaddy(getNews(position));
                //TODO Implement navigation
            }
        });

        return view;
    }

    // напом. по позиции
    News getNews(int position) {
        return ((News) getItem(position));
    }




}
