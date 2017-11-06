package com.dsm.wakeheart.Graph;


        import android.app.Service;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Handler;
        import android.os.IBinder;
        import android.util.Log;
        import android.widget.Toast;

        import com.google.gson.JsonPrimitive;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

        import static com.dsm.wakeheart.Graph.Mode.DAY;
        import static com.dsm.wakeheart.Graph.Mode.HOUR;
        import static com.dsm.wakeheart.Graph.Mode.MONTH;
        import static com.dsm.wakeheart.Graph.Mode.WEEK;
/**
 * Created by admin on 2017-10-06.
 */

/** my Timer -> DataProcessingService
 * 백그라운드 데이터 평균 처리 서비스
 * 서버에는 일단위 데이터만 넘긴다
 * 나머지 데이터는 내부 데이터로, 기본 제공 단위 데이터 표시에 사용된다.
 */
public class DataProcessingService extends Service {

    ArrayList<Integer> heartRates = new ArrayList<>(); //저장된 심박수값
    ArrayList<Integer> hourInterval = new ArrayList<>(); // 1시간 단위 심박수 통계
    ArrayList<Integer> dayInterval = new ArrayList<>(); // 하루 단위 심박수 통계
    ArrayList<Integer> dayIntervalTemp = new ArrayList<>(); // 주단위 추가된 부분
    ArrayList<Integer> weekInterval = new ArrayList<>(); // 주단위 추가된 부분
    ArrayList<Integer> monthInterval = new ArrayList<>(); // 월 단위 심박수 통계
    private final Byte BDAY = 0;
    private final Byte BWEEK = 1;
    private final Byte BMONTH = 2;

    //토스트 띄우기용
    Handler mHandler;

    String tempToken = "JWT eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZGVudGl0eSI6InJyIiwiaWF0IjoxNTA5NDU3NzcyLCJuYmYiOjE1MDk0NTc3NzIsImV4cCI6MTU0MDk5Mzc3Mn0.jjDSfEFdsW58YrPbxrMS-udPZ-S2fwdtnIMX2b7ThLo";

    /**
     * 데이터 처리에 필요한 변수들
     */
    long current = System.currentTimeMillis();
    Date date = new Date(current); //계산의 중점이 되는 시간
    String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
    int IHourNow = Integer.parseInt(new SimpleDateFormat("HH").format(date));
    int IDayNow = Integer.parseInt(new SimpleDateFormat("dd").format(date));
    int IMonthNow = Integer.parseInt(new SimpleDateFormat("MM").format(date));
    int avg;
    int sum = 0;
    int cnt = 0;
    Temp temp = new Temp((int) 0, (int) 0, (int) 0, (int) 0); // 각각 hour, day, month, 10분 단위 평균 임시저장용

