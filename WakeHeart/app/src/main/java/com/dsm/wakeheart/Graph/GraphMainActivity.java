package com.dsm.wakeheart.Graph;


import android.app.DatePickerDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dsm.wakeheart.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import static com.dsm.wakeheart.Graph.Mode.DAY;
import static com.dsm.wakeheart.Graph.Mode.MONTH;
import static com.dsm.wakeheart.Graph.Mode.WEEK;
//y값은 Entry형, x값은 String형
// 현재 코드에서는 x값은 xVals
// y값은 heartVals를 거쳐 Entry형인 yVals로 추가함

public class GraphMainActivity extends AppCompatActivity implements View.OnClickListener, OnChartGestureListener, OnChartValueSelectedListener {
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private DatePickerDialog FromDialog;
    private DatePickerDialog ToDialog;
    private SimpleDateFormat dateFormatter;

    private LineChart lineChart;
    LinearLayout dateSel;
    ArrayList<Axis> tempVals = new ArrayList<>(Arrays.asList(new Axis(0f, 70f), new Axis(1f, 78f), new Axis(2f, 84f), new Axis(3f, 75f), new Axis(4f, 88f),
            new Axis(5f, 70f), new Axis(6f, 71f), new Axis(7f, 83f), new Axis(8f, 84f), new Axis(9f, 70f), new Axis(10f, 80f), new Axis(11f, 88f), new Axis(12f, 70f), new Axis(13f, 83f),
            new Axis(14f, 89f), new Axis(15f, 78f), new Axis(16f, 73f), new Axis(17f, 78f), new Axis(18f, 79f), new Axis(19f, 83f), new Axis(20f, 75f), new Axis(21f, 79f),
            new Axis(22f, 90f), new Axis(23f, 76f), new Axis(24f, 88f), new Axis(25f, 85f), new Axis(26f, 80f), new Axis(27f, 73f), new Axis(28f, 78f), new Axis(29f, 86f), new Axis(30f, 80f),
            new Axis(31f, 76f), new Axis(32f, 70f), new Axis(33f, 88f), new Axis(34f, 83f), new Axis(35f, 77f), new Axis(36f, 80f), new Axis(37f, 77f)));
    ArrayList<Axis> heartVals = new ArrayList<>();
    ArrayList<String> xVals = new ArrayList<>();
    ArrayList<Entry> yVals = new ArrayList<>();
    private ArrayList<Button> buttonList = null;

    private final int IWEEK = 7; //7일
    private final int ISIXMONTH = 6; //6개월
    private final int IFIVEWEEK = 5; //5주
    private final int ANMONTH = 30;
    Mode mode = Mode.DAY;
    int cnt = IWEEK;
    // 지정에서 사용하는 캘린더
    Calendar calendar;

    //데이터 처리에 사용할 ArrayList
    public ArrayList<Integer> threeSecInterval = new ArrayList<>();
    ArrayList<Integer> hourInterval = new ArrayList<>();
    ArrayList<Integer> dayInterval = new ArrayList<>();
    ArrayList<Integer> weekInterval = new ArrayList<>();
    ArrayList<Integer> monthInterval = new ArrayList<>();

    //직접 지정에 사용할 클래스 생성
    DateData fromDateData = new DateData(0, 0);
    DateData toDateData = new DateData(0, 0);

