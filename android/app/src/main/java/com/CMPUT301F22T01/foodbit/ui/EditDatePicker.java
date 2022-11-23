package com.CMPUT301F22T01.foodbit.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class EditDatePicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private EditText editText;
    private int day;
    private int month;
    private int year;
    private Context _context;
    private Calendar calendar;

    public EditDatePicker(Context context, EditText editText)
    {
        this.editText = editText;
        this.editText.setOnClickListener(this);
        this._context = context;

        calendar = Calendar.getInstance(TimeZone.getDefault());
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        updateDisplay();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        month = monthOfYear;
        day = dayOfMonth;
        updateDisplay();

    }

    @Override
    public void onClick(View v) {
        calendar.set(year,month,day);
        DatePickerDialog dialog = new DatePickerDialog(_context, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();

    }
    // updates the date in the birth date EditText
    private void updateDisplay() {

        editText.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(day).append("/").append(month + 1).append("/").append(year).append(" "));
    }
    public Date getDate() {
        return new Date(year,month,day);
    }


}