    /**
     * 생성자
     */
    public DataProcessingService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test", "백그라운드 처리부 가동");
        //처리된 데이터 존재 여부 false 초기화
        initDataExist();
    }

    /**
     * 서비스의 콜백, 저장된 심박수 불러와 처리
     *
     * @param intent  수신부에서 받은 인텐트
     * @param flags
     * @param startId
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //데이터 받기, 처리, pref 추가, 존재여부 설정 스레드
        Log.d("test", "DataProcessingService의 onStartCommand");
        Thread checkSetPrefThread = new CheckSetPrefThread();
        checkSetPrefThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    private SharedPreferences getHeartRatePref() {
        return getSharedPreferences("heartPref", MODE_PRIVATE);
    }


    /**
     * 처리된 ArrayList 들을 각각
     * hourPref, dayPref, weekPref, monthPref 에 저장
     */
    private void setPref() {

        //시간 단위 추가
        SharedPreferences hourPref = getSharedPreferences("hourPref", MODE_PRIVATE);
        SharedPreferences.Editor hourPrefEdit = hourPref.edit();

        hourPrefEdit.putInt("hour_Size", hourInterval.size()); //사이즈

        if(hourInterval.size() != 0) {
            for (int i = 0 ; i < hourInterval.size() ; i++) {
                hourPrefEdit.remove("Hour_" + i);
                hourPrefEdit.putInt("Hour_" + i, hourInterval.get(i)); //값
            }
            hourPrefEdit.commit(); //커밋
        }



        //일 단위 추가
        SharedPreferences dayPref = getSharedPreferences("dayPref", MODE_PRIVATE);
        SharedPreferences.Editor dayPrefEdit = dayPref.edit();

        hourPrefEdit.putInt("Day_Size", dayInterval.size()); //사이즈

        if(dayInterval.size() != 0) {
            for (int i = 0 ; i < dayInterval.size() ; i++) {
                dayPrefEdit.remove("Day_" + i);
                dayPrefEdit.putInt("Day_" + i, dayInterval.get(i)); //값
            }
            hourPrefEdit.commit(); //커밋
        }

        //주 단위 추가
        SharedPreferences weekPref = getSharedPreferences("weekPref", MODE_PRIVATE);
        SharedPreferences.Editor weekPrefEdit = weekPref.edit();

        weekPrefEdit.putInt("Week_Size", weekInterval.size()); //사이즈

        if(weekInterval.size() != 0) {
            for (int i = 0 ; i < weekInterval.size() ; i++) {
                weekPrefEdit.remove("Week_" + i);
                weekPrefEdit.putInt("Week_" + i, weekInterval.get(i)); //값
            }
            weekPrefEdit.commit(); //커밋
        }

        //월 단위 추가
        SharedPreferences monthPref = getSharedPreferences("monthPref", MODE_PRIVATE);
        SharedPreferences.Editor monthPrefEdit = monthPref.edit();

        monthPrefEdit.putInt("Month_Size", monthInterval.size()); //사이즈

        if(monthInterval.size() != 0) {
            for (int i = 0 ; i < monthInterval.size() ; i++) {
                monthPrefEdit.remove("Month_" + i);
                monthPrefEdit.putInt("Month_" + i, monthInterval.get(i)); //값
            }
            monthPrefEdit.commit(); //커밋
        }
    }

    /**
     * 모드와 불린 값을 통해 데이터 유무 세팅
     * @param mode
     * @param bool
     */
    private void setDataExist(Mode mode, Boolean bool) {
        SharedPreferences isDataExist = getSharedPreferences("isDataPref", MODE_PRIVATE);
        SharedPreferences.Editor isDataEdit = isDataExist.edit();

        switch (mode) {
            case HOUR:
                isDataEdit.remove("isHourExist");
                isDataEdit.putBoolean("isHourExist", bool);
                break;
            case DAY:
                isDataEdit.remove("isDayExist");
                isDataEdit.putBoolean("isDayExist", bool);
                break;
            case WEEK:
                isDataEdit.remove("isWeekExist");
                isDataEdit.putBoolean("isWeekExist", bool);
                break;
            case MONTH:
                isDataEdit.remove("isMonthExist");
                isDataEdit.putBoolean("isMonthExist", bool);
                break;
        }
        isDataEdit.commit();
    }

    /**
     * 모든 데이터 존재 여부 false 초기화
     */
    public void initDataExist() {
        SharedPreferences isDataExist = getSharedPreferences("isDataPref", MODE_PRIVATE);
        SharedPreferences.Editor isDataEdit = isDataExist.edit();
        isDataEdit.remove("isHourExist");
        isDataEdit.putBoolean("isHourExist", false);
        isDataEdit.remove("isDayExist");
        isDataEdit.putBoolean("isDayExist", false);
        isDataEdit.remove("isWeekExist");
        isDataEdit.putBoolean("isWeekExist", false);
        isDataEdit.remove("isMonthExist");
        isDataEdit.putBoolean("isMonthExist", false);
        isDataEdit.commit();
        Log.d("test", "initDataExist() 완료");
    }

    class CheckSetPrefThread extends Thread {
        int temp;
        @Override
        public void run() {
            Log.d("test", "처리 스레드 가동");
            //받기
            SharedPreferences heartPref = getHeartRatePref();
            int size = heartPref.getInt("Rate_Size", -1);
            Log.d("test", "사이즈 :  " + size);
            //값이 존재할 때 실행
            if (size >= 1) {
                for (int i = 0; i < size; i++) { //값 추가
                    Log.d("test", "5번 실행");
                    temp = heartPref.getInt("Rate_" + i, 0);
                    Log.d("test", "temp 실행");
                    heartRates.add(temp);
                    Log.d("test", "heartRates 추가");
                }
            }
            //처리, 데이터 존재여부 설정
            checkAllDateChanged();
            // 단위별 pref 값 추가
            setPref();
        }
    }

    /**
     * 시간, 일, 일주일, 월 변경 체크 메소드
     */
    public void checkAllDateChanged() {
        checkHourChanged();
        checkDayChanged();
        checkMonthChanged();
    }

    /**
     * 시간 변경 체크 메소드
     */
    public void checkHourChanged() {
        sum = 0;
        final int ALLHOUR = 24;
        //10분마다 temp에 업데이트 (실제 반영 x)
        for (int i : heartRates) {
            sum += i;
            Log.d("test", "처리부 sum" + i);
        }
        if (cnt == 0) { //첫실행
            avg = sum / heartRates.size();
            Log.d("test", "hour Avg " + avg);
            temp.MinTemp = avg;
            cnt++;
        } else {
            avg = (sum / heartRates.size() + temp.MinTemp) / 2;
            Log.d("test", "hour Avg " + avg);
            temp.MinTemp = avg; //기존값과 추가된 값의 평균을 추가
        }

        heartRates.clear();

        //1시간이 지나면 실제 반영 시간 업데이트
        if (temp.iHourTemp == 0) { //첫 실행 시 현재 시간으로 저장
            temp.iHourTemp = IHourNow;
        }
        if (temp.iHourTemp != IHourNow) { //시간이 다름
            temp.iHourTemp = IHourNow; //시간업데이트
//            hourInterval.add(temp.MinTemp); //데이터 추가
            //(테스트) 데이터 추가 코드
            if(hourInterval.size() < ALLHOUR ) {
                hourInterval.add(temp.MinTemp);
            } else {
                for(int i = 0 ; i < hourInterval.size(); i++) {
                    hourInterval.add(i, hourInterval.get(i+1));
                }
                hourInterval.add(temp.MinTemp);
            }


            if(getSharedPreferences("isDataPref",MODE_PRIVATE)
                    .getBoolean("isHourExist", false) == false) { //false 설정 시
                setDataExist(HOUR, true);
            }
            temp.MinTemp = 0;
        }
    }

    /**
     * 일, 주 변경 체크 메소드, 시간 변경 시 서버 전송
     */
    public void checkDayChanged() {
        final int DAY7 = 7;
        final int WEEK4 = 4;

        if (temp.iDayTemp == 0) { //첫실행 시 현재 일 반영
            temp.iDayTemp = IDayNow;
        }
        sum = 0;
        if (temp.iDayTemp != IDayNow) { // 일 변경
            temp.iDayTemp = IDayNow; //시간 업데이트
            for (int h : hourInterval) {
                sum += h;
            }
            avg = sum / hourInterval.size();
            dayInterval.add(avg);
            //(테스트) 데이터 추가 코드
            if(dayInterval.size() < DAY7) {
                dayInterval.add(temp.MinTemp);
            } else {
                for(int i = 0 ; i < dayInterval.size(); i++) {
                    dayInterval.add(i, dayInterval.get(i+1));
                }
                dayInterval.add(temp.MinTemp);
            }



            if(getSharedPreferences("isDataPref",MODE_PRIVATE)
                    .getBoolean("isDayExist", false) == false) {
                setDataExist(DAY, true);
            }
//            hourInterval.clear();
            updateServerData(avg); //일 단위 서버 전송

            //주 단위 추가된 부분
            //7개가 쌓일 때마다 처리함, 어차피 30일 쌓이면 일 단위 초기화됨
            dayIntervalTemp.add(avg);
            if (dayIntervalTemp.size()%7 == 0) {
                sum = 0;
                for (int i = 0; i < dayIntervalTemp.size(); i++) {
                    sum += dayIntervalTemp.get(i);
                }
                //(테스트) 데이터 추가 코드
                if(monthInterval.size() < WEEK4) {
                    monthInterval.add(temp.MinTemp);
                } else {
                    for(int i = 0 ; i < monthInterval.size(); i++) {
                        monthInterval.add(i, monthInterval.get(i+1));
                    }
                    monthInterval.add(temp.MinTemp);
                }



                if(getSharedPreferences("isDataPref",MODE_PRIVATE)
                        .getBoolean("isWeekExist", false) == false) {
                    setDataExist(WEEK, true);
                }
                dayIntervalTemp.clear();
            }
        }
    }

    /**
     * 월 변경 체크 메소드, 시간 변경 시 서버 전송
     */
    public void checkMonthChanged() {
        final int MONTH6 = 6;
        if (temp.iMonthTemp == 0) {
            temp.iMonthTemp = IMonthNow;
        }
        sum = 0;

        if (temp.iMonthTemp != IMonthNow) { //월이 바뀜
            temp.iMonthTemp = IMonthNow;
            for (int d : dayInterval) { //일 단위 처리
                sum += d;
            }
            avg = sum / dayInterval.size();
            //(테스트) 데이터 추가 코드
            if(monthInterval.size() < MONTH6) {
                monthInterval.add(temp.MinTemp);
            } else {
                for(int i = 0 ; i < monthInterval.size(); i++) {
                    monthInterval.add(i, monthInterval.get(i+1));
                }
                monthInterval.add(temp.MinTemp);
            }

            if(getSharedPreferences("isDataPref",MODE_PRIVATE)
                    .getBoolean("isDayExist", false) == false) {
                setDataExist(MONTH, true);
            }
        }
    }


    /**
     * 서버 전송 메소드
     *
     * @param heartRate 심박수
     */
    public void updateServerData(int heartRate) {
        RetrofitGraphInterface retrofitService = APIClient.getRetrofit().create(RetrofitGraphInterface.class);
        Call<JsonPrimitive> call = retrofitService.updateData(tempToken, heartRate);
        call.enqueue(new Callback<JsonPrimitive>() {
            @Override
            public void onResponse(Call<JsonPrimitive> call, final Response<JsonPrimitive> response) {
                if(response.code() == 201) {
                    mHandler = new Handler();
                    Thread successThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "심박수 상태 업로드 성공", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    successThread.start();
                    Log.d("test", "심박수 상태 업로드 성공");
                } else {
                    mHandler = new Handler();
                    Thread ErrorThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "ErrorCode : " + response.code(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                    ErrorThread.start();
                    Log.d("test", "::호출 실패::");
                    Log.d("test", "headers : " + response.headers());
                    Log.d("test", "raw : " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<JsonPrimitive> call, Throwable t) {
                mHandler = new Handler();

                Thread failToastThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "서버 불러오기 실패", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                failToastThread.start();
            }
        });
    }
}