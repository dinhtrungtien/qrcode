package com.example.qrcode.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qrcode.R;
import com.example.qrcode.model.History;


public class CreateFragment extends Fragment {
    private static final String RESULT = "result";

    private ConstraintLayout clipboard;
    private ConstraintLayout url;
    private ConstraintLayout text;
    private ConstraintLayout contact;
    private ConstraintLayout email;
    private ConstraintLayout sms;
    private ConstraintLayout geo;
    private ConstraintLayout phone;
    private ConstraintLayout wifi;
    private ConstraintLayout myQr;
    private ConstraintLayout calendar;

    private View rootView;

    public static CreateFragment newInstance(String result) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putString(RESULT, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clipboard = rootView.findViewById(R.id.clipboard);
        url = rootView.findViewById(R.id.url);
        wifi = rootView.findViewById(R.id.wifi);
        text = rootView.findViewById(R.id.text);
        contact = rootView.findViewById(R.id.contact);
        email = rootView.findViewById(R.id.email);
        sms = rootView.findViewById(R.id.SMS);
        geo = rootView.findViewById(R.id.GEO);
        phone = rootView.findViewById(R.id.phone);
        myQr = rootView.findViewById(R.id.card);
        calendar = rootView.findViewById(R.id.Calendar);


        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarFragment fragment = new CalendarFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack("");
                transaction.commit();
            }
        });

        clipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentFragment fragment = new ContentFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack("");
                transaction.commit();
            }
        });

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UrlFragment fragment = new UrlFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack("");
                transaction.commit();
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextFragment fragment = new TextFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack("");
                transaction.commit();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactFragment fragment = new ContactFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack("");
                transaction.commit();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmailFragment fragment = new EmailFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack("");
                transaction.commit();
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsFragment fragment = new SmsFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack("");
                transaction.commit();
            }
        });

        geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeoFragment fragment = new GeoFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack("");
                transaction.commit();
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneFragment fragment = new PhoneFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack("");
                transaction.commit();
            }
        });

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiFragment fragment = new WifiFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack("");
                transaction.commit();
            }
        });

        myQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyqrFragment fragment = new MyqrFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack("");
                transaction.commit();
            }
        });
    }


}