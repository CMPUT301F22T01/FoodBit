package com.CMPUT301F22T01.foodbit.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.type.DateTime;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


    public EditDatePicker(Context context, EditText editText)
    {
        this.editText = editText;
        this.editText.setOnClickListener(this);
        this._context = context;

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        editText.setHint(toString());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        month = monthOfYear;
        day = dayOfMonth;
        calendar.set(year,month,day);
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


    private void updateDisplay() {
        editText.setText(toString());
    }

    public Date getDate() {
        return new Date(year,month,day);
    }

    public String toString() {
        formatter.setTimeZone(calendar.getTimeZone());
        return formatter.format(calendar.getTime());
    }
    public void setDate(Date date) {
        day = date.getDate();
        month = date.getMonth();
        year = date.getYear();
        updateDisplay();
    }


}
