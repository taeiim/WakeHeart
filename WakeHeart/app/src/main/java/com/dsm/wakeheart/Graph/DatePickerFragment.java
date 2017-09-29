package com.dsm.wakeheart.Graph;

        import android.app.AlertDialog;
        import android.app.DatePickerDialog;
        import android.app.Dialog;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.support.v4.app.DialogFragment;
        import android.util.TypedValue;
        import android.view.Gravity;
        import android.widget.DatePicker;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import java.util.Calendar;

/**
 * Created by admin on 2017-09-21.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                AlertDialog.BUTTON_POSITIVE,this,year,month,day);

        // Create a TextView programmatically.
        TextView tv = new TextView(getActivity());

        // Create a TextView programmatically
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setPadding(10, 10, 10, 10);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        tv.setText("This is a custom title.");
        tv.setTextColor(Color.parseColor("#ff0000"));
        tv.setBackgroundColor(Color.parseColor("#FFD2DAA7"));

        // Set the newly created TextView as a custom tile of DatePickerDialog
        //dpd.setCustomTitle(tv);

        // Or you can simply set a tile for DatePickerDialog
            /*
                setTitle(CharSequence title)
                    Set the title text for this dialog's window.
            */
        dpd.setTitle("This is a simple title."); // Uncomment this line to activate it

        // Return the DatePickerDialog
        return  dpd;
    }

    public void onDateSet(DatePicker view, int year, int month, int day){
        // Do something with the chosen date
    }
}