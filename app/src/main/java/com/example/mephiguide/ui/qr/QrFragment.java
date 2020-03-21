package com.example.mephiguide.ui.qr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.example.mephiguide.R;
import com.google.zxing.Result;


public class QrFragment extends Fragment {

    private QrViewModel mViewModel;
    private static final int RC_PERMISSION = 10;
    private CodeScanner mCodeScanner;
    private boolean mPermissionGranted;

    private Button button;
    CodeScannerView scannerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_qr, container, false);

        button = root.findViewById(R.id.qr_scanner_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR(v);
            }
        });

        scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(getActivity(), scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {

            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override

                    public void run() {
                        Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_SHORT).show();
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
                        });
            }
        });
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
        /*
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        */
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(QrViewModel.class);
        // TODO: Use the ViewModel
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

}
