package com.example.qrcode.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrcode.R;
import com.example.qrcode.adapter.HistoryAdapter;
import com.example.qrcode.database.DatabaseRepository;
import com.example.qrcode.model.History;

import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment implements HistoryAdapter.ItemClickListener {

    private RecyclerView rcvFavorites;
    private List<History> mListFavorites;
    private HistoryAdapter favoritesAdapter;
    private DatabaseRepository repository;


    public FavoritesFragment() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle("Favorites");
        super.onCreate(savedInstanceState);

        repository = new DatabaseRepository(requireActivity().getApplication());
        mListFavorites = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvFavorites = view.findViewById(R.id.rcv_favorites);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvFavorites.setLayoutManager(linearLayoutManager);
        favoritesAdapter= new HistoryAdapter();
        rcvFavorites.setAdapter(favoritesAdapter);

        repository.getAllFavorite().observe(getViewLifecycleOwner(), new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                favoritesAdapter.setData(histories, requireContext(),repository, FavoritesFragment.this);
            }

        });

    }

    @Override
    public void onItemClick(History history) {
        ResultFragment fragment = ResultFragment.newInstance(history);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack("");
        transaction.commit();
    }

    @Override
    public void onMenuClick(View view, History history) {
        PopupMenu popup = new PopupMenu(requireContext(), view);
        //inflating menu from xml resource
        popup.inflate(R.menu.options_menu);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:

                        repository.deleteHistory(history.getId());

                        break;
                    case R.id.share:

                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,history.getResult() );
                        sendIntent.setType("text/plain");
                        Intent shareIntent = Intent.createChooser(sendIntent, "Share QR code");
                        requireActivity().startActivity(shareIntent);
                        break;

                    case R.id. create:

//                        CreateFragment fragment = CreateFragment.newInstance();
//                        FragmentManager manager = getFragmentManager();
//                        FragmentTransaction transaction = manager.beginTransaction();
//                        transaction.replace(R.id.content_frame, fragment);
//                        transaction.addToBackStack("");
//                        transaction.commit();
                }
                return false;
            }
        });
        //displaying the popup
        popup.show();
    }
}