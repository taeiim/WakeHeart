package com.dsm.wakeheart.Graph;


        import android.app.DatePickerDialog;
        import android.content.Context;
        import android.view.View;
        import android.widget.DatePicker;

        import java.lang.reflect.Field;

/**
 * Created by admin on 2017-09-19.
 */

public class MyDatePickerDialog extends DatePickerDialog {
    public MyDatePickerDialog(Context context, int themeResId, OnDateSetListener callBack, int year,
                              int monthOfYear, int dayOfMonth) {
        super(context,themeResId, callBack, year, monthOfYear, dayOfMonth);

        try {
            Field[] f = DatePickerDialog.class.getDeclaredFields();
            for (Field dateField : f) {
                if(dateField.getName().equals("mDatePicker") ) {
                    dateField.setAccessible(true);

                    DatePicker datePicker = (DatePicker)dateField.get(this);

                    Field datePickerFields[] = dateField.getType().getDeclaredFields();

                    for(Field datePickerField : datePickerFields) {
                        if("mYearPicker".equals(datePickerField.getName())  ||
                                "mYearSpinner".equals(datePickerField.getName() )) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = new Object();
                            dayPicker = datePickerField.get(datePicker);
                            ((View)dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
            setTitle(monthOfYear+"월 / " + dayOfMonth + "일");
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        setTitle(year+"년 "+(month+1)+"월");
    }
}