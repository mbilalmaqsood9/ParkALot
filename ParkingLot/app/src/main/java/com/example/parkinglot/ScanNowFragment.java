package com.example.parkinglot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanNowFragment extends Fragment implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan_now, container, false);
        scannerView = new ZXingScannerView(getActivity().getApplicationContext());


        return view;
    }

    @Override
    public void handleResult(Result rawResult) {

    }
}