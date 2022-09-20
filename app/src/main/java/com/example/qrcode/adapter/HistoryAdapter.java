package com.example.qrcode.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrcode.R;
import com.example.qrcode.database.DatabaseRepository;
import com.example.qrcode.model.History;
import com.example.qrcode.utils.Utils;

import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<History> mListHistory;
    private DatabaseRepository repository;
    private Context mCtx;
    private ItemClickListener mItemListener;

    public void setData(List<History> list, Context context, DatabaseRepository repository, ItemClickListener itemClickListener) {
        this.mListHistory = list;
        this.repository = repository;
        this.mCtx = context;
        this.mItemListener = itemClickListener;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        History history = mListHistory.get(position);
        Log.d("TAG", "onBindViewHolder: " + history.getId());
        if (history == null) {
            return;
        }

        int thumbnail = -1;
        if (history.getType() == Utils.TYPE_URL) {
            thumbnail = R.drawable.ic_url;
            holder.tvDangqrH.setText("URL");
        } else if (history.getType() == Utils.TYPE_TEXT) {
            thumbnail = R.drawable.ic_text;
            holder.tvDangqrH.setText("TEXT");
        } else if (history.getType() == Utils.TYPE_WIFI) {
            thumbnail = R.drawable.ic_wifi;
            holder.tvDangqrH.setText("Wifi");
        } else if (history.getType() == Utils.TYPE_PRODUCT) {
            thumbnail = R.drawable.ic_product;
            holder.tvDangqrH.setText("Product");
        } else if (history.getType() == Utils.TYPE_BARCODE) {
            thumbnail = R.drawable.ic_vin;
            holder.tvDangqrH.setText("Vin");
        } else if (history.getType() == Utils.TYPE_PHONE) {
            thumbnail = R.drawable.ic_phone;
            holder.tvDangqrH.setText("Phone");
        } else if (history.getType() == Utils.TYPE_CONTACT) {
            thumbnail = R.drawable.ic_my;
            holder.tvDangqrH.setText("Contact");
        } else if (history.getType() == Utils.TYPE_ISBN) {
            thumbnail = R.drawable.ic_isbn;
            holder.tvDangqrH.setText("ISBN");
        } else if (history.getType() == Utils.TYPE_EMAIL) {
            thumbnail = R.drawable.ic_mail;
            holder.tvDangqrH.setText("EMAIL");
        } else if (history.getType() == Utils.TYPE_SMS) {
            thumbnail = R.drawable.ic_sms;
            holder.tvDangqrH.setText("SMS");
        } else if (history.getType() == Utils.TYPE_GEO) {
            thumbnail = R.drawable.ic_location;
            holder.tvDangqrH.setText("GEO");
        } else if (history.getType() == Utils.TYPE_CALENDAR) {
            thumbnail = R.drawable.ic_calendar;
            holder.tvDangqrH.setText("CALENDAR");
        }

        holder.imgDangQrH.setImageResource(thumbnail);
        holder.tvTimeH.setText(Utils.formatDate(new Date(history.getTime())));
        holder.tvKetQuaQRH.setText(history.getResult());
        if (history.isFavorite()) {
            holder.imgFavoritesH.setImageResource(R.drawable.ic_removefavorites);
        } else {
            holder.imgFavoritesH.setImageResource(R.drawable.ic_addfavorites);
        }
        holder.imgFavoritesH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (history.isFavorite()) {
                    repository.updateFavoriteHistory(history.getId(), false);

                } else {
                    repository.updateFavoriteHistory(history.getId(), true);

                }

            }
        });
        holder.imgTuyChonH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mItemListener.onMenuClick(v, history);

                //creating a popup menu

            }
        });
        holder.itemView.setOnClickListener(View -> {

            mItemListener.onItemClick(history);

        });

    }

    @Override
    public int getItemCount() {
        if (mListHistory != null) {
            return mListHistory.size();
        }
        return 0;
    }


    public interface ItemClickListener {
        void onItemClick(History history);

        void onMenuClick(View view, History history);
    }


    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgDangQrH, imgFavoritesH, imgTuyChonH;
        private TextView tvTimeH, tvKetQuaQRH, tvDangqrH;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            imgDangQrH = itemView.findViewById(R.id.img_dangqrH);
            imgFavoritesH = itemView.findViewById(R.id.img_favoritesH);
            tvTimeH = itemView.findViewById(R.id.tv_timeH);
            tvKetQuaQRH = itemView.findViewById(R.id.tv_ketquaqrH);
            imgTuyChonH = itemView.findViewById(R.id.img_tuychonH);
            tvDangqrH = itemView.findViewById(R.id.tv_dangqrH);
        }
    }
}
