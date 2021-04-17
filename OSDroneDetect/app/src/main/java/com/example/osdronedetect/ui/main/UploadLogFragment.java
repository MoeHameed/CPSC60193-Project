package com.example.osdronedetect.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.osdronedetect.R;

public class UploadLogFragment extends Fragment {

    public UploadLogFragment() {
    }

    public static UploadLogFragment newInstance() {
        UploadLogFragment fragment = new UploadLogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upload_log, container, false);
    }
}