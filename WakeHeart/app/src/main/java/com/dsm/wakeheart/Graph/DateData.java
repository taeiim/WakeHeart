package com.dsm.wakeheart.Graph;

/**
 * Created by admin on 2017-09-27.
 */

/** 직접 지정에서 받는 날짜 */
public class DateData {
    int year;
    int month;
    int day;
    public DateData(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    };
    public void changeData(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
