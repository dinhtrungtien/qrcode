package com.example.qrcode.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history")

public class History implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String type;
    private long time;
    private String result;
    private  boolean isFavorite;

    public History( String type, long time, String result) {
        this.type = type;
        this.time = time;
        this.result = result;
    }

    protected History(Parcel in) {
        id = in.readInt();
        type = in.readString();
        time = in.readLong();
        result = in.readString();
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeLong(time);
        dest.writeString(result);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
