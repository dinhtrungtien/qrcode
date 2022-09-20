package com.example.qrcode.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.qrcode.common.Constant;
import com.example.qrcode.R;
import com.example.qrcode.utils.SharedPreferencesUtil;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppCompatButton btBlue, btRed, btOrange, btYellow, btBrown, btGreen, btPurpleBlue, btLightGreen, btPink, btSilver;
    private LinearLayout lnTheme;
    private SwitchCompat aSwitch;
    private CheckBox cbBeep, cbVibrate, cbCopy;
    private View rootView;
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle("Settings");
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        btBlue = rootView.findViewById(R.id.blue);
        btRed = rootView.findViewById(R.id.red);
        btOrange = rootView.findViewById(R.id.orange);
        btYellow = rootView.findViewById(R.id.yellow);
        btBrown = rootView.findViewById(R.id.brown);
        btGreen = rootView.findViewById(R.id.green);
        btPurpleBlue = rootView.findViewById(R.id.purpleBlue);
        btLightGreen = rootView.findViewById(R.id.lightGreen);
        btPink = rootView.findViewById(R.id.pink);
        btSilver = rootView.findViewById(R.id.silver);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            requireActivity().setTheme(R.style.Theme_Dark);
        } else {
            requireActivity().setTheme(R.style.Theme_QrCode);
        }
        super.onViewCreated(view, savedInstanceState);

        cbBeep = rootView.findViewById(R.id.beep);
        cbVibrate = rootView.findViewById(R.id.Vibrate);
        cbCopy = rootView.findViewById(R.id.copy);
        aSwitch = rootView.findViewById(R.id.theme);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            aSwitch.setChecked(true);
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        cbBeep.setChecked(SharedPreferencesUtil.getInstance().get(Constant.SOUND_SCAN, Boolean.class, false));
        cbBeep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtil.getInstance().put(Constant.SOUND_SCAN, isChecked);
            }
        });

        cbVibrate.setChecked(SharedPreferencesUtil.getInstance().get(Constant.VIBRATE_SCAN, Boolean.class, false));
        cbVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtil.getInstance().put(Constant.VIBRATE_SCAN, isChecked);
            }
        });

        cbCopy.setChecked(SharedPreferencesUtil.getInstance().get(Constant.COPY_SCAN, Boolean.class, false));
        cbCopy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtil.getInstance().put(Constant.COPY_SCAN, isChecked);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}