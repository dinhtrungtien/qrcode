package com.example.qrcode.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.qrcode.R;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class CalendarFragment extends Fragment {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private View rootView;
    private Button btnExport;
    private EditText eventName;


    public CalendarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        datePicker = rootView.findViewById(R.id.date_picker);
        timePicker = rootView.findViewById(R.id.time_picker);
        btnExport = rootView.findViewById(R.id.exportCalendar);

        final View dialogView = View.inflate(requireContext(), R.layout.fragment_calendar, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

//                time = calendar.getTimeInMillis();
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogView);
        alertDialog.show();

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }

}