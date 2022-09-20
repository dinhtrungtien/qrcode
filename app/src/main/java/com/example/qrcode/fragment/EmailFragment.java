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

public class EmailFragment extends Fragment {

    private Button btnExport;
    private View rootView;
    private EditText edtEmail;
    private EditText edtSubject;
    private EditText edtBody;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle("My QR");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_email, container, false);

        btnExport = rootView.findViewById(R.id.bt_export);
        edtEmail = rootView.findViewById(R.id.edt_email);
        edtSubject = rootView.findViewById(R.id.edt_subject);
        edtBody = rootView.findViewById(R.id.edt_body);

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String subject = edtSubject.getText().toString();
                String body = edtBody.getText().toString();

                String result = email + "\n" + subject + "\n" + body;
                ResultEmailFragment fragment = ResultEmailFragment.newInstance(result);

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