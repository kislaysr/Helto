package com.example.kislay.firebaseexample.models;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kislay on 05-05-2017.
 */

public class MedicineReminder extends RealmObject {
    @PrimaryKey
    private int id;
    private String medicineName;
    private String time ;
    private String daysInterval;
    private String other;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDaysInterval() {
        return daysInterval;
    }

    public void setDaysInterval(String daysInterval) {
        this.daysInterval = daysInterval;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