    // 모든 세팅 후 초기화
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //편의상 세로 고정
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US); //날짜 표시 포맷
        DialogDatePicker(); // 미완된 직접지정 알림창
        dateSel = (LinearLayout) findViewById(R.id.dateSel); //직접지정용 레이아웃
        dateSel.setVisibility(View.GONE);

        lineChart = (LineChart) findViewById(R.id.lineChart);
        initSegmentbuttons(); //단위(일,주,월) 선택할 버튼 초기화
        setChartData(DAY, IWEEK, IWEEK); // 차트 세팅
    }

    //setAxisData 실행 및 chart 세팅
    public void setChartData(Mode mode, int back, int duration) { //매개변수는 setAxisData 위함
        LineDataSet lineDataSet;
        float limitAround = 83f;
        String limitText = getString(R.string.limitText); // 특정 y값 라인으로 표시할 문자열
//        Typeface mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        //X축(날짜), y축(심박수, Int) 값 추가
        setAxisData(mode, back, duration);

        //y값(심박수, Entry) 추가
        yVals.clear();
        for (Axis axis : heartVals) {
            yVals.add(new Entry(axis.getX(), axis.getY()));
        }

        // X축 설정
        lineChart.getXAxis().setEnabled(true); //x값 사용
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); //차트 하단에 x값 표시
        lineChart.getXAxis().setTextSize(12f);
        lineChart.getXAxis().setTextColor(Color.BLACK);
        lineChart.getXAxis().enableGridDashedLine(10f, 10f, 0f);
        lineChart.getXAxis().setDrawGridLines(true); // 점선
        lineChart.setDrawGridBackground(true);
        lineChart.getXAxis().setGridColor(Color.LTGRAY);
        lineChart.getXAxis().setLabelCount(cnt, true); //레이블 나누는 수
        if (mode == WEEK) {
            lineChart.getXAxis().setLabelCount(6, true);
        }
        lineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() { //x값 데이터 포맷
            @Override
            // 오류 나는 부분으로, 밑에 있는 주석이 본래 코드. 대충 바꿔서 이상하지만 오류는 안 나게 나옴
            public String getFormattedValue(float value, AxisBase axis) {
                if (((int) value) >= xVals.size())
                    return xVals.get(xVals.size() - 1);
                else
                    return xVals.get((int) value);
            }
            //return xVals.get((int) value);
        });


        // Y축 설정
        YAxis y = lineChart.getAxisLeft(); //왼쪽에 y값 표시
        y.setLabelCount(6, false); //y축 레이블을 6개 생성함
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setDrawGridLines(true);
        y.enableGridDashedLine(10f, 10f, 0f); // 점선
        y.setGridColor(Color.LTGRAY);
        y.setAxisLineColor(Color.BLACK);
        y.setTextSize(16f);
        y.setDrawLimitLinesBehindData(true); // limit lines are drawn behind data (and not on top)


        //y축 리미트라인 추가
        LimitLine ll = new LimitLine(limitAround, limitText);
        ll.setLineColor(Color.RED);
        ll.setLineWidth(4f);
        ll.enableDashedLine(10f, 10f, 0f);
        ll.setTextColor(Color.BLACK);
        ll.setTextSize(10f);

        y.addLimitLine(ll);

        //차트 설정
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(true); // 범례 표시
//        lineChart.animateXY(2000, 2000); //애니메이션 효과
        lineChart.animateY(2000);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.getDescription().setEnabled(false); //우측 하단에 나오는 문구 제거
        lineChart.setTouchEnabled(true); //터치 가능
        lineChart.setDragEnabled(true); //드래그 가능
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(false); //핀치 줌 가능(두 손가락으로 확대 및 축소)
        lineChart.setDoubleTapToZoomEnabled(true);
        lineChart.setDrawGridBackground(false); //백그라운드 손 댈 수 없음
        lineChart.setMaxHighlightDistance(300);
        lineChart.setOnChartGestureListener(this);
        lineChart.setOnChartValueSelectedListener(this);

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(yVals);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged(); // let the chart know it's data changed
            lineChart.invalidate(); // refresh
        } else {
            // create a dataset and give it a type
            lineDataSet = new LineDataSet(yVals, "Heart Rate");

            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lineDataSet.setCubicIntensity(0.2f);
            lineDataSet.setDrawFilled(false);
            lineDataSet.setDrawCircles(true);
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setLineWidth(1.8f);
            lineDataSet.setCircleRadius(4f);
            lineDataSet.setCircleColor(ContextCompat.getColor(this, R.color.colorPrimary));
            lineDataSet.setHighLightColor(Color.rgb(244, 117, 117)); //연분홍
            lineDataSet.setColor(Color.BLACK);
            lineDataSet.setFillColor(Color.WHITE);
            lineDataSet.setFillAlpha(100); // 투명도
            lineDataSet.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });
            lineDataSet.setValueFormatter(new IValueFormatter() { //y값 데이터 포맷, 이 경우는 최소 한자리 수 이상, 소수점이 있다면 한자리까지
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return new DecimalFormat("0.#").format(value);
                }
            });

            LineData data = new LineData(lineDataSet);
