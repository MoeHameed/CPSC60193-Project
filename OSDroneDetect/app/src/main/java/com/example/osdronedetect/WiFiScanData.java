package com.example.osdronedetect;

public class WiFiScanData {
    private String SSID;
    private String BSSID;
    private int RSSI;
    private int FREQUENCY;
    private long TIMESTAMP;
    private boolean isDrone = false;
    private double rangeM = 0;

    WiFiScanData(String ssid, String bssid, int rssi, int freq, long timestamp, boolean isdrone){
        this.SSID = ssid;
        this.BSSID = bssid;
        this.RSSI = rssi;
        this.FREQUENCY = freq;
        this.TIMESTAMP = timestamp;
        this.isDrone = isdrone;
        this.rangeM = calcRange();
    }

    private double calcRange()
    {
        double exp = (27.55 - (20 * Math.log10(this.FREQUENCY)) + Math.abs(this.RSSI)) / 20.0;
        return Math.round(Math.pow(10.0, exp) * 100.0) / 100.0;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public int getRSSI() {
        return RSSI;
    }

    public void setRSSI(int RSSI) {
        this.RSSI = RSSI;
    }

    public int getFREQUENCY() {
        return FREQUENCY;
    }

    public void setFREQUENCY(int FREQUENCY) {
        this.FREQUENCY = FREQUENCY;
    }

    public long getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(long TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

    public boolean equals(WiFiScanData o){
        return this.SSID == o.getSSID() && this.BSSID == o.getBSSID() && this.RSSI == o.getRSSI() && this.FREQUENCY == o.getFREQUENCY() && this.TIMESTAMP == o.getTIMESTAMP();
    }

    public boolean isDrone() {
        return isDrone;
    }

    public void setDrone(boolean drone) {
        isDrone = drone;
    }

    public double getRangeM() {
        return rangeM;
    }

    public void setRangeM(double rangeM) {
        this.rangeM = rangeM;
    }
}
