package com.example.osdronedetect.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osdronedetect.R;
import com.example.osdronedetect.WiFiScanData;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<WiFiScanData> wiFiScanData = null;

    RecyclerViewAdapter(Context context, ArrayList<WiFiScanData> wiFiScanData){
        this.layoutInflater = LayoutInflater.from(context);
        this.wiFiScanData = wiFiScanData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.scan_data_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (wiFiScanData == null) return;
        WiFiScanData data = wiFiScanData.get(position);
        holder.updateView(data);
    }

    @Override
    public int getItemCount() {
        if (wiFiScanData == null) return 0;
        return wiFiScanData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView SSID;
        TextView BSSID;
        TextView RSSI;
        TextView FREQ;
        TextView TIME;
        TextView RANGE;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SSID = itemView.findViewById(R.id.ssid_textView);
            BSSID = itemView.findViewById(R.id.bssid_textView);
            RSSI = itemView.findViewById(R.id.rssi_textView);
            FREQ = itemView.findViewById(R.id.frequency_textView);
            TIME = itemView.findViewById(R.id.timestamp_textView);
            RANGE = itemView.findViewById(R.id.range_textView);
            cardView = itemView.findViewById(R.id.cardView);
        }

        public void updateView(WiFiScanData data)
        {
            SSID.setText(data.getSSID());
            BSSID.setText(data.getBSSID());
            RSSI.setText(String.valueOf(data.getRSSI()));
            FREQ.setText(String.valueOf(data.getFREQUENCY()));
            TIME.setText(String.valueOf(data.getTIMESTAMP()));
            RANGE.setText(String.valueOf(data.getRangeM()));

            if(data.isDrone()){
                System.out.println(data.getSSID() + " is a drone!!");
                cardView.setCardBackgroundColor(Color.parseColor("#6bf28f"));
            }
            else
            {
                cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            }

        }
    }
}
