package com.example.qrcode.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.qrcode.R;
import com.example.qrcode.model.History;
import com.example.qrcode.utils.Utils;

import java.util.Calendar;
import java.util.Date;


public class ResultFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private View rootView;
    private TextView tvResult;
    private TextView tvTypeQR;
    private ImageView imgTypeQR, imgFavorites;
    private TextView tvTime;
    private ConstraintLayout ctlSearch, ctlShare, ctlCopy;
    private ConstraintLayout ctlCall;
    private ConstraintLayout ctlAddContact;
    private ConstraintLayout ctlOpen;
    private ConstraintLayout ctlShowMap;
    private ConstraintLayout ctlSendEmail;
    private ConstraintLayout ctlSendSms;
    private ConstraintLayout ctlSendMms;
    private ConstraintLayout ctlGetDirections;
    private ConstraintLayout ctlAddEvent;
    private ConstraintLayout ctlConnect;
    private ConstraintLayout ctlCopyPass;


    private History result;

    public ResultFragment() {

    }

    public static ResultFragment newInstance(History history) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, history);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle("Result");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            result = getArguments().getParcelable(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_result, container, false);
        tvResult = rootView.findViewById(R.id.tv_ketqua);
        tvTypeQR = rootView.findViewById(R.id.tv_dangqr);
        imgTypeQR = rootView.findViewById(R.id.img_dangqr);
//        imgFavorites = rootView.findViewById(R.id.img_favorites);
        tvTime = rootView.findViewById(R.id.tv_time);

        if (result.getType() == Utils.TYPE_URL) {
            imgTypeQR.setImageResource(R.drawable.ic_url);
            tvTypeQR.setText("URL");
        } else if (result.getType() == Utils.TYPE_TEXT) {
            imgTypeQR.setImageResource(R.drawable.ic_text);
            tvTypeQR.setText("TEXT");
        } else if (result.getType() == Utils.TYPE_WIFI) {
            imgTypeQR.setImageResource(R.drawable.ic_wifi);
            tvTypeQR.setText("WIFI");
        } else if (result.getType() == Utils.TYPE_PRODUCT) {
            imgTypeQR.setImageResource(R.drawable.ic_product);
            tvTypeQR.setText("PRODUCT");
        } else if (result.getType() == Utils.TYPE_BARCODE) {
            imgTypeQR.setImageResource(R.drawable.ic_vin);
            tvTypeQR.setText("VIN");
        } else if (result.getType() == Utils.TYPE_PHONE) {
            imgTypeQR.setImageResource(R.drawable.ic_phone);
            tvTypeQR.setText("PHONE");
        } else if (result.getType() == Utils.TYPE_ISBN) {
            imgTypeQR.setImageResource(R.drawable.ic_isbn);
            tvTypeQR.setText("ISBN");
        } else if (result.getType() == Utils.TYPE_EMAIL) {
            imgTypeQR.setImageResource(R.drawable.ic_mail);
            tvTypeQR.setText("EMAIL");
        } else if (result.getType() == Utils.TYPE_SMS) {
            imgTypeQR.setImageResource(R.drawable.ic_sms);
            tvTypeQR.setText("SMS");
        } else if (result.getType() == Utils.TYPE_GEO) {
            imgTypeQR.setImageResource(R.drawable.ic_location);
            tvTypeQR.setText("GEO");
        } else if (result.getType() == Utils.TYPE_CALENDAR) {
            imgTypeQR.setImageResource(R.drawable.ic_calendar);
            tvTypeQR.setText("CALENDAR");
        } else if (result.getType() == Utils.TYPE_CONTACT) {
            imgTypeQR.setImageResource(R.drawable.ic_my);
            tvTypeQR.setText("CONTACT");
        }

        tvResult.setText(result.getResult());
        tvTime.setText(Utils.formatDate(new Date(result.getTime())));


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ctlSearch = rootView.findViewById(R.id.search);
        ctlShare = rootView.findViewById(R.id.share);
        ctlCopy = rootView.findViewById(R.id.copy);
        ctlCall = rootView.findViewById(R.id.call);
        ctlAddContact = rootView.findViewById(R.id.addContact);
        ctlOpen = rootView.findViewById(R.id.open);
        ctlShowMap = rootView.findViewById(R.id.ShowMap);
        ctlSendEmail = rootView.findViewById(R.id.SendEmail);
        ctlSendSms = rootView.findViewById(R.id.SendSms);
        ctlSendMms = rootView.findViewById(R.id.SendMms);
        ctlGetDirections = rootView.findViewById(R.id.getDirections);
        ctlAddEvent = rootView.findViewById(R.id.addEvent);
        ctlConnect = rootView.findViewById(R.id.connect);
        ctlCopyPass = rootView.findViewById(R.id.copyPass);

        ctlAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = result.getResult().split("\n")[0];
                String beginTime = result.getResult().split("\n")[1];
                String endTime = result.getResult().split("\n")[2];
                String location = result.getResult().split("\n")[3];
                String description = result.getResult().split("\n")[4];

