package com.example.mephiguide.ui.qr;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.example.mephiguide.R;
import com.example.mephiguide.data_types.Qr;
import com.google.zxing.Result;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class QrFragment extends Fragment {

    private QrViewModel mViewModel;

    private static final int RC_PERMISSION = 10;

    private boolean mPermissionGranted;

    private String contents;
    private boolean showingQr;

    private Button button;
    private CodeScannerView scannerView;

    private CodeScanner mCodeScanner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(QrViewModel.class);
        View root = inflater.inflate(R.layout.fragment_qr, container, false);

        mViewModel.getQr().observe(getViewLifecycleOwner(), new Observer<ArrayList>() {
            @Override
            public void onChanged(@Nullable ArrayList qr) {

                showQR(qr);
            }
        });

        button = root.findViewById(R.id.qr_scanner_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR(v);
            }
        });

        scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(getActivity(), scannerView);
        setOnDecode();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = false;
                requestPermissions(new String[] {Manifest.permission.CAMERA}, RC_PERMISSION);
            } else {
                mPermissionGranted = true;
            }
        } else {
            mPermissionGranted = true;
        }
        return root;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == RC_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = true;
            } else {
                mPermissionGranted = false;
            }
        }
    }


    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void scanQR(View v) {
        if (mPermissionGranted) {
            button.setVisibility(View.GONE);
            scannerView.setVisibility(View.VISIBLE);
            mCodeScanner.startPreview();
        }
        else{
            Toast.makeText(getActivity(), getString(R.string.scanner_error), Toast.LENGTH_LONG).show();
        }
    }

    private void setOnDecode(){
        mCodeScanner.setDecodeCallback(new DecodeCallback() {

            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        showingQr = false;
                        contents = result.getText();
                        String prefix = contents.substring(0, contents.indexOf(' '));
                        try {
                            prefix = URLEncoder.encode(prefix, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setMessage("Не удалось считать QR-код. Пожалуйста, попробуйте ещё раз.")
                                    .setTitle("QR-код");
                            AlertDialog dialog1 = builder1.create();
                            dialog1.show();
                            e.printStackTrace();
                        }
                        mViewModel.updateQr(prefix);
                        Log.d("QR", "Отсканировали QR-код");

                    }
                });
            }

        });
        mCodeScanner.setErrorCallback(new ErrorCallback(){
            @Override
            public void onError(@NonNull Exception error) {
                getActivity().runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), getString(R.string.scanner_error), Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
        });
    }

    private void showQR(ArrayList qr){
        if (!showingQr) {
            showingQr = true;

            if (qr != null) {
                Qr info = (Qr) qr.get(0);
                Bundle bundle = new Bundle();
                bundle.putString("data", getString(R.string.web_start) + "<h4>" + info.getName()
                        + "</h4><br>" + info.getText() + getString(R.string.web_end));
                contents = "";
                Navigation.findNavController(scannerView).navigate(R.id.action_navigation_qr_to_navigation_html, bundle);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("data", getString(R.string.web_start) + "<h4>" +
                        getString(R.string.wrong_qr) +
                        "</h4><br>" + contents + getString(R.string.web_end));
                contents = "";
                Navigation.findNavController(scannerView).navigate(R.id.action_navigation_qr_to_navigation_html, bundle);
            }
            Log.d("QR", "Открываем QR-код");
        }
    }

}