//            data.setValueTypeface(mTfLight);
            data.setValueTextSize(12f);
            data.setDrawValues(false);

            lineChart.setData(data); // set data
            lineChart.invalidate(); //refresh
        }
    }

    //chart Data setting
    public void setAxisData(Mode mode, int back, int duration) {
        calendar = Calendar.getInstance();
        final String dateFormat = "MM/dd";
        final String monthFormat = "MM월";
        final String weekFormat = "MM/dd";
        //초기화
        heartVals.clear();
        xVals.clear();

        //x값(날짜, string), y값(심박, int) 추가
        switch (mode) {
            case DAY:
                //cnt - 1일 전으로 세팅
                calendar.add(Calendar.DATE, -back + 1);
                //오늘까지
                for (int i = 0; i < duration; i++) {
                    xVals.add(new SimpleDateFormat(dateFormat).format(calendar.getTime()));
                    calendar.add(Calendar.DATE, 1);
//                    heartVals.add(new Axis(i, i));
                }

                for (int i = 0; i < duration + 1; i++)
                    heartVals.add(tempVals.get(i));
                break;

//            case WEEK:
//                //  (cnt - 1) 주 전으로 세팅
//                calendar.add(Calendar.WEEK_OF_MONTH, (-cnt + 1));
//                //오늘까지, cnt개 만큼
//                for (int i = 0; i < cnt; i++) {
//                    xVals.add(new SimpleDateFormat(weekFormat).format(calendar.getTime()));
//                    heartVals.add(new Axis(i, i));
//                    calendar.add(Calendar.WEEK_OF_MONTH, 1);
//                }
//                for (int i=0; i<cnt; i++)
//                    heartVals.add(tempVals.get(i));
//                break;
            case WEEK:
                //  (cnt - 1) 주 전으로 세팅
                calendar.add(Calendar.DATE, (-back + 1));
                //오늘까지, cnt개 만큼
                for (int i = 0; i < duration; i++) {
                    xVals.add(new SimpleDateFormat(weekFormat).format(calendar.getTime()));
//                    heartVals.add(new Axis(i, i));
                    calendar.add(Calendar.DATE, 1);
                }
                for (int i = 0; i < duration; i++) {
                    heartVals.add(tempVals.get(i));
                }

                break;
            case MONTH:
                //cnt -1 월 전으로 세팅
                calendar.add(Calendar.MONTH, (-back + 1));
                //오늘까지, cnt개 만큼 값 추가
                for (int i = 0; i < duration; i++) {
                    xVals.add(new SimpleDateFormat(monthFormat).format(calendar.getTime()));
                    calendar.add(Calendar.MONTH, 1);
//                    heartVals.add(new Axis(i, 72 + i * 2));
                }
                for (int i = 0; i < duration; i++) {
                    heartVals.add(tempVals.get(i));
                }
                break;
        }
    }

    //버튼 초기화
    private void initSegmentbuttons() {
        buttonList = new ArrayList<>();

        Button btnWeek = (Button) findViewById(R.id.buttonAnWeek);
        Button btn1Month = (Button) findViewById(R.id.button1Month);
        Button btn6Month = (Button) findViewById(R.id.button6Month);
        Button btnAssign = (Button) findViewById(R.id.buttonAssign);
        Button btnSubmit = (Button) findViewById(R.id.submitBtn);

        btnWeek.setOnClickListener(this);
        btn1Month.setOnClickListener(this);
        btn6Month.setOnClickListener(this);
        btnAssign.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        buttonList.add(btnWeek);
        buttonList.add(btn1Month);
        buttonList.add(btn6Month);
        buttonList.add(btnAssign);
    }

    //클릭이벤트
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAnWeek:
                dateSel.setVisibility(View.GONE);
                setChartData(DAY, IWEEK, IWEEK);
                break;

            case R.id.button1Month:
                dateSel.setVisibility(View.GONE);
                setChartData(WEEK, ANMONTH, ANMONTH);
//                setChartData(DAY, ANMONTH);
                break;

            case R.id.button6Month:
                dateSel.setVisibility(View.GONE);
                setChartData(MONTH, ISIXMONTH, ISIXMONTH);
                break;
            //직접지정
            case R.id.buttonAssign:
                dateSel.setVisibility(View.VISIBLE);
//                DialogDatePicker();
                break;

            case R.id.fromDateEtxt:
                FromDialog.show();
                break;
            case R.id.toDateEtxt:
                ToDialog.show();
                break;
            case R.id.submitBtn:
                submitClicked();
                break;
        }
    }

    //직접 지정에서 사용될 datepickerDialog
    //확인 버튼 - 날짜값 차이 계산하기
    private void DialogDatePicker() {
        fromDateEtxt = (EditText) findViewById(R.id.fromDateEtxt);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        toDateEtxt = (EditText) findViewById(R.id.toDateEtxt);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        DatePickerDialog.OnDateSetListener mDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    // onDateSet method
                    Calendar cal = Calendar.getInstance();

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        cal.set(year, monthOfYear, dayOfMonth);
                        fromDateEtxt.setText(dateFormatter.format(cal.getTime()));
                        fromDateData.changeData(monthOfYear, dayOfMonth);
                    }
                };
        Calendar baseCalendar = Calendar.getInstance();
        FromDialog = new DatePickerDialog(this, R.style.AppTheme, mDateSetListener,
                baseCalendar.get(Calendar.YEAR), baseCalendar.get(Calendar.MONTH), baseCalendar.get(Calendar.DATE));

        FromDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        FromDialog.getDatePicker().setMaxDate(baseCalendar.getTimeInMillis()); //최댓값
        FromDialog.setCanceledOnTouchOutside(true); //다이얼로그 밖을 터치하면 취소

        ViewGroup.LayoutParams params = FromDialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        FromDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////

        DatePickerDialog.OnDateSetListener mDateSetListener2 =
                new DatePickerDialog.OnDateSetListener() {
                    // onDateSet method
                    Calendar cal = Calendar.getInstance();

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        cal.set(year, monthOfYear, dayOfMonth);
                        //리스너에선 캘린더 최솟값 조정 못하는 것 고려해야됨
//                        if(fromDateData.day == 0 || fromDateData.month == 0) { //to부터 설정하므로 그냥 추가함
                        toDateEtxt.setText(dateFormatter.format(cal.getTime()));
                        toDateData.changeData(monthOfYear, dayOfMonth);
//                        } else (year)
                    }
                };

        ToDialog = new DatePickerDialog(GraphMainActivity.this, R.style.AppTheme, mDateSetListener2,
                baseCalendar.get(Calendar.YEAR), baseCalendar.get(Calendar.MONTH), baseCalendar.get(Calendar.DATE));

        ToDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        ToDialog.getDatePicker().setMaxDate(baseCalendar.getTimeInMillis()); //최댓값 세팅
