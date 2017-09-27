package com.dsm.wakeheart.Graph;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//10분마다 시간, 일, 월 변경 체크
// 서버 넘길 땐 int로 보내야 됨
public class myTimer {
    ArrayList<Integer> heartRates, hourInterval = new ArrayList<>(), dayInterval = new ArrayList<>(), monthInterval = new ArrayList<>();

    Temp temp = new Temp(null, null, null, (int) 0);


    public void main(String[] args) {
        //혹여나 어레이리스트 접근 불가 시 핸들러 추가 필요
        TimerTask task = new TimerTask() {
            public void run() {
//                while() {
                try {
                    checkHourChanged();
                } catch (Exception e) {
                    e.printStackTrace();
//                    }
                }
            }
        };
        Timer mTimer = new Timer();
        mTimer.schedule(task, 600000, 600000); //10분 후 실행하고 매 10분마다 실행, 체크를 10분마다 함
    }

    public void checkHourChanged() {
        int sum = 0;
        long current = System.currentTimeMillis();
        Date date = new Date(current); //계산의 중점이 되는 시간을 뜻함
        String hourNow = new SimpleDateFormat("HH").format(date);
        int IHourNow = Integer.parseInt(hourNow);
        String dayNow = new SimpleDateFormat("dd").format(date);
        int IDayNow = Integer.parseInt(dayNow);
        String monthNow = new SimpleDateFormat("MM").format(date);
        int IMonthNow = Integer.parseInt(monthNow);
        //시간 단위
        //10분마다 temp에 업데이트하여 1시간이 지나면 넣음
        if (temp.MinTemp == 0) {
            temp.MinTemp = IHourNow;
        } else {
            temp.MinTemp = (temp.MinTemp + IHourNow) / 2; //temp 업데이트
        }

        if (temp.hourTemp == null) {
            temp.hourTemp = hourNow;
        } // 첫 실행
        else {
            if (hourNow != temp.hourTemp) { //시간이 다름
                hourInterval.add(temp.MinTemp);
                temp.dayTemp = dayNow;
                //Integer.parseInt(temp.dayTemp); // 서버 보낼땐 int로
            }
        }

        //일 단위
        if (temp.dayTemp == null) {
            temp.dayTemp = dayNow;
        } else {//일 단위 체크
            if (dayNow != temp.dayTemp) { //날이 바뀜
                for (int h : hourInterval) { //일 단위 처리
                    sum += h;
                }
                dayInterval.add(sum / hourInterval.size());
                hourInterval.clear();
                temp.dayTemp = dayNow;
            }
        }

        //월 단위
        if (temp.monthTemp == null) { // 첫 실행
            temp.monthTemp = monthNow;
        } else {//일 단위 체크
            if (monthNow != temp.monthTemp) { //월이 바뀜
                temp.monthTemp = monthNow;
                for (int d : dayInterval) { //일 단위 처리
                    sum += d;
                }
                monthInterval.add(sum / dayInterval.size());
                dayInterval.clear();
                temp.monthTemp = monthNow;
            }
        }//해야됨

    }
}//서버 연결
//서비스 구동


