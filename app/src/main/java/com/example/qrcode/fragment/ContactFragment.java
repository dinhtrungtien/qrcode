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

public class ContactFragment extends Fragment {

    private Button btnExport;
    private View rootView;
    private EditText edtName;
    private EditText edtOrganization;
    private EditText edtAddress;
    private EditText edtPhone;
    private EditText edtEmail;
    private EditText edtNote;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle("My QR");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_contact, container, false);

        btnExport = rootView.findViewById(R.id.exportContact);
        edtName = rootView.findViewById(R.id.edt_name);
        edtOrganization = rootView.findViewById(R.id.edt_organization);
        edtAddress = rootView.findViewById(R.id.edt_address);
        edtPhone = rootView.findViewById(R.id.edt_phone);
        edtEmail = rootView.findViewById(R.id.edt_email);
        edtNote = rootView.findViewById(R.id.edt_note);


        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String org = edtOrganization.getText().toString();
                String address = edtAddress.getText().toString();
                String phone = edtPhone.getText().toString();
                String email = edtEmail.getText().toString();
                String note = edtNote.getText().toString();

                String result = name + "\n" + org + "\n" + address + "\n" + phone + "\n" + email + "\n" + note;
                ResultMyQrFragment fragment = ResultMyQrFragment.newInstance(result);

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