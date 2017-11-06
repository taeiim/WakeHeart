package com.dsm.wakeheart.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.dsm.wakeheart.Graph.APIClient;
import com.dsm.wakeheart.Graph.Axis;
import com.dsm.wakeheart.Graph.DateData;
import com.dsm.wakeheart.Graph.Mode;
import com.dsm.wakeheart.Graph.MyHeartRateItem;
import com.dsm.wakeheart.Graph.RetrofitGraphInterface;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.dsm.wakeheart.Graph.Mode.DAY;
import static com.dsm.wakeheart.Graph.Mode.MONTH;
import static com.dsm.wakeheart.Graph.Mode.WEEK;

/**
 * 일, 월, 6개월 그래프
 */
public class Graph2Fragment extends Fragment implements View.OnClickListener, OnChartGestureListener, OnChartValueSelectedListener {
    String tempToken = "JWT eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZGVudGl0eSI6InJyIiwiaWF0IjoxNTA5NDU3NzcyLCJuYmYiOjE1MDk0NTc3NzIsImV4cCI6MTU0MDk5Mzc3Mn0.jjDSfEFdsW58YrPbxrMS-udPZ-S2fwdtnIMX2b7ThLo";

    private View rootView;

    //직접 지정
    LinearLayout dateSel;
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private DatePickerDialog FromDialog;
    private DatePickerDialog ToDialog;
    private SimpleDateFormat dateFormatter;

    //그래프
    private LineChart lineChart;
    //그래프에 사용될 어레이리스트
    ArrayList<Axis> heartVals = new ArrayList<>(); // 첫 y값
    ArrayList<String> xVals = new ArrayList<>(); // 최종 x값
    ArrayList<Entry> yVals = new ArrayList<>(); // 최종 y값
    float limitLineNum = 83f;

    // 기본 제공에 사용되는 상수 (하루, 일주일, 월)
    private final int IWEEK = 7; //7일
    private final int ISIXMONTH = 6; //6개월
    private final int IFIVEWEEK = 5; //5주
    private final int ANMONTH = 30; //30일, 한달


    //백그라운드에서 전달 받은 ArrayList
    ArrayList<Integer> weekInterval = new ArrayList<>();
    ArrayList<Integer> dayInterval = new ArrayList<>();
    ArrayList<Integer> monthInterval = new ArrayList<>();

    //직접 지정
    private ArrayList<Button> buttonList = null; //버튼
    DateData fromDateData = new DateData(0, 0, 0);
    DateData toDateData = new DateData(0, 0, 0);
    String fromData = null; //서버에 보낼 스트링 yyyy-mm-dd
    String toData = null; //서버에 보낼 스트링 yyyy-mm-dd
    Mode mode2 = null; //스피너에 사용
    Byte bMode = -1; //스피너에 사용, 서버 전송용
    Calendar calendar; //지정에서 사용하는 캘린더
    private final Byte BDAY = 0; //스피너값 모드에 따라 들어갈 상수
    private final Byte BWEEK = 1; //스피너값 모드에 따라 들어갈 상수
    private final Byte BMONTH = 2; //스피너값 모드에 따라 들어갈 상수

    boolean isDataExist = false;
    boolean isServerExist = false;


