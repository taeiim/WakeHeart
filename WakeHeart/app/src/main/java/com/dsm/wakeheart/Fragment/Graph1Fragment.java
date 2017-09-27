package com.dsm.wakeheart.Fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dsm.wakeheart.Activity.SettingsActivity;
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
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by parktaeim on 2017. 8. 25..
 */

public class Graph1Fragment extends android.support.v4.app.Fragment {
    ImageView settingsBtn;
    private LineChart lineChart;
    ArrayList<Axis> heartVals = new ArrayList<>();
    ArrayList<String> xVals = new ArrayList<>();
    ArrayList<Entry> yVals = new ArrayList<>();

    int cnt = 6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph1,container,false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //세로 고정

        lineChart = (LineChart) rootView.findViewById(R.id.lineChart);

        setChartData(cnt);


        //설정 버튼 누르면 설정 액티비티로 넘어감
        settingsBtn = (ImageView) rootView.findViewById(R.id.setting_icon);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }




    //setAxisData 실행 및 chart 세팅
    public void setChartData(int cnt) { //매개변수는 setAxisData 때문
        LineDataSet lineDataSet;
        String limitText = getString(R.string.limitText); // Critical Blood Pressure
//        Typeface mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        //X축(날짜), y축(심박수, Int) 값 추가
        setAxisData(cnt);

        //y값(심박수, Entry) 추가
        yVals.clear();
        for (Axis axis : heartVals) {
            yVals.add(new Entry(axis.getX(), axis.getY()));
        }

        // X축 설정
        XAxis x = lineChart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setTextSize(12f);
        x.setTextColor(Color.BLACK);
        x.setEnabled(true);
        x.setDrawGridLines(true);
        x.setGridColor(Color.LTGRAY);
//        x.setGranularity(1f); //간격제한
        x.setLabelCount(cnt);
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
        YAxis y = lineChart.getAxisLeft();
        y.setLabelCount(6, false); //y축 레이블을 6개 생성함
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(true);
        y.setGridColor(Color.LTGRAY);
        y.setAxisLineColor(Color.BLACK);
        y.setTextSize(16f);

        //y축 리미트라인 추가
        LimitLine ll = new LimitLine(5f, limitText);
        ll.setLineColor(Color.RED);
        ll.setLineWidth(2f);
        ll.setTextColor(Color.BLACK);
        ll.setTextSize(12f);
        y.addLimitLine(ll);


        //차트 설정
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(true);
        lineChart.animateXY(1000, 1000);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setMaxHighlightDistance(300);

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(yVals);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
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
            heartVals.add(new Axis(i, i * 2));
        }
    }
}