package com.example.osdronedetect.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.osdronedetect.R;
import com.example.osdronedetect.WiFiScanData;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ScanSettingsFragment extends Fragment {

    RecyclerView scanDataView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<WiFiScanData> wiFiScanData = null;
    TextView lastScanned;
    Button startScan;
    Button stopScan;
    TextView numDronesText;

    public ScanSettingsFragment() {
    }

    public static ScanSettingsFragment newInstance() {
        return new ScanSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scan_settings, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        startScan = getView().findViewById(R.id.startScanningButton);
        stopScan = getView().findViewById(R.id.stopScanningButton);
        //stopScan.setEnabled(false);

        scanDataView = getView().findViewById(R.id.recyclerView);
        scanDataView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), wiFiScanData);
        scanDataView.setAdapter(recyclerViewAdapter);

        lastScanned = getView().findViewById(R.id.lastScanned_textView);
        numDronesText = getView().findViewById(R.id.numDronesTextView);

    }

    public void setWiFiScanData(ArrayList<WiFiScanData> wiFiScanData, int numDrones, Timestamp timestamp)
    {
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), wiFiScanData);
        scanDataView.setAdapter(recyclerViewAdapter);

        // TODO: Add location to this or map view?

        lastScanned.setText("Last Scanned: " + timestamp.toString());
        numDronesText.setText(String.valueOf(numDrones) + " of " + String.valueOf(wiFiScanData.size()) + " Devices are Drones");

    }

    public void toggleButtonEnable()
    {
        startScan.setEnabled(!startScan.isEnabled());
        stopScan.setEnabled(!stopScan.isEnabled());
    }



}