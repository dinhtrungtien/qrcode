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

public class GeoFragment extends Fragment {

    private Button btnExport;
    private View rootView;
    private EditText edtLatitude;
    private EditText edtLongitude;
    private EditText edtQuery;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle("My QR");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_geo, container, false);

        btnExport = rootView.findViewById(R.id.bt_exportGeo);
        edtLatitude = rootView.findViewById(R.id.edt_latitude);
        edtLongitude = rootView.findViewById(R.id.edt_longitude);
        edtQuery = rootView.findViewById(R.id.edt_query);


        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = edtLatitude.getText().toString();
                String longitude = edtLongitude.getText().toString();
                String query = edtQuery.getText().toString();

                String result = latitude + "\n" + longitude + "\n" + query;
                ResultGeoFragment fragment = ResultGeoFragment.newInstance(result);

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