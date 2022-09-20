package com.example.qrcode.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.qrcode.model.History;

import java.util.List;

public class DatabaseRepository {


    private HistoryDatabase appHistoryDatabase;
    //    private FavoriteDataBase favoriteDataBase;
    private HistoryDAO historyDAO;
    //    private FavoriteDAO favoriteDAO;
    private LiveData<List<History>> allHistory;
    private LiveData<List<History>> allFavorite;


    public DatabaseRepository(Application application) {
        appHistoryDatabase = HistoryDatabase.getInstance(application);
//        favoriteDataBase = FavoriteDataBase.getInstance(application);
        historyDAO = appHistoryDatabase.historyDAO();
//        favoriteDAO = favoriteDataBase.favoriteDAO();
        allHistory = historyDAO.getListHistory();
        allFavorite = historyDAO.getAllFavorite(true);

    }

    public void addHistory(History history) {
        historyDAO.insertHistory(history);
    }

    public void deleteHistory(int id) {
        historyDAO.deleteHistory(id);
    }

    public LiveData<List<History>> getAllHistory() {
        return allHistory;
    }

    public void updateFavoriteHistory(int id, boolean isFavorite) {
        historyDAO.updateFavorite(id, isFavorite);
    }

    public LiveData<List<History>> getAllFavorite() {
        return allFavorite;
    }

}
