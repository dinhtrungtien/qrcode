package com.example.qrcode.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.core.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static final String TYPE_URL = "URI";
    public static final String TYPE_TEXT = "TEXT";
    public static final String TYPE_WIFI = "WIFI";
    public static final String TYPE_PRODUCT = "PRODUCT";
    public static final String TYPE_BARCODE = "VIN";
    public static final String TYPE_PHONE = "TEL";
    public static final String TYPE_CONTACT = "ADDRESSBOOK";
    public static final String TYPE_ISBN = "ISBN";
    public static final String TYPE_EMAIL = "EMAIL_ADDRESS";
    public static final String TYPE_SMS = "SMS";
    public static final String TYPE_GEO = "GEO";
    public static final String TYPE_CALENDAR = "CALENDAR";


    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss  dd/MM/yyyy");
        return format.format(date);
    }

    public static boolean checkPermissionForReadExtertalStorage(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = context.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public static void requestPermissionForReadExtertalStorage(Activity activity)   {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1001);
        }
    }

    private static void vibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }
}
