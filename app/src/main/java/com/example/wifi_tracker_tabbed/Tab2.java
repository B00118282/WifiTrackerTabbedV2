package com.example.wifi_tracker_tabbed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Tab2 extends AppCompatActivity {
    TextView txtWifiInfo;
    Button btninfo;
    TextView txtIPAddress, txtMacAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab2);
        txtWifiInfo = (TextView) findViewById(R.id.idTxt);
        btninfo = (Button) findViewById(R.id.idBtn);
    }

    public void getWifiInformation(View view) {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String macAddress = wifiInfo.getMacAddress();
        String bssid =wifiInfo.getBSSID();
        int rssi = wifiInfo.getRssi();
        String ssid =wifiInfo.getSSID();
        int networdId = wifiInfo.getNetworkId();
        String ipAddress = Formatter.formatIpAddress(ip);
        String info = "ipAddress:" + ipAddress +
                "\n" + "MacAddress:"+macAddress+
                "\n"+ "SSID"+ ssid+
                "\n"+"NetworkID"+networdId;
        txtWifiInfo.setText(info);

    }
}