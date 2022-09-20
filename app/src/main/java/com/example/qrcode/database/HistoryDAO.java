package com.example.qrcode.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.qrcode.model.History;

import java.util.List;

@Dao
public interface HistoryDAO {

    @Insert
    void insertHistory(History history);

    @Query("SELECT *FROM history")
    LiveData<List<History>> getListHistory();

    @Query("UPDATE history SET isFavorite = :isFavorite WHERE id =:id")
    void updateFavorite(int id, boolean isFavorite);

    @Query("SELECT *FROM history WHERE isFavorite =:favorite")
    LiveData<List<History>> getAllFavorite(boolean favorite);

    @Query("DELETE FROM history WHERE id = :id")
     void deleteHistory(int id);


}
