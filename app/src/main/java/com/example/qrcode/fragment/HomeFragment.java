package com.example.qrcode.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qrcode.common.Constant;
import com.example.qrcode.R;
import com.example.qrcode.database.DatabaseRepository;
import com.example.qrcode.model.History;
import com.example.qrcode.utils.SharedPreferencesUtil;
import com.example.qrcode.utils.Utils;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;

import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class HomeFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private ZXingScannerView xingScannerView;
    //    private Button btnZoom;
    private ImageView imgFlash;
    //    private ImageView imgCamera;
    private int cameraIndex = 0;
    private DatabaseRepository repository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        repository = new DatabaseRepository(requireActivity().getApplication());
        getActivity().setTitle("Scan");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Utils.formatDate(new Date(System.currentTimeMillis()));

        Log.d("TAG", "onCreateView: " + Utils.formatDate(new Date(System.currentTimeMillis())));
        Log.d("TAG", "onCreateView: " + System.currentTimeMillis());

        xingScannerView = view.findViewById(R.id.scan_view);
        imgFlash = view.findViewById(R.id.img_flashOn);
//        imgCamera = view.findViewById(R.id.img_camera);
//        btnZoom = view.findViewById(R.id.btnZoom);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        btnZoom.setOnClickListener(v -> setMaxZoom());
        imgFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (xingScannerView.getFlash()) {
                    xingScannerView.setFlash(false);
                    imgFlash.setImageResource(R.drawable.ic_flash_off);
                } else {
                    xingScannerView.setFlash(true);
                    imgFlash.setImageResource(R.drawable.ic_flash_on);
                }
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        xingScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        xingScannerView.startCamera(cameraIndex);          // Start camera on resume
        /**
         * Camera
         * 1: MẶt trước
         * 0: Mặt sau
         **/

    }

    private void setMaxZoom() {
//        Camera.Parameters parameters = xingScannerView.getCamera().getParameters();
//
//        int max = parameters.getMaxZoom();
//        Log.e("Maxzoom", max + "");
//        parameters.setZoom(max);

        xingScannerView.getCamera().startSmoothZoom(10);
    }

    @Override
    public void onPause() {
        super.onPause();
        xingScannerView.stopCamera();           // Stop camera on pause
    }

    public void setEnablePlash(boolean isEnable) {
        xingScannerView.setFlash(isEnable);
    }


    @RequiresApi(api = 31)
    @Override
    public void handleResult(Result rawResult) {

        ParsedResult parsedResult = ResultParser.parseResult(rawResult);

        if (SharedPreferencesUtil.getInstance().get(Constant.SOUND_SCAN, Boolean.class, false)) {
            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
        }

        if (SharedPreferencesUtil.getInstance().get(Constant.VIBRATE_SCAN, Boolean.class, false)) {
            Vibrator v = (Vibrator) requireActivity().getSystemService(Context.VIBRATOR_SERVICE);
            Log.d("TAG", "handleResult: " + v);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(500);
            }
        }

        if (SharedPreferencesUtil.getInstance().get(Constant.COPY_SCAN, Boolean.class, false)) {

            Toast.makeText(getContext(), "Copy", Toast.LENGTH_SHORT).show();

            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("copy", rawResult.getText());
            clipboard.setPrimaryClip(clip);
        }


        History history = new History(parsedResult.getType().name(), System.currentTimeMillis(),parsedResult.getDisplayResult());
        repository.addHistory(history);
        FragmentManager manager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, ResultFragment.newInstance(history));
        transaction.addToBackStack("");
        transaction.commit();


    }

    private Object getSystemService(String vibratorService) {
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}