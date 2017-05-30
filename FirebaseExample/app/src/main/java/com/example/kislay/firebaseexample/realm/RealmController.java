package com.example.kislay.firebaseexample.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.example.kislay.firebaseexample.models.DigitalRecord;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kislay on 02-05-2017.
 */

public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

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
        realm.clear(DigitalRecord.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<DigitalRecord> getRecords() {

        return realm.where(DigitalRecord.class).findAll();
    }

    //query a single item with the given id
    public DigitalRecord getRecord(String id) {

        return realm.where(DigitalRecord.class).equalTo("id", id).findFirst();
    }

    //check if Book.class is empty
    public boolean hasRecords() {

        return !realm.allObjects(DigitalRecord.class).isEmpty();
    }

    //query example
    public RealmResults<DigitalRecord> queryedBooks() {

        return realm.where(DigitalRecord.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}
