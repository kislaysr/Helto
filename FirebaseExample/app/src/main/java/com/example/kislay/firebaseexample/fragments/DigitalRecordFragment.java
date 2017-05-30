package com.example.kislay.firebaseexample.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kislay.firebaseexample.R;
import com.example.kislay.firebaseexample.adapters.DigitalRecordAdapter;
import com.example.kislay.firebaseexample.adapters.RealmDigitalRecordAdapter;
import com.example.kislay.firebaseexample.app.Prefs;
import com.example.kislay.firebaseexample.camera.CameraActivity;
import com.example.kislay.firebaseexample.models.DigitalRecord;
import com.example.kislay.firebaseexample.realm.RealmController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sandrios.sandriosCamera.internal.SandriosCamera;
import com.sandrios.sandriosCamera.internal.configuration.CameraConfiguration;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;



/**
 * A simple {@link Fragment} subclass.
 */
public class DigitalRecordFragment extends Fragment {
    private static final int CAPTURE_MEDIA = 368;
    private DigitalRecordAdapter adapter;
    private Realm realm;
    private Activity activity;
    private RecyclerView recycler;
    private Button b;
    public DigitalRecordFragment() {
        // Required empty public constructor

    }


    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_digital_record, container, false);

        recycler = (RecyclerView)v.findViewById(R.id.digitalRecordList);
        this.realm = RealmController.with(this).getRealm();
        setupRecycler();
        b = (Button)v.findViewById(R.id.capture);

//        if (!Prefs.with(getContext()).getPreLoad()) {
//            setRealmData();
//        }

        // refresh the realm instance
        RealmController.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically
        setRealmAdapter(RealmController.with(this).getRecords());
        // realm ends
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),CameraActivity.class);
                startActivityForResult(i,0);

            }
        });
       // ImageView img = (ImageView)v.findViewById(R.id.img);
        //File f  =getAlbumStorageDir("com.example.kislay.firebaseexample/");
        //Picasso.with(getContext()).load("http://i.imgur.com/DvpvklR.png").into(img);
        //Picasso.with(getContext()).load("file://"+f+"/IMG_2017_05_02_12_37_59.jpg").into(img);

        //Log.e("FragPath",f.getAbsolutePath());
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        adapter.notifyDataSetChanged();
//        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setRealmAdapter(RealmResults<DigitalRecord> digitalRecords) {

        RealmDigitalRecordAdapter realmAdapter = new RealmDigitalRecordAdapter(getContext(),digitalRecords , true);
        // Set the data and tell the RecyclerView to draw
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }
    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        // create an empty adapter and add it to the recycler view
        adapter = new DigitalRecordAdapter(getContext());
        recycler.setAdapter(adapter);
    }




}