    String[] modes = {"::단위::", "일", "주", "월"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_graph2, container, false);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN); //날짜 표시 포맷

        DialogDatePicker(); //직접지정 다이얼로그
        setSpinner(); // 직접 지정 단위 선택 스피너
        dateSel = (LinearLayout) rootView.findViewById(R.id.dateSel); //직접지정용 레이아웃
        dateSel.setVisibility(View.GONE);

        lineChart = (LineChart) rootView.findViewById(R.id.lineChart);
        initSegmentButtons(); //단위(일,주,월) 선택할 버튼 초기화

        //데이터 수신받아 최솟값인 dayInterval 의 유무
        isDataExist = getIsDayExist();

        //받았으면 받은 데이터 토대로, 받지 못했으면 모든 값이 0으로 표현
        if (isDataExist) {
            //X축(날짜), y축(심박수, Int) 값 추가
            setAxisData(DAY, IWEEK, IWEEK);
            setChartData(DAY); // 차트 세팅
        } else {
            initZeroChartData();
            setChartData(DAY);
        }


        return rootView;
    }


    /**
     * 차트 설정
     *
     * @param mode 무슨 단위로 하는가(일,월)
     */
    public void setChartData(Mode mode) { //매개변수는 setAxisData 위함
        LineDataSet lineDataSet;
        String limitText = getString(R.string.limitText); // 특정 y값 라인으로 표시할 문자열
//        Typeface mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");


        //y값(심박수, Entry) 추가
        yVals.clear();
        for (Axis axis : heartVals) {
            yVals.add(new Entry(axis.getIndex(), axis.getY()));
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
        //모드에 따라 레이블 갯수 다르게 기본 7, 4, 6개
        switch (mode) {
            case DAY:
                lineChart.getXAxis().setLabelCount(7, true);
                break;
            case WEEK:
                lineChart.getXAxis().setLabelCount(4, true);
                break;
            case MONTH:
                lineChart.getXAxis().setLabelCount(6, true);
                break;
        }
        lineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() { //x값 데이터 포맷
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (((int) value) >= xVals.size()) {
                    if ((int) value <= 0) {
                        return xVals.get(0);
                    }
                    return xVals.get(xVals.size() - 1);
                } else {
                    if ((int) value < 0) {
                        return xVals.get(0);
                    }
                    return xVals.get((int) value);
                }
            }
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
        LimitLine ll = new LimitLine(limitLineNum, limitText);
        ll.setLineColor(Color.RED);
        ll.setLineWidth(4f);
        ll.enableDashedLine(10f, 10f, 0f);
        ll.setTextColor(Color.BLACK);
        ll.setTextSize(10f);

        y.addLimitLine(ll);

        //차트 설정
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(true); // 범례 표시
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
            // create a dataSet and give it a type
            lineDataSet = new LineDataSet(yVals, "Heart Rate");

            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lineDataSet.setCubicIntensity(0.2f);
            lineDataSet.setDrawFilled(false);
            lineDataSet.setDrawCircles(true);
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setLineWidth(1.8f);
            lineDataSet.setCircleRadius(4f);
            lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);
            lineDataSet.setCircleColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
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

    /**
     * 차트에 들어갈 데이터 설정
     *
     * @param mode     무슨 단위로 하는가(일,월)
     * @param back     며칠전으로 돌아가는가
     * @param duration back으로부터 며칠간 하는가
     */
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
//                    heartVals.add(new Axis(i, i)); //더미 심박수
                    heartVals.add(new Axis(i, dayInterval.get(i))); //처리된 심박수

                }
                break;

            case WEEK:
                //  (cnt - 1) 일 전으로 세팅
                calendar.add(Calendar.DATE, (-back + 1));
                //오늘까지, cnt 개 만큼
                for (int i = 0; i < duration; i++) {
                    xVals.add(new SimpleDateFormat(weekFormat).format(calendar.getTime()));
                    heartVals.add(new Axis(i, weekInterval.get(i))); //서버연결 심박수
                    calendar.add(Calendar.DATE, 1);
                }

                break;
            case MONTH:
                //cnt - 1 월 전으로 세팅
                calendar.add(Calendar.MONTH, (-back + 1));
                //오늘까지, cn t개 만큼 값 추가
                for (int i = 0; i < duration; i++) {
                    xVals.add(new SimpleDateFormat(monthFormat).format(calendar.getTime()));
                    calendar.add(Calendar.MONTH, 1);
                    heartVals.add(new Axis(i, monthInterval.get(i))); //서버연결 심박수
                }
                break;
        }
    }

    //버튼 초기화
    private void initSegmentButtons() {
        buttonList = new ArrayList<>();

        Button btnWeek = (Button) rootView.findViewById(R.id.buttonAnWeek);
        Button btn1Month = (Button) rootView.findViewById(R.id.button1Month);
        Button btn6Month = (Button) rootView.findViewById(R.id.button6Month);
        Button btnAssign = (Button) rootView.findViewById(R.id.buttonAssign);
        Button btnSubmit = (Button) rootView.findViewById(R.id.submitBtn);

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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAnWeek:
                dateSel.setVisibility(View.GONE);
                isDataExist = getIsDayExist();
                if (isDataExist) {
                    setAxisData(DAY, IWEEK, IWEEK);
                    setChartData(DAY);
                } else {
                    initZeroChartData();
                    setChartData(DAY);
                }
                break;

            case R.id.button1Month:
                dateSel.setVisibility(View.GONE);

                isDataExist = getIsWeekExist();
                if (isDataExist) {
                    setAxisData(WEEK, ANMONTH, ANMONTH);
                    setChartData(WEEK);
                } else {
                    initZeroChartData();
                    setChartData(WEEK);
                }
                break;

            case R.id.button6Month:
                dateSel.setVisibility(View.GONE);

                isDataExist = getIsMonthExist();
                if (isDataExist) {
                    setAxisData(MONTH, ISIXMONTH, ISIXMONTH);
                    setChartData(MONTH);
                } else {
                    initZeroChartData();
                    setChartData(MONTH);
                }
                break;

            //이하 직접지정
            case R.id.buttonAssign:
                dateSel.setVisibility(View.VISIBLE);
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

    //직접 지정에서 사용될 datepickerDialog 생성
    private void DialogDatePicker() {
        fromDateEtxt = (EditText) rootView.findViewById(R.id.fromDateEtxt);
        fromDateEtxt.setInputType(InputType.TYPE_NULL); //dateTime 타입이 보임, 개선 여지
        fromDateEtxt.requestFocus();
        toDateEtxt = (EditText) rootView.findViewById(R.id.toDateEtxt);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        DatePickerDialog.OnDateSetListener mDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    // onDateSet method
                    Calendar cal = Calendar.getInstance();

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String year2;
                        String month2;
                        cal.set(year, monthOfYear, dayOfMonth);
                        fromDateEtxt.setText(dateFormatter.format(cal.getTime()));
                        fromDateData.changeData(year, monthOfYear + 1, dayOfMonth);

                        if ((monthOfYear + 1) < 10) {
                            month2 = "0" + (monthOfYear + 1);
                        } else {
                            month2 = "" + (monthOfYear + 1);
                        }
                        if (dayOfMonth < 10) {
                            year2 = "0" + dayOfMonth;
                        } else {
                            year2 = "" + dayOfMonth;
                        }
                        fromData = year + "-" + month2 + "-" + year2;
                        Toast.makeText(getContext(), fromData, Toast.LENGTH_SHORT).show();
                    }
                };
        Calendar baseCalendar = Calendar.getInstance();
        FromDialog = new DatePickerDialog(getActivity(), R.style.AppTheme, mDateSetListener,
                baseCalendar.get(Calendar.YEAR), baseCalendar.get(Calendar.MONTH), baseCalendar.get(Calendar.DATE));

        FromDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        FromDialog.getDatePicker().setMaxDate(baseCalendar.getTimeInMillis()); //최댓값
        FromDialog.setCanceledOnTouchOutside(true); //다이얼로그 밖을 터치하면 취소

        ViewGroup.LayoutParams params = FromDialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        FromDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        /*********************************************다이얼로그간 구분선*****************************************************************/

        DatePickerDialog.OnDateSetListener mDateSetListener2 =
                new DatePickerDialog.OnDateSetListener() {
                    // onDateSet method
                    Calendar cal = Calendar.getInstance();

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        cal.set(year, monthOfYear, dayOfMonth);
                        String year2;
                        String month2;
                        //리스너에선 캘린더 최솟값 조정 못하는 것 고려해야됨
//                        if(fromDateData.day == 0 || fromDateData.month == 0) { //to부터 설정하므로 그냥 추가함
                        toDateEtxt.setText(dateFormatter.format(cal.getTime()));
                        toDateData.changeData(year, monthOfYear, dayOfMonth);

                        if ((monthOfYear + 1) < 10) {
                            month2 = "0" + (monthOfYear + 1);
                        } else {
                            month2 = "" + (monthOfYear + 1);
                        }
                        if (dayOfMonth < 10) {
                            year2 = "0" + dayOfMonth;
                        } else {
                            year2 = "" + dayOfMonth;
                        }
//
                        toData = year + "-" + month2 + "-" + year2;
                        Toast.makeText(getContext(), toData, Toast.LENGTH_SHORT).show();
//                        } else (year)
                    }
                };

        ToDialog = new DatePickerDialog(getActivity(), R.style.AppTheme, mDateSetListener2,
                baseCalendar.get(Calendar.YEAR), baseCalendar.get(Calendar.MONTH), baseCalendar.get(Calendar.DATE));

        ToDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        ToDialog.getDatePicker().setMaxDate(baseCalendar.getTimeInMillis()); //최댓값 세팅
//        ToDialog.getDatePicker().setMinDate(); //최솟값 세팅
        ToDialog.setCanceledOnTouchOutside(true); //다이얼로그 밖을 터치하면 취소
        ViewGroup.LayoutParams params2 = ToDialog.getWindow().getAttributes();
        params2.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params2.height = ViewGroup.LayoutParams.MATCH_PARENT;
        ToDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    //다이얼로그 확인 버튼, 서버 수신 및 차트 세팅
    private void submitClicked() {
        if (fromData == null || toData == null || bMode == -1) {  //셋 중 하나라도 값이 설정되어 있지 않을 때
            Toast.makeText(getContext(), "시작일과 최종일, 단위를 설정해주세요", Toast.LENGTH_LONG).show();
        } else {
            getServerData(fromData, toData, bMode);
        }
    }

    /**
     * 처리 데이터 존재 여부 확인
     */
    public Boolean getIsDayExist() {
        Boolean defaultFalse = false;
        boolean isDataExist = this.getActivity().getSharedPreferences("isDataPref", MODE_PRIVATE).getBoolean("isDayExist", defaultFalse);
        Log.d("test", "dayInterval " + isDataExist);
        return isDataExist;

    }

    public Boolean getIsWeekExist() {
        Boolean defaultFalse = false;
        boolean isWeekExist = this.getActivity().getSharedPreferences("isDataPref", MODE_PRIVATE).getBoolean("isWeekExist", defaultFalse);
        return isWeekExist;
    }

    public Boolean getIsMonthExist() {
        Boolean defaultFalse = false;
        boolean isMonthExist = this.getActivity().getSharedPreferences("isDataPref", MODE_PRIVATE).getBoolean("isMonthExist", defaultFalse);
        return isMonthExist;
    }

    /**
     * 서버로부터 받아오기, x값과 y값 전부 세팅함
     *
     * @param fromDate 시작일 서버 전송용
     * @param toDate   최종일 서버 전송용
     * @param mode     단위 서버 미전송
     */
    public void getServerData(final String fromDate, final String toDate, final byte mode) {
        RetrofitGraphInterface retrofitService = APIClient.getRetrofit().create(RetrofitGraphInterface.class);
        Call<List<MyHeartRateItem>> call = retrofitService.getData(tempToken, fromDate, toDate);
        call.enqueue(new Callback<List<MyHeartRateItem>>() {
            @Override
            public void onResponse(Call<List<MyHeartRateItem>> call, Response<List<MyHeartRateItem>> response) {
                Log.d("test", "" + response.raw());
                if (response.code() == 200) {
                    isServerExist = true;  //서버에서 데이터를 잘 받음
                    Date date;
                    Calendar calendar = Calendar.getInstance();
                    List<MyHeartRateItem> json = response.body();
                    List<MyHeartRateItem> temp = new ArrayList<>(); // json 에서 존재하는 데이터만 저장
                    List<MyHeartRateItem> temp2 = new ArrayList<>(); //완성된 일 단위 데이터 저장
                    ArrayList<MyHeartRateItem> al2 = new ArrayList<>(); //주, 월 단위 저장
                    Log.d("test", "response is Successful\n" + response.raw());
                    Log.d("test", "response == " + response.body().toString());
                    Log.d("test", "array size == " + json.size());
                    for (int i = 0; i < json.size(); i++) {
                        Log.d("test", "array(" + i + ") : " + "(" + json.get(i).getRate() + ", " + json.get(i).getDate() + ")");

                        if (json.get(i).getDate() != null) {
                            temp.add(json.get(i));
                            Log.d("test", "temp arrayList에 추가됨 : " + "array(" + i + ") : " + "(" + json.get(i).getRate() + ", " + json.get(i).getDate() + ")");
                        }
                    }

                    try {
                        date = dateFormatter.parse(fromDate);
                        calendar.setTime(date);
                    } catch (ParseException e) {
                    }

                    int a = 0;
                    MyHeartRateItem dataTemp = json.get(0);

                    //날짜 순차대로 돌면서 그날의 데이터가 있으면 따로 저장하고, 아니면 0 저장
                    while (true) {
                        if (temp.size() != 0) { // 존재하는 데이터가 아예 없다 (전부 0이다)
                            if (temp.get(a).getDate() != null) {
                                dataTemp = temp.get(a);
                            }
                        }

                        if (dateFormatter.format(calendar.getTime()).equals(dataTemp.getDate())) { //어레이에 저장된 날짜와 다를 때
                            temp2.add(dataTemp);
                        } else {
                            temp2.add(new MyHeartRateItem(0, dateFormatter.format(calendar.getTime())));
                        }

                        if (dateFormatter.format(calendar.getTime()).equals(toDate)) {
                            break;
                        }
                        calendar.add(Calendar.DATE, 1);
                    }
//                substring(5, 7) : 월만 자름(MM),    substring(5, 10) : 월일 자름(MM-DD),    substring(3, 5) : 일 자름(DD)

                    int sum = 0;
                    heartVals.clear();
                    xVals.clear();
                    Log.d("test", "heartVals, xVals 추가 시작");
                    switch (mode) {
                        case 0: //일
                            for (int i = 0; i < temp2.size(); i++) {
                                heartVals.add(new Axis(i, temp2.get(i).getRate()));
                                xVals.add(temp2.get(i).getDate().substring(5, 10)); //월일 자름 MM-DD
                            }
                            break;
                        case 1: //주
                            al2.clear();

                            //주 단위 생성
                            for (int i = 0; i < temp2.size(); i++) {
                                sum += temp2.get(i).getRate();
                                if (i >= 7) {
                                    al2.add(new MyHeartRateItem(sum, temp2.get(i).getDate()));
                                    sum = 0;
                                } else {
                                    Toast.makeText(getContext(), "주 단위를 표현하기에 데이터가 부족합니다", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            //주단위 추가
                            for (int i = 0; i < al2.size(); i++) {
                                heartVals.add(new Axis(i, al2.get(i).getRate()));
                                xVals.add(al2.get(i).getDate().substring(5, 10)); //월일 자름 MM-DD
                            }
                            break;

                        case 2: //월
                            //월단위 생성
                            al2.clear();
                            for (int i = 0; i < temp2.size(); i++) {
                                sum += temp2.get(i).getRate();
                                if (i >= 30) {
                                    al2.add(new MyHeartRateItem(sum, temp2.get(i).getDate()));
                                    sum = 0;
                                } else {
                                    Toast.makeText(getContext(), "월 단위를 표현하기에 데이터가 부족합니다", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            //월 단위 추가
                            for (int i = 0; i < al2.size(); i++) {
                                heartVals.add(new Axis(i, al2.get(i).getRate()));
                                xVals.add(al2.get(i).getDate().substring(5, 7) + "월"); // 월만 자름 : MM
                            }
                            break;
                    }

                } else if (response.code() == 204) {
                    Toast.makeText(getContext(), "두 날짜 사이 심박수 데이터 없음", Toast.LENGTH_LONG).show();
                    isServerExist = false;
                } else {
                    Toast.makeText(getContext(), "에러발생!\nErrorCode : " + response.code(), Toast.LENGTH_LONG).show();
                    Log.d("test", "###호출 실패###");
                    Log.d("test", "errorBody : " + response.errorBody());
                    Log.d("test", "headers : " + response.headers());
                    Log.d("test", "raw : " + response.raw());
                    isServerExist = false;
                }

                if (isServerExist) {
                    switch (mode) {
                        case 0:
                            setChartData(DAY);
                            break;
                        case 1:
                            setChartData(WEEK);
                            break;
                        case 2:
                            setChartData(MONTH);
                            break;
                    }
                } else {
                    Toast.makeText(getContext(), "서버에서 받아온 데이터가 존재하지 않습니다", Toast.LENGTH_LONG).show();
                    initZeroChartData();
                }
            }

            @Override
            public void onFailure(Call<List<MyHeartRateItem>> call, Throwable t) {
                Toast.makeText(getContext(), "서버 연결 중 오류 발생;\n" + t.toString(), Toast.LENGTH_LONG).show();
                Log.d("onFailure Throwable", t.toString());
            }
        });
    }

    public void setSpinner() {
        final Spinner spinner = (Spinner) rootView.findViewById(R.id.selModeBtn);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, modes);
        // (파라미터) android.R은 안드로이드에서 제공하는 리소스 사용한다는 의미

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (modes[position] != "::단위::") {
                    Toast.makeText(getContext(), "선택된 단위: " + modes[position], Toast.LENGTH_LONG).show();
                }
                if (modes[position] == "일") {
                    bMode = BDAY;
                    mode2 = DAY;
                } else if (modes[position] == "주") {
                    bMode = BWEEK;
                    mode2 = WEEK;
                } else if (modes[position] == "월") {
                    bMode = BMONTH;
                    mode2 = MONTH;
                } else {
                    bMode = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 그래프 내 x,y값 0세팅
     */
    public void initZeroChartData() {
        xVals = new ArrayList<>(Arrays.asList("0", "0", "0", "0", "0", "0", "0", "0"));
        heartVals = new ArrayList<>(Arrays.asList(new Axis(0, 0), new Axis(1, 0), new Axis(2, 0), new Axis(3, 0), new Axis(4, 0), new Axis(5, 0), new Axis(6, 0), new Axis(7, 0)));
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            lineChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
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