//        ToDialog.getDatePicker().setMinDate();
        ToDialog.setCanceledOnTouchOutside(true); //다이얼로그 밖을 터치하면 취소

        ViewGroup.LayoutParams params2 = ToDialog.getWindow().getAttributes();
        params2.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params2.height = ViewGroup.LayoutParams.MATCH_PARENT;
        ToDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    private void submitClicked() {
        if (fromDateData.day == 0 || fromDateData.month == 0 //둘중 하나라도 값이 설정되어 있지 않을 때
                || toDateData.day == 0 || toDateData.month == 0) {
            Toast.makeText(getApplicationContext(), "시작일과 최종일을 설정해주세요", Toast.LENGTH_LONG).show();
            return;
        }
        Calendar calendar = Calendar.getInstance(); // 현재 날짜
        //실제 날에서의 차이
        int Month = Integer.parseInt(new SimpleDateFormat("MM").format(System.currentTimeMillis()));
        int day = Integer.parseInt(new SimpleDateFormat("HH").format(System.currentTimeMillis()));
        int back_month = (Month + 1) - fromDateData.month;
        int back_day = day - fromDateData.day;
        //기준일로부터 카운팅, dif : difference
        int monthDif = toDateData.month - fromDateData.month;
        int dayDif = toDateData.day - fromDateData.day;
        //올해에서 작년으로 갈 경우를 고려, 날짜간 차잇값 양수 설정
        back_month = setDatePlus(back_month);
        back_day = setDatePlus(back_day);
        monthDif = setDatePlus(monthDif);
        dayDif = setDatePlus(dayDif);
        //편의상 단위별은 미지원허고 월 변경 시 월 단위로 줌
        int day222 = 1;
        if (monthDif != 0) { //월단위 지정
            setChartData(MONTH, back_month, monthDif);
        } else {
            if (dayDif != 0) { //일 단위 지정
                setChartData(DAY, back_day, dayDif);
            } else { //같은 날짜
                Toast.makeText(getApplicationContext(), "시작일과 최종일을 서로 다르게 설정해주세요.", Toast.LENGTH_LONG).show();
            }
        }

        //4초 뒤 공지
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
        }
        Toast.makeText(getApplicationContext(), "단위 지정은 추후 업데이트될 예정이며,\n현재는 바뀐 날짜 중 큰 단위 기준으로 표시됩니다", Toast.LENGTH_LONG).show();
    }

    private int setDatePlus(int i) {
        if (i < 0) {
            i = i + 12 + 1;
        }else if(i > 0) {
            i++;
        }
        return i;
    }

    //    void dateModeChanged (int month, int day) {
//        //c--- 현재 날짜 기준
//        //--- 선택된 날짜 기준
//        if (month != bmonth) { // 월이 다름, 월단위 표현
//            cnt = month - bmonth;
//            if(cnt < 0){ cnt = -cnt; }
//            mode = MONTH;
//        } else if (day != bday){ // 일이 다름, 일단위 표현
//            cnt = day - bday;
//            if(cnt < 0){ cnt = - cnt; }
//            mode = DAY;
//        }
//    }
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            lineChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + lineChart.getLowestVisibleX() + ", high: " + lineChart.getHighestVisibleX());
        Log.i("MIN MAX", "xmin: " + lineChart.getXChartMin() + ", xmax: " + lineChart.getXChartMax() + ", ymin: " + lineChart.getYChartMin() + ", ymax: " + lineChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}