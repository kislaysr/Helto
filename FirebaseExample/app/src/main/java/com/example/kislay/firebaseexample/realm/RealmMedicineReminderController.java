package com.example.kislay.firebaseexample.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.example.kislay.firebaseexample.models.DigitalRecord;
import com.example.kislay.firebaseexample.models.MedicineReminder;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kislay on 05-05-2017.
 */

public class RealmMedicineReminderController {
    private static RealmMedicineReminderController instance;
    private final Realm realm;

    public RealmMedicineReminderController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmMedicineReminderController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmMedicineReminderController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmMedicineReminderController with(Activity activity) {

        if (instance == null) {
            instance = new RealmMedicineReminderController(activity.getApplication());
        }
        return instance;
    }

    public static RealmMedicineReminderController with(Application application) {

        if (instance == null) {
            instance = new RealmMedicineReminderController(application);
        }
        return instance;
    }

    public static RealmMedicineReminderController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(MedicineReminder.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<MedicineReminder> getRecords() {

        return realm.where(MedicineReminder.class).findAll();
    }

    //query a single item with the given id
    public MedicineReminder getRecord(String id) {

        return realm.where(MedicineReminder.class).equalTo("id", id).findFirst();
    }

    //check if Book.class is empty
    public boolean hasRecords() {

        return !realm.allObjects(MedicineReminder.class).isEmpty();
    }

    //query example
    public RealmResults<MedicineReminder> queryedBooks() {

        return realm.where(MedicineReminder.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}
