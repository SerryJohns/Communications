package com.example.communications.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BTDiscoveryReceiver extends BroadcastReceiver {
    private static final String TAG = BTDiscoveryReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(BluetoothDevice.ACTION_FOUND)) {
            // Discovery has found a device. Get the BluetoothDevice
            // object and its info from the Intent
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            String deviceName = device.getName();
            String deviceHardwareAddress = device.getAddress(); // MAC Address
            Log.d(TAG, String.format("onReceive: Discovered Devices: %s, %s", deviceName, deviceHardwareAddress));
        }
    }
}
