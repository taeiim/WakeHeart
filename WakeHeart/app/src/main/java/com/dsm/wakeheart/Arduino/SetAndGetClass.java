package com.dsm.wakeheart.Arduino;

import java.io.InputStream;

public class SetAndGetClass {

    private final static SetAndGetClass setAndGetClass = new SetAndGetClass();

    private SetAndGetClass(){}

    private BluetoothControl bluetoothControl;

    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public static SetAndGetClass getInstance(){
        return setAndGetClass;
    }

    public BluetoothControl getBlutoothControl() {
        return bluetoothControl;
    }

    public void setBlutoothControl(BluetoothControl blutoothControl) {
        this.bluetoothControl = bluetoothControl;
        inputStream = blutoothControl.getInputStream();
    }
}