//                Calendar cal = Calendar.getInstance();
//                Intent intent = new Intent(Intent.ACTION_EDIT);
//                intent.setType("vnd.android.cursor.item/event");
//                intent.putExtra("title", title);
//                intent.putExtra("allDay", false);
//                intent.putExtra("beginTime", beginTime);
//                intent.putExtra("endTime", endTime);
//                intent.putExtra("rrule", "FREQ=YEARLY");
//                startActivity(intent);

                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("allDay", true);
                intent.putExtra("rrule", "FREQ=YEARLY");
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra(Intent.EXTRA_EMAIL, "");
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
                startActivity(intent);
            }
        });

        ctlConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                WifiManager wifiManager = (WifiManager) requireContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
//                for( WifiConfiguration i : list ) {
//                    if(i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
//                        wifiManager.disconnect();
//                        wifiManager.enableNetwork(i.networkId, true);
//                        wifiManager.reconnect();
//
//                        break;
//                    }
//                }

            }
        });

        ctlShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String label = String.format("%s", result.getResult());
//                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(label));
//
//                String geoUri = "http://maps.google.com/maps?q=loc:" + 23 + "," + 108 + " (" + "ha noi" + ")";
//
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
//                mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                requireContext().startActivity(mapIntent);
                Log.d("TAG", "onClick: " + result.getResult());

                String location = result.getResult().split(",")[0];

                String url = "https://www.google.com/maps/search/?api=1&query=" + "";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        ctlSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Intent email = new Intent(Intent.ACTION_SEND);
//                email.putExtra(Intent.EXTRA_EMAIL, new String[]{result.getResult()});
//                email.putExtra(Intent.EXTRA_SUBJECT, "");
//                email.putExtra(Intent.EXTRA_TEXT, "");

//                email.setType("message/rfc822");
//                startActivity(Intent.createChooser(email, "Choose an Email client :"));

                String email = result.getResult().split("\n")[0];
                String title = result.getResult().split("\n")[1];
                String content = result.getResult().split("\n")[2];


                Intent testIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + title
                        + "&body=" + content
                        + "&to=" + email);
                testIntent.setData(data);
                try {
                    startActivity(testIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ctlAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
                contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                contactIntent
                        .putExtra(ContactsContract.Intents.Insert.NAME, "")
                        .putExtra(ContactsContract.Intents.Insert.PHONE, "");

                startActivityForResult(contactIntent, 1);
            }
        });

        ctlSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String sms = result.getResult().substring(result.getResult().indexOf('\n') + 1);
                String phone = result.getResult().split("\n")[0];
                String sms = result.getResult().replace(phone, "");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
                intent.putExtra("sms_body", sms);
                startActivity(intent);
            }
        });

        ctlSendMms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String sms = result.getResult().substring(result.getResult().indexOf('\n') + 1);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mms:" + result.getResult()));
                intent.putExtra("mms_body", result.getResult());
                startActivity(intent);
            }
        });

        ctlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG24", "onClick: " + result.getResult());
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + result.getResult()));
                startActivity(callIntent);
            }
        });

        ctlOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result.getType() == Utils.TYPE_URL) {
                    String url = result.getResult();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } else if (result.getType() == Utils.TYPE_TEXT) {
                    String query = result.getResult();
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    intent.setClassName("com.google.android.googlequicksearchbox", "com.google.android.googlequicksearchbox.SearchActivity");
                    intent.putExtra("query", query);
                    startActivity(intent);
                }
            }
        });

        ctlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result.getType() == Utils.TYPE_URL) {
                    String url = result.getResult();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } else if (result.getType() == Utils.TYPE_TEXT) {
                    String query = result.getResult();
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    intent.setClassName("com.google.android.googlequicksearchbox", "com.google.android.googlequicksearchbox.SearchActivity");
                    intent.putExtra("query", query);
                    startActivity(intent);
                }
            }
        });


        ctlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, result.getResult());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, "Share qr code");
                requireActivity().startActivity(shareIntent);
            }
        });

        ctlCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Copy", Toast.LENGTH_SHORT).show();

                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copy", tvResult.getText());
                clipboard.setPrimaryClip(clip);
            }
        });
    }


}