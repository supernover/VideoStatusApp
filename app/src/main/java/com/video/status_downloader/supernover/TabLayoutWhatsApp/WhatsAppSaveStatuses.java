package com.video.status_downloader.supernover.TabLayoutWhatsApp;


import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.video.status_downloader.supernover.Adaptor.WhatsAppSavedStatusesAdaptor;
import com.video.status_downloader.supernover.Models.ModelStatus;
import com.video.status_downloader.supernover.R;
import com.video.status_downloader.supernover.Utills.Config;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WhatsAppSaveStatuses extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ArrayList<ModelStatus> data;
    RecyclerView rv;
    TextView textView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public WhatsAppSaveStatuses() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_whats_app_save_pictures, container, false);
        rv = view.findViewById(R.id.rv_status);
        mSwipeRefreshLayout = view.findViewById(R.id.contentView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        textView = view.findViewById(R.id.textView);

        rv.setHasFixedSize(true);

        loadData();

        return view;
    }

    public void loadData() {
        data = new ArrayList<>();
        final String path = Config.WhatsAppSaveStatus;
        File directory = new File(path);
        if (directory.exists()) {
            final File[] files = directory.listFiles();
            Log.d("Files", "Size: " + files.length);
            final String[] paths = {""};

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    for (int i = 0; i < files.length; i++) {
                        Log.d("Files", "FileName:" + files[i].getName());
                        Log.d("Files", "FileName:" + files[i].getName().substring(0, files[i].getName().length() - 4));
                        if (files[i].getName().endsWith(".jpg") || files[i].getName().endsWith("gif") || files[i].getName().endsWith(".mp4")) {
                            paths[0] = path + "" + files[i].getName();
                            ModelStatus modelStatus = new ModelStatus(paths[0], files[i].getName().substring(0, files[i].getName().length() - 4), 0);
                            data.add(modelStatus);
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if (!(data.toArray().length > 0)) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("No Status Available \n Check Out some Status & come back again...");
                    }
                    WhatsAppSavedStatusesAdaptor adapter = new WhatsAppSavedStatusesAdaptor(getActivity(), data);
                    rv.setAdapter(adapter);

                    LinearLayoutManager llm = new GridLayoutManager(getActivity(), 2);
                    rv.setLayoutManager(llm);
                }
            }.execute();


        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText("No Status Available \n Check Out some Status & come back again...");
/*
            Snackbar.make(getActivity().findViewById(android.R.id.content), "WhatsApp Not Installed",
                    Snackbar.LENGTH_SHORT).show();*/
        }
        refreshItems();
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    public void refreshItems() {
        onItemsLoadComplete();
    }

    public void onItemsLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
