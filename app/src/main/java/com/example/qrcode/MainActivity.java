package com.example.qrcode;

import static androidx.core.view.GravityCompat.START;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qrcode.database.DatabaseRepository;
import com.example.qrcode.fragment.CreateFragment;
import com.example.qrcode.fragment.FavoritesFragment;
import com.example.qrcode.fragment.HistoryFragment;
import com.example.qrcode.fragment.HomeFragment;
import com.example.qrcode.fragment.MyqrFragment;
import com.example.qrcode.fragment.ResultFragment;
import com.example.qrcode.fragment.SettingsFragment;
import com.example.qrcode.model.History;
import com.example.qrcode.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_SCANIMAGE = 1;
    private static final int FRAGMENT_FAVORITES = 2;
    private static final int FRAGMENT_HISTORY = 3;
    private static final int FRAGMENT_MYQR = 4;
    private static final int FRAGMENT_CREATE = 5;
    private static final int FRAGMENT_SETTINGS = 6;
    private static final int RESULT_LOAD_IMG = 999;
    TextView ketQua;
    private int mCurrentFragment = FRAGMENT_HOME;
    private DrawerLayout mDrawerLayout;
    private DatabaseRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        repository = new DatabaseRepository(getApplication());


        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.getHeaderView(0).findViewById(R.id.layout_scan).setOnClickListener(v -> {
            mDrawerLayout.close();
            if (mCurrentFragment != FRAGMENT_HOME) {
                replaceFragment(new HomeFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }
        });

        navigationView.getHeaderView(0).findViewById(R.id.layout_scanImage).setOnClickListener(v -> {
            mDrawerLayout.close();
            if (!Utils.checkPermissionForReadExtertalStorage(this)) {
                Utils.requestPermissionForReadExtertalStorage(this);
            } else {
//                Intent photoPickerIntent = new Intent();
//                photoPickerIntent.setType("image/*");
//                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_PICK);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMG);

                pickFromGallery();
                if (mCurrentFragment != FRAGMENT_SCANIMAGE) {
                    replaceFragment(new HomeFragment());
                    mCurrentFragment = FRAGMENT_SCANIMAGE;
                }
            }

        });

        navigationView.getHeaderView(0).findViewById(R.id.layout_favorites).setOnClickListener(v -> {
            mDrawerLayout.close();
            if (mCurrentFragment != FRAGMENT_FAVORITES) {
                replaceFragment(new FavoritesFragment());
                mCurrentFragment = FRAGMENT_FAVORITES;
            }
        });

        navigationView.getHeaderView(0).findViewById(R.id.layout_history).setOnClickListener(v -> {
            mDrawerLayout.close();
            if (mCurrentFragment != FRAGMENT_HISTORY) {
                replaceFragment(new HistoryFragment());
                mCurrentFragment = FRAGMENT_HISTORY;
            }
        });

        navigationView.getHeaderView(0).findViewById(R.id.layout_myQr).setOnClickListener(v -> {
            mDrawerLayout.close();
            if (mCurrentFragment != FRAGMENT_MYQR) {
                replaceFragment(new MyqrFragment());
                mCurrentFragment = FRAGMENT_MYQR;
            }
        });

        navigationView.getHeaderView(0).findViewById(R.id.layout_createQr).setOnClickListener(v -> {
            mDrawerLayout.close();
            if (mCurrentFragment != FRAGMENT_CREATE) {
                replaceFragment(new CreateFragment());
                mCurrentFragment = FRAGMENT_CREATE;
            }
        });

        navigationView.getHeaderView(0).findViewById(R.id.layout_settings).setOnClickListener(v -> {
            mDrawerLayout.close();
            if (mCurrentFragment != FRAGMENT_SETTINGS) {
                replaceFragment(new SettingsFragment());
                mCurrentFragment = FRAGMENT_SETTINGS;
            }
        });

        navigationView.getHeaderView(0).findViewById(R.id.layout_share).setOnClickListener(v -> {
            mDrawerLayout.close();
            Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "QR code");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, "Share qr code");
            startActivity(shareIntent);

        });


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, new HomeFragment());
        transaction.commit();

    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/*")
                .addCategory(Intent.CATEGORY_OPENABLE);
        List<String> mimeTypes = Arrays.asList("image/jpeg", "image/png");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, String.valueOf(mimeTypes));
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        getString(R.string.app_name)
                ), RESULT_LOAD_IMG
        );
    }

    //back = nút back trên ddt
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(START)) {
            mDrawerLayout.closeDrawer(START);
        } else {
            super.onBackPressed();
        }
    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Request code", requestCode + " resultCode:" + resultCode);
        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMG) {
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                Result result = parseInfoFromBitmap(selectedImage);
                handleResult(result);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "You haven't picked Image " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    private void handleResult(Result rawResult) {
        if (rawResult != null) {
            if (rawResult.getText().startsWith("http://") || rawResult.getText().startsWith("https://") || rawResult.getText().startsWith("www.")) {
                History history = new History(Utils.TYPE_URL, System.currentTimeMillis(), rawResult.getText());
                repository.addHistory(history);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, ResultFragment.newInstance(history));
                transaction.addToBackStack("");
                transaction.commit();

            } else {
                History history = new History(Utils.TYPE_TEXT, System.currentTimeMillis(), rawResult.getText());
                repository.addHistory(history);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, ResultFragment.newInstance(history));
                transaction.addToBackStack("");
                transaction.commit();
            }

        } else {
            Toast.makeText(this, "error photo", Toast.LENGTH_LONG).show();
        }

    }

    public Result parseInfoFromBitmap(Bitmap bitmap) {
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        RGBLuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(),
                bitmap.getHeight(), pixels);
        GlobalHistogramBinarizer binarizer = new GlobalHistogramBinarizer(source);
        BinaryBitmap image = new BinaryBitmap(binarizer);
        Result result;
        try {
            result = new QRCodeReader().decode(image);
            return result;
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}
