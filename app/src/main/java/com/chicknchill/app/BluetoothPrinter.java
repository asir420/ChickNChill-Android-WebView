package com.chicknchill.app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BluetoothPrinter {
    private static final String TAG = "BluetoothPrinter";
    private BluetoothAdapter btAdapter;
    private BluetoothSocket btSocket;
    private OutputStream outStream;

    // Standard SPP UUID
    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public BluetoothPrinter() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    // Returns paired device if found by name; else null
    public BluetoothDevice findPairedDeviceByName(String name) {
        if (btAdapter == null) return null;
        Set<BluetoothDevice> paired = btAdapter.getBondedDevices();
        for (BluetoothDevice d : paired) {
            if (d.getName().equalsIgnoreCase(name)) return d;
        }
        return null;
    }

    // Connect to device (call on background thread)
    public boolean connect(BluetoothDevice device) {
        try {
            btSocket = device.createRfcommSocketToServiceRecord(SPP_UUID);
            btAdapter.cancelDiscovery();
            btSocket.connect();
            outStream = btSocket.getOutputStream();
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Connect failed", e);
            close();
            return false;
        }
    }

    // Send plain text to printer
    public boolean printText(String text) {
        if (outStream == null) return false;
        try {
            outStream.write(text.getBytes("UTF-8"));
            outStream.flush();
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Write failed", e);
            return false;
        }
    }

    public void close() {
        try {
            if (outStream != null) outStream.close();
            if (btSocket != null) btSocket.close();
        } catch (IOException ignored) {}
    }
}
