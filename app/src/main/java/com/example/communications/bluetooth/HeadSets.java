package com.example.communications.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.communications.BuildConfig;

import java.util.Set;
import java.util.UUID;

public class HeadSets {
    public static final int REQUEST_ENABLE_BT = 1001;
    public static final UUID APP_UUID = UUID.fromString(BuildConfig.APPLICATION_ID);
    private static final String TAG = HeadSets.class.getSimpleName();
    private final Context mContext;
    private BluetoothHeadset bluetoothHeadset;

    // Get the default Adapter
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private BluetoothProfile.ServiceListener profileListener = new BluetoothProfile.ServiceListener() {
        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            if (profile == BluetoothProfile.HEADSET) {
                bluetoothHeadset = (BluetoothHeadset) proxy;
                Log.d(TAG, "onServiceConnected: Headsets connected");
            }
        }

        @Override
        public void onServiceDisconnected(int profile) {
            if (profile == BluetoothProfile.HEADSET) {
                bluetoothHeadset = null;
                Log.d(TAG, "onServiceDisconnected: Headsets disconnected");
            }
        }
    };

    public HeadSets(Context activityContext) {
        this.mContext = activityContext;
    }

    public void initConnection() {
        // Check if bluetooth is enabled
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            ((Activity) mContext).startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        // Establish a connection to the proxy
        bluetoothAdapter.getProfileProxy(mContext, profileListener, BluetoothProfile.HEADSET);

        // call functions on the bluetooth headset
        Log.d(TAG, "initConnection: Headsets initialized");
    }

    public void closeConnection() {
        // Close proxy connection after use
        bluetoothAdapter.closeProfileProxy(BluetoothProfile.HEADSET, bluetoothHeadset);
        Log.d(TAG, "closeConnection: Headsets connection closed");
    }

    public void getPairedDevices() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC Address
                Log.d(TAG, String.format("getPairedDevices: Name: %s, MAC Address: %s", deviceName, deviceHardwareAddress));
            }
        }
    }

    public void makeDeviceDiscoverable() {
        // Sets the device to be discoverable for 5 minutes (300 seconds)
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        mContext.startActivity(discoverableIntent);
    }

    public void discoverDevices() {
        // Start discovery
        bluetoothAdapter.startDiscovery();
    }
}
