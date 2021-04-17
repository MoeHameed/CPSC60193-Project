package com.example.osdronedetect.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osdronedetect.R;

public class MapViewFragment extends Fragment {

    TextView locationText;

    public MapViewFragment() {
    }

    public static MapViewFragment newInstance() {
        MapViewFragment fragment = new MapViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_view, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        locationText = getView().findViewById(R.id.locationtextview);
    }

    public void setLocationText(String text)
    {
        locationText.setText(text);
    }
}