package com.CMPUT301F22T01.foodbit.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

/**
 * Class to display the DatePicker fragment.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    final Calendar c = Calendar.getInstance();
    private Date date;
    // Use this instance of the interface to deliver action events
    NoticeDialogListener listener;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DatePickerFragment dialog);
    }

    /**
     * Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (NoticeDialogListener) getParentFragment();
    }

    /**
     * Create a new instance of DatePickerDialog and return it
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    /**
     * Sends the chosen date back to the target fragment
     * @param view
     * @param year the chosen year
     * @param month the chosen month
     * @param day the chosen day
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {
        date = new Date(year,month,day);
        listener.onDialogPositiveClick(this);
    }
}