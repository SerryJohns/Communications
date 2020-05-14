package com.example.communications.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.example.communications.BuildConfig;

import java.io.IOException;

import static com.example.communications.bluetooth.HeadSets.APP_UUID;

public class AcceptThread extends Thread {
    private static final String TAG = AcceptThread.class.getSimpleName();
    // Simplified thread for the server component that accepts incoming connections
    private final String NAME = BuildConfig.APPLICATION_ID;
    private final BluetoothServerSocket mmServerSocket;
    private final BluetoothAdapter bluetoothAdapter;

    public AcceptThread(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
        // Use a temporary object that is later assigned to mmServerSocket because mmServerSocket is final
        BluetoothServerSocket temp = null;
        try {
            temp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, APP_UUID);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "AcceptThread: Socket's listen method call failed: ", e);
        }
        this.mmServerSocket = temp;
    }

    @Override
    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until an exception occurs or a socket is returned
        while (true) {
            try {
                socket = mmServerSocket.accept();
                Log.d(TAG, "run: Incoming connection accepted");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "run: Socket's accept method failed", e);
            }
            if (socket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.
                try {
                    manageConnectedSocket(socket);
                    mmServerSocket.close();
                    Log.d(TAG, "run: Incoming connection Release socket");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void manageConnectedSocket(BluetoothSocket socket) {
        Log.d(TAG, "manageConnectedSocket: Manage connected socket: " + socket.toString());
    }

    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "cancel: Could not close the connect socket", e);
        }
    }

}
