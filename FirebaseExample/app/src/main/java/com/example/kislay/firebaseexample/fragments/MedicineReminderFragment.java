package com.example.kislay.firebaseexample.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kislay.firebaseexample.R;
import com.example.kislay.firebaseexample.activity.MedicineReminderForm;
import com.example.kislay.firebaseexample.adapters.DigitalRecordAdapter;
import com.example.kislay.firebaseexample.adapters.MedicineReminderAdapter;
import com.example.kislay.firebaseexample.adapters.RealmDigitalRecordAdapter;
import com.example.kislay.firebaseexample.adapters.RealmMedicineReminderAdapter;
import com.example.kislay.firebaseexample.camera.CameraActivity;
import com.example.kislay.firebaseexample.models.DigitalRecord;
import com.example.kislay.firebaseexample.models.MedicineReminder;
import com.example.kislay.firebaseexample.realm.RealmController;
import com.example.kislay.firebaseexample.realm.RealmMedicineReminderController;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicineReminderFragment extends Fragment {

    private MedicineReminderAdapter adapter;
    private Realm realm;
    private Activity activity;
    private RecyclerView recycler;
    private Button b;
    public MedicineReminderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_medicine_reminder, container, false);

        recycler = (RecyclerView)v.findViewById(R.id.reminderList);
        this.realm = RealmMedicineReminderController.with(this).getRealm();
        setupRecycler();
        b = (Button)v.findViewById(R.id.addReminder);
        RealmMedicineReminderController.with(this).refresh();


        setRealmAdapter(RealmMedicineReminderController.with(this).getRecords());
        // realm ends
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),MedicineReminderForm.class);
                startActivityForResult(i,0);

            }
        });
        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        adapter.notifyDataSetChanged();
//        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setRealmAdapter(RealmResults<MedicineReminder> reminders) {

        RealmMedicineReminderAdapter realmAdapter = new RealmMedicineReminderAdapter(getContext(),reminders , true);
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
        adapter = new MedicineReminderAdapter(getContext());
        recycler.setAdapter(adapter);
    }

}
