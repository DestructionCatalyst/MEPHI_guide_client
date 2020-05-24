package com.example.mephiguide.ui.html;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mephiguide.MyLog;
import com.example.mephiguide.R;

public class HtmlFragment extends Fragment {

    private WebView webView;
    private TextView textView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_html, container, false);
        MyLog.i("Navigated to HTML content fragment");

        webView = root.findViewById(R.id.html_webView);
        webView.loadData(getArguments().getString("data"), "text/html; charset=utf-8", "utf-8");

        textView = root.findViewById(R.id.html_textView);
        String qr = getArguments().getString("qr");
        if ((qr != null)&&(!qr.equals(""))){
            textView.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            textView.setText(qr);
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
