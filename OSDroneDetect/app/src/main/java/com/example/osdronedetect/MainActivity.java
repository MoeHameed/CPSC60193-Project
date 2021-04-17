package com.example.osdronedetect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.osdronedetect.ui.main.SectionsPagerAdapter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private WifiManager wifiManager = null;
    public ArrayList<WiFiScanData> wiFiScanData = null;
    SectionsPagerAdapter sectionsPagerAdapter = null;
    Timer timer = new Timer();
    ArrayList<String> knownDroneSSIDs = null;   // Strings are ssid regexs
    ArrayList<String> knownDroneBSSIDs = null;   // Strings are bssids regexs
    LocationManager locationManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onStart() {
        super.onStart();

        wiFiScanData = new ArrayList<WiFiScanData>();

        knownDroneSSIDs = new ArrayList<String>();
        knownDroneSSIDs.add("^TELLO.*");

        knownDroneBSSIDs = new ArrayList<String>();
        knownDroneBSSIDs.add("^60:60:1f:62:1d:1b.*");

        SetupWiFiScanning();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locationListener);

        startScanning(null);
    }

    private final LocationListener locationListener = location -> sectionsPagerAdapter.getMapViewFragment().setLocationText("LAT: " + location.getLatitude() + ", LON: " + location.getLongitude() + ", ALT: " + location.getAltitude());

    // From button
    // Scanning every 30 seconds
    public void startScanning(View view) {
        timer.cancel();
        timer.purge();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Timestamp time = new Timestamp(System.currentTimeMillis());
                if (location == null && sectionsPagerAdapter.getMapViewFragment() != null) {
                    System.out.println("LAST LOCATION WAS NULL AT " + time.toString());
                }
                else if (location != null && sectionsPagerAdapter.getMapViewFragment() != null){
                    System.out.println("LAST LOCATION WAS NOT NULL AT " + time.toString());
                }

                if (!wifiManager.startScan()) {
                    scanFailure();
                }
            }
        }, 0, 30000); // 30 seconds
    }

    // From button
    // Stop scanning timer
    public void stopScanning(View view) {
        timer.cancel();
        timer.purge();
    }

    public void SetupWiFiScanning()
    {
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        IntentFilter intentFilter = new IntentFilter();

        BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getBooleanExtra( WifiManager.EXTRA_RESULTS_UPDATED, false)) {
                    scanSuccess();
                } else {
                    scanFailure();
                }
            }
        };

        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        this.registerReceiver(wifiScanReceiver, intentFilter);
    }

    private void scanSuccess() {
        List<ScanResult> results = wifiManager.getScanResults();

        System.out.println("GOT " + results.size() + " WIFI RESULTS!!!");

        int numDrones = processScanResults(results);

        sectionsPagerAdapter.getScanSettingsFragment().setWiFiScanData(wiFiScanData, numDrones, new Timestamp(System.currentTimeMillis()));

    }

    private int processScanResults(List<ScanResult> results)
    {
        wiFiScanData.clear();

        ArrayList<WiFiScanData> detectedDrones = new ArrayList<WiFiScanData>();

        // add all results to list and check if it's a drone
        for (ScanResult result : results) {
            String ssid = result.SSID.isEmpty() ? "*HIDDEN*" : result.SSID;

            if (isKnownSSID(ssid) || isKnownBSSID(result.BSSID))
                detectedDrones.add(new WiFiScanData(ssid, result.BSSID, result.level, result.frequency, result.timestamp, true));
            else
                wiFiScanData.add(new WiFiScanData(ssid, result.BSSID, result.level, result.frequency, result.timestamp, false));
        }

        // Sort results by range, and then by drone first
        detectedDrones.sort((o1, o2) -> (int) (o1.getRangeM() - o2.getRangeM()));
        wiFiScanData.sort((o1, o2) -> (int) (o1.getRangeM() - o2.getRangeM()));

        wiFiScanData.addAll(0, detectedDrones);

        // TODO: prune results: duplicate SSIDS/BSSIDS

        return detectedDrones.size();
    }

    private void scanFailure() {
        //List<ScanResult> results = wifiManager.getScanResults();    // OLD RESULTS
    }

    private boolean isKnownSSID(String ssid)
    {
        for(String regexp : knownDroneSSIDs)
        {
            Pattern p = Pattern.compile(regexp);
            Matcher m = p.matcher(ssid);
            if(m.matches())
            {
                return true;
            }

        }
        return false;
    }

    private boolean isKnownBSSID(String bssid)
    {
        for(String regexp : knownDroneBSSIDs)
        {
            Pattern p = Pattern.compile(regexp);
            Matcher m = p.matcher(bssid);
            if(m.matches())
            {
                return true;
            }
        }
        return false;
    }

}