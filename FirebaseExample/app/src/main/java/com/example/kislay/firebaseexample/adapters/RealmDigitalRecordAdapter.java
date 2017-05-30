package com.example.kislay.firebaseexample.adapters;

import android.content.Context;

import com.example.kislay.firebaseexample.models.DigitalRecord;

import io.realm.RealmResults;

/**
 * Created by kislay on 02-05-2017.
 */

public class RealmDigitalRecordAdapter extends RealmModelAdapter<DigitalRecord> {
    public RealmDigitalRecordAdapter(Context context, RealmResults<DigitalRecord> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
