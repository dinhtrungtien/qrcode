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


public class ResultMyQrFragment extends Fragment {
    private static final String MY_QR = "my_qr";

    private TextView tvExport;
    private View rootView;
    private String result;
    private LinearLayout lnCopy;
    private LinearLayout lnShare;
    private ImageView imgExport;
    private QRGEncoder qrgEncoder;
    private Bitmap bitmap;


    public static ResultMyQrFragment newInstance(String result) {
        ResultMyQrFragment fragment = new ResultMyQrFragment();
        Bundle args = new Bundle();
        args.putString(MY_QR, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_result_my_qr, container, false);

        if (getArguments() != null) {
            result = getArguments().getString(MY_QR);
        }

        tvExport = rootView.findViewById(R.id.tv_export);
        imgExport = rootView.findViewById(R.id.img_export);

        WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);

        // khởi tạo 1 biến để hiển thị mặc định
        Display display = manager.getDefaultDisplay();

        // tạo 1 biến cho điểm sẽ được hiển thị trong mã QR
        Point point = new Point();
        display.getSize(point);

        // nhận được chiều rộng và chiều cao của 1 diểm
        int width = point.x;
        int height = point.y;

        // tạo kích thước từ chiều rộng và chiều cao
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        // đặt thứ nguyên này bên trong mã qr của chúng tôi bộ mã hóa để tạo mã qr của chúng tôi.
        qrgEncoder = new QRGEncoder(result, null, QRGContents.Type.TEXT, dimen);
        try {
            // nhận qrcode của chúng tôi ở dạng bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();
            // bitmap được đặt bên trong hình ảnh của chúng tôi xem bằng phương thức .setimagebitmap.
            imgExport.setImageBitmap(bitmap);
        } catch (WriterException e) {
            // phương pháp này được gọi cho xử lý ngoại lệ.
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