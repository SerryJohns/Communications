package com.example.communications.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BluetoothStatusReceiver extends BroadcastReceiver {
    private static final String TAG = BluetoothStatusReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        // broadcast detected
        if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            Bundle data = intent.getExtras();
            int state = data.getInt(BluetoothAdapter.EXTRA_STATE);
            switch (state) {
                case BluetoothAdapter.STATE_TURNING_ON:
                    Log.d(TAG, "onReceive: Bluetooth State: Turning On");
                    break;
                case BluetoothAdapter.STATE_ON:
                    Log.d(TAG, "onReceive: Bluetooth State: On");
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    Log.d(TAG, "onReceive: Bluetooth State: Turning Off");
                    break;
                case BluetoothAdapter.STATE_OFF:
                    Log.d(TAG, "onReceive: Bluetooth State: Off");
                    break;
                case BluetoothAdapter.ERROR:
                    Log.d(TAG, "onReceive: Bluetooth State: Error");
                    break;
            }
            if (state == BluetoothAdapter.STATE_TURNING_ON) {
                Log.d(TAG, "onReceive: Bluetooth State: Turning On");
            }
        }
    }
}
