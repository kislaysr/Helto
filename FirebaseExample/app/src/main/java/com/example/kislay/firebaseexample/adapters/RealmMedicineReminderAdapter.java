package com.example.kislay.firebaseexample.adapters;

import android.content.Context;

import com.example.kislay.firebaseexample.models.MedicineReminder;

import io.realm.RealmResults;

/**
 * Created by kislay on 05-05-2017.
 */

public class RealmMedicineReminderAdapter extends RealmModelAdapter<MedicineReminder> {
    public RealmMedicineReminderAdapter(Context context, RealmResults<MedicineReminder> realmResults, boolean automaticUpdate) {
            super(context, realmResults, automaticUpdate);
    }
}