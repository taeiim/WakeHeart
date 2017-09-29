package com.dsm.wakeheart.Graph;

public class DateData {
    int month;
    int day;
    public DateData(int month, int day){
        this.month = month;
        this.day = day;
    };
    public void changeData(int month, int day) {
        this.month = month;
        this.day = day;
    }
    public void resetData() {
        this.month = 0;
        this.day = 0;
    }
}
