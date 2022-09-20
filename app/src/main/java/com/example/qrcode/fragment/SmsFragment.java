package com.example.qrcode.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qrcode.R;

public class SmsFragment extends Fragment {

    private Button btnExport;
    private View rootView;
    private EditText edtPhone;
    private EditText edtMessage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle("My QR");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sms, container, false);

        btnExport = rootView.findViewById(R.id.bt_exportSms);
        edtPhone = rootView.findViewById(R.id.edt_phone);
        edtMessage = rootView.findViewById(R.id.edt_message);


        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtPhone.getText().toString();
                String message = edtMessage.getText().toString();

                String result = phone + "\n" + message;
                ResultSmsFragment fragment = ResultSmsFragment.newInstance(result);

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