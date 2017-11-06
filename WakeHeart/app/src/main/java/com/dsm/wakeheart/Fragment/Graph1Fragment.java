package com.dsm.wakeheart.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dsm.wakeheart.Graph.Axis;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * 시간 그래프
 */
public class Graph1Fragment extends Fragment implements OnChartValueSelectedListener, OnChartGestureListener {
    private LineChart lineChart;
    ArrayList<Axis> heartVals = new ArrayList<>();
    ArrayList<Integer> hourInterval = new ArrayList<>();
    ArrayList<String> xVals = new ArrayList<>();
    ArrayList<Entry> yVals = new ArrayList<>();

    int cnt = 24;
    float limitLineNum = 140f; //리미트 라인 값
    boolean isHourExist = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph1, container, false);

        lineChart = (LineChart) rootView.findViewById(R.id.lineChart);
        //백그라운드 데이터 존재 여부 수신
        isHourExist = getIsHourExist();

        if (isHourExist) {
            setAxisData(cnt); //X축(날짜), y축(심박수, Int) 값 추가
            setChartData(); //Y축 Entry 타입 변환, 기타 세팅
        } else {
            initZeroChartData(); //X축(날짜), y축(심박수, Int) 값 0으로 추가
            setChartData(); //Y축 Entry 타입 변환, 기타 세팅
        }

        return rootView;
    }


    //setAxisData 실행 및 chart 세팅
    public void setChartData() { //매개변수는 setAxisData 때문
        LineDataSet lineDataSet;
        String limitText = getString(R.string.limitText); // Critical Blood Pressure
//        Typeface mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        //y값(심박수, Entry) 추가
        yVals.clear();
        for (Axis axis : heartVals) {
            yVals.add(new Entry(axis.getIndex(), axis.getY()));
        }

        // X축 설정
        XAxis x = lineChart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM); //차트 하단에 x값 표시
        x.setTextSize(12f);
        x.setTextColor(Color.BLACK);
        x.setDrawGridLines(true);
        x.enableGridDashedLine(10f, 10f, 0f); //점선
        x.setGridColor(Color.LTGRAY);
        lineChart.setDrawGridBackground(true);
//        x.setGranularity(1f); //간격제한
        x.setLabelCount(8);
        x.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (((int) value) >= xVals.size())
                    return xVals.get(xVals.size() - 1);
                else
                    return xVals.get((int) value);
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
        y.setTextSize(13f);
        y.setDrawLimitLinesBehindData(true); // limit lines are drawn behind data (and not on top)

        //y축 리미트라인 추가
        LimitLine ll = new LimitLine(limitLineNum, limitText);
        ll.setLineColor(Color.RED);
        ll.setLineWidth(2f);
        ll.enableDashedLine(10f, 10f, 0f);
        ll.setTextColor(Color.BLACK);
        ll.setTextSize(10f);

        y.addLimitLine(ll);


        //차트 설정
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(true);
        lineChart.animateY(2000);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.getDescription().setEnabled(false); //우측 하단에 나오는 문구 제거
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(false); //핀치 줌 가능(두 손가락으로 확대 및 축소)
        lineChart.setDoubleTapToZoomEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.setMaxHighlightDistance(300);
        lineChart.setDoubleTapToZoomEnabled(true);
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
            lineDataSet.setValueFormatter(new IValueFormatter() {
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
    public void setAxisData(int cnt) {
        Calendar calendar = Calendar.getInstance();
        final String hourFormat = "HH시";
        //초기화
        heartVals.clear();
        xVals.clear();

        //x값(날짜, string), y값(심박, int) 추가
        calendar.add(Calendar.HOUR_OF_DAY, -cnt + 1); //cnt - 1 시간 전으로
        for (int i = 0; i < cnt; i++) {
            xVals.add(new SimpleDateFormat(hourFormat).format(calendar.getTime()));
            calendar.add(Calendar.HOUR_OF_DAY, 1);
//            heartVals.add(new Axis(i, 70 + i*2));
            heartVals.add(new Axis(i, hourInterval.get(i)));
        }
    }

    /**
     * 시간 단위 데이터 존재 여부
     *
     * @return
     */
    public boolean getIsHourExist() {
        boolean defaultFalse = false;
        boolean isHourExist = this.getActivity().getSharedPreferences("isDataPref", MODE_PRIVATE).getBoolean("isHourExist", defaultFalse);
        return isHourExist;
    }

    /**
     * 그래프 내 x,y값 0세팅 및 에러 발생 토스트메시지
     */
    public void initZeroChartData() {
        Toast.makeText(getContext(), "심박수 값을 받지 못했습니다", Toast.LENGTH_LONG).show();

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
