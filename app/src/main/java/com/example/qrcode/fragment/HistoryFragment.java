package com.example.qrcode.fragment;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.List;

public class HistoryFragment extends Fragment implements HistoryAdapter.ItemClickListener {


    private RecyclerView rcvHistory;

    private HistoryAdapter historyAdapter;
    private List<History> mListHistory;
    private DatabaseRepository repository;
    private View rootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle("History");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_history, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rcvHistory = rootView.findViewById(R.id.rcv_history);
        repository = new DatabaseRepository(requireActivity().getApplication());
        historyAdapter = new HistoryAdapter();

        repository.getAllHistory().observe(getViewLifecycleOwner(), new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                historyAdapter.setData(histories, requireContext(), repository, HistoryFragment.this);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvHistory.setLayoutManager(linearLayoutManager);
        rcvHistory.setAdapter(historyAdapter);

    }

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

                        showMenuDelete(history);

                        break;
                    case R.id.share:

                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,history.getResult() );
                        sendIntent.setType("text/plain");
                        Intent shareIntent = Intent.createChooser(sendIntent, "Share QR code");
                        requireActivity().startActivity(shareIntent);
                        break;
                }
                return false;
            }
        });
        //displaying the popup
        popup.show();
    }

    private void showMenuDelete(History history){


        repository.deleteHistory(history.getId());
    }

}
