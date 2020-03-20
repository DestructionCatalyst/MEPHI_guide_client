package com.example.mephiguide.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.mephiguide.R;
import com.example.mephiguide.data_types.News;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    private ArrayList<News> objects;
    private HomeFragment fragment;

    NewsAdapter(Context context, HomeFragment frag, ArrayList<News> products) {
        objects = products;
        fragment = frag;
        lInflater = (LayoutInflater) context
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

        final News pnews = getNews(position);

        ((TextView) view.findViewById(R.id.tvName)).setText(pnews.getName());
        ((TextView) view.findViewById(R.id.tvTime)).setText(pnews.getT());
        ((TextView) view.findViewById(R.id.tvPlace)).setText(pnews.getPlace());

        final View finalView = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("data", fragment.getString(R.string.web_start)
                        + "<h4><br>" + pnews.getName() + "</h4><br>" + pnews.getText() +
                        "<br>Место: " + pnews.getPlace() + "<br>Время: " + pnews.getT() +
                        fragment.getString(R.string.web_end));
                Navigation.findNavController(finalView).navigate(R.id.action_navigation_home_to_navigation_html, bundle);
            }
        });

        return view;
    }

    // напом. по позиции
    private News getNews(int position) {
        return ((News) getItem(position));
    }




}
