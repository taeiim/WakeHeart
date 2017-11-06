package com.dsm.wakeheart.Graph;


public class Axis {

    private float index;

    public float getIndex() {
        return index;
    }

    public float getY() {
        return y;
    }

    private float y;

    public Axis(float index, float y){
        this.index = index;
        this.y = y;
    }

}
