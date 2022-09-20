package com.example.qrcode.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qrcode.R;

import androidmads.library.qrgenearator.QRGEncoder;


public class ContentFragment extends Fragment {
    private EditText edtText;
    private View rootView;
    private Button btnExport;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_content, container, false);

        edtText = rootView.findViewById(R.id.edt_text);
        btnExport = rootView.findViewById(R.id.exportContent);

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = edtText.getText().toString();

                String result = text;
                ResultTextFragment fragment = ResultTextFragment.newInstance(result);

                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack("");
                transaction.commit();

            }
        });

        return rootView;
    }
}