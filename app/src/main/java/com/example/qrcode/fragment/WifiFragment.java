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

public class WifiFragment extends Fragment {

    private Button btnExport;
    private View rootView;
    private EditText edtName;
    private EditText edtPassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle("My QR");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_wifi, container, false);

        btnExport = rootView.findViewById(R.id.bt_exportWifi);
        edtName = rootView.findViewById(R.id.edt_network);
        edtPassword = rootView.findViewById(R.id.edt_pass);


        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String password = edtPassword.getText().toString();

                String result = name + "\n" + password;
                ResultWifiFragment fragment = ResultWifiFragment.newInstance(result);

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