package com.example.prabhendu.connectme;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class TagCreationScreen extends ActionBarActivity {

    private static Button startDate, startTime;
    private static TextView startDateValue, startTimeValue;
    static final String startIdentifier = "START";

    private static Button endDate, endTime;
    private static TextView endDateValue, endTimeValue;
    static final String endIdentifier = "END";

    static String identifier = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_creation_screen);

        EditText tagName = (EditText) findViewById(R.id.tagName);
        tagName.setHint("Tag Name");

        EditText tagDesc = (EditText) findViewById(R.id.tagDescription);
        tagName.setHint("Tag Description");

        startDate = (Button) findViewById(R.id.startDate);
        startDateValue = (TextView) findViewById(R.id.startDateValue);
        startTime = (Button) findViewById(R.id.startTime);
        startTimeValue = (TextView) findViewById(R.id.startTimeValue);

        endDate = (Button) findViewById(R.id.endDate);
        endDateValue = (TextView) findViewById(R.id.endDateValue);
        endTime = (Button) findViewById(R.id.endTime);
        endTimeValue = (TextView) findViewById(R.id.endTimeValue);

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "startDatePicker");
    }
    public void endDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "endDatePicker");
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month+1, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Fragment dateStart = getActivity().getSupportFragmentManager().findFragmentByTag("startDatePicker");
            if (dateStart != null) {
                startDateValue.setText(month + "/" + day + "/" + year);
            }
            Fragment dateEnd = getActivity().getSupportFragmentManager().findFragmentByTag("endDatePicker");
            if (dateEnd != null) {
                endDateValue.setText(month + "/" + day + "/" + year);
            }
        }
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "startTimePicker");
    }
    public void endTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "endTimePicker");
    }
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Fragment timeStart = getActivity().getSupportFragmentManager().findFragmentByTag("startTimePicker");
            if (timeStart != null) {
                startTimeValue.setText(hourOfDay + ":" + minute);
            }
            Fragment timeEnd = getActivity().getSupportFragmentManager().findFragmentByTag("endTimePicker");
            if (timeEnd != null) {
                endTimeValue.setText(hourOfDay + ":" + minute);
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tag_creation_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
