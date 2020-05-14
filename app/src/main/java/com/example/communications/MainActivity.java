package com.example.communications;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.communications.bluetooth.BTDiscoveryReceiver;
import com.example.communications.bluetooth.BluetoothStatusReceiver;
import com.example.communications.bluetooth.HeadSets;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BluetoothStatusReceiver bluetoothStatusReceiver;
    private HeadSets bluetoothHeadsets;
    private BTDiscoveryReceiver btDiscoveryReciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // listen to bluetooth status changes
        bluetoothStatusReceiver = new BluetoothStatusReceiver();
        registerReceiver(bluetoothStatusReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        btDiscoveryReciever = new BTDiscoveryReceiver();
        registerReceiver(btDiscoveryReciever, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        doSomeBluetoothOperations();
    }

    private void doSomeBluetoothOperations() {
        bluetoothHeadsets = new HeadSets(this);
        bluetoothHeadsets.initConnection();
        bluetoothHeadsets.getPairedDevices();
        bluetoothHeadsets.discoverDevices();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == HeadSets.REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: Bluetooth Permissions Granted");
        } else if (requestCode == HeadSets.REQUEST_ENABLE_BT && resultCode == RESULT_CANCELED) {
            Log.d(TAG, "onActivityResult: Bluetooth Permissions Denied");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothStatusReceiver);
        unregisterReceiver(btDiscoveryReciever);
        bluetoothHeadsets.closeConnection();
    }
}
