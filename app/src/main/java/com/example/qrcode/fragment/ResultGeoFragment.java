package com.example.qrcode.fragment;

import static android.content.Context.WINDOW_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qrcode.R;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class ResultGeoFragment extends Fragment {
    private static final String GEO = "geo";

    private TextView tvExport;
    private View rootView;
    private String result;
    private LinearLayout lnCopy;
    private LinearLayout lnShare;
    private ImageView imgExport;
    private QRGEncoder qrgEncoder;
    private Bitmap bitmap;


    public static ResultGeoFragment newInstance(String result) {
        ResultGeoFragment fragment = new ResultGeoFragment();
        Bundle args = new Bundle();
        args.putString(GEO, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_result_geo, container, false);

        if (getArguments() != null) {
            result = getArguments().getString(GEO);
        }

        tvExport = rootView.findViewById(R.id.tv_exportGeo);
        imgExport = rootView.findViewById(R.id.img_exportGeo);

        WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);

        // kh???i t???o 1 bi???n ????? hi???n th??? m???c ?????nh
        Display display = manager.getDefaultDisplay();

        // t???o 1 bi???n cho ??i???m s??? ???????c hi???n th??? trong m?? QR
        Point point = new Point();
        display.getSize(point);

        // nh???n ???????c chi???u r???ng v?? chi???u cao c???a 1 di???m
        int width = point.x;
        int height = point.y;

        // t???o k??ch th?????c t??? chi???u r???ng v?? chi???u cao
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        // ?????t th??? nguy??n n??y b??n trong m?? qr c???a ch??ng t??i b??? m?? h??a ????? t???o m?? qr c???a ch??ng t??i.
        qrgEncoder = new QRGEncoder(result, null, QRGContents.Type.TEXT, dimen);
        try {
            // nh???n qrcode c???a ch??ng t??i ??? d???ng bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();
            // bitmap ???????c ?????t b??n trong h??nh ???nh c???a ch??ng t??i xem b???ng ph????ng th???c .setimagebitmap.
            imgExport.setImageBitmap(bitmap);
        } catch (WriterException e) {
            // ph????ng ph??p n??y ???????c g???i cho x??? l?? ngo???i l???.
            Log.e("Tag", e.toString());
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lnCopy = rootView.findViewById(R.id.lnCopy);
        lnShare = rootView.findViewById(R.id.lnShare);

        lnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Copy", Toast.LENGTH_SHORT).show();

                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copy", tvExport.getText());
                clipboard.setPrimaryClip(clip);
            }
        });

        lnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, result);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, "Share qr code");
                requireActivity().startActivity(shareIntent);
            }
        });

        tvExport.setText(result);


    }
}