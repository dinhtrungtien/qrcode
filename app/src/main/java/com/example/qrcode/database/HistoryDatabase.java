package com.example.qrcode.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.qrcode.model.History;

@androidx.room.Database(entities = {History.class}, version = 1)
public abstract class HistoryDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "history.db";
    private static HistoryDatabase instance;

    public static synchronized HistoryDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), HistoryDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract HistoryDAO historyDAO();
}
