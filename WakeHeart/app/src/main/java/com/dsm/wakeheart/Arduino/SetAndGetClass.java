package com.dsm.wakeheart.Arduino;

public class SetAndGetClass {

    private final static SetAndGetClass setAndGetClass = new SetAndGetClass();

    private SetAndGetClass(){}

    private BluetoothControl bluetoothControl;

    public static SetAndGetClass getInstance(){
        return setAndGetClass;
    }

    public BluetoothControl getBlutoothControl() {
        return bluetoothControl;
    }

    public void setBlutoothControl(BluetoothControl blutoothControl) {
        this.bluetoothControl = bluetoothControl;
    }
}
