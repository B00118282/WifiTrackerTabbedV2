package com.example.wifi_tracker_tabbed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class Tab2 extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab2, container, false);
    }

public class MACAddress extends AppCompatActivity {

        private TextView textView;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_tab2);

            textView = findViewById(R.id.textView);
        }

        public void buttonClick(View view) {

            try {
                List<NetworkInterface> networkInterfaceList = Collections.list(NetworkInterface.getNetworkInterfaces());

                String stringMac = "";

                for (NetworkInterface networkInterface : networkInterfaceList) {
                    if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                        for (int i = 0; i < networkInterface.getHardwareAddress().length; i++) {
                            String stringMacByte = Integer.toHexString(networkInterface.getHardwareAddress()[i] & 0xFF);

                            if (stringMacByte.length() == 1) {
                                stringMacByte = "0" + stringMacByte;
                            }

                            stringMac = stringMac + stringMacByte.toUpperCase() + ":";
                        }
                        break;
                    }
                }

                textView.setText(stringMac.substring(0, stringMac.length() - 1));


            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }
}