package com.example.kislay.firebaseexample.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kislay.firebaseexample.R;
import com.example.kislay.firebaseexample.adapters.DigitalRecordAdapter;
import com.example.kislay.firebaseexample.adapters.MedicineReminderAdapter;
import com.example.kislay.firebaseexample.models.DigitalRecord;
import com.example.kislay.firebaseexample.models.MedicineReminder;
import com.example.kislay.firebaseexample.notification.broadcast_recievers.NotificationEventReceiver;
import com.example.kislay.firebaseexample.realm.RealmController;
import com.example.kislay.firebaseexample.realm.RealmMedicineReminderController;

import java.util.Date;

import io.realm.Realm;

public class MedicineReminderForm extends AppCompatActivity {
    private EditText name;
    private EditText other;
    private EditText time;
    private EditText interval;
    private Button b;
    private Realm realm;
    private Activity activity;
    EditText s,m,h;
    private MedicineReminderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder_form);

        s = (EditText)findViewById(R.id.s);
        m = (EditText)findViewById(R.id.m);
        h = (EditText)findViewById(R.id.h);
        this.realm  =RealmMedicineReminderController.with(this).getRealm();
        name = (EditText)findViewById(R.id.medicineName);
        other = (EditText)findViewById(R.id.other);

        interval = (EditText)findViewById(R.id.repeat);
        b = (Button)findViewById(R.id.sub);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sec = Integer.parseInt(s.getText().toString());
                int min = Integer.parseInt(m.getText().toString());
                int hou = Integer.parseInt(h.getText().toString());
                String med = name.getText().toString();
                String t = ""+hou+"::"+min+"::"+sec+"";
                String ot = other.getText().toString();
                String in  = interval.getText().toString();

                MedicineReminder medicineReminder = new MedicineReminder();
                medicineReminder.setId((int) (RealmMedicineReminderController.getInstance().getRecords().size() + System.currentTimeMillis()));

                medicineReminder.setMedicineName(med);
                medicineReminder.setTime(t);
                medicineReminder.setDaysInterval(in);
                medicineReminder.setOther(ot);

                realm.beginTransaction();
                realm.copyToRealm(medicineReminder);
                realm.commitTransaction();


                Toast.makeText(getApplicationContext(),"Saved.. Notification Set!! ",Toast.LENGTH_SHORT).show();
                Log.e("Button","Clicked");
                NotificationEventReceiver.setTime(sec,min,hou);
                NotificationEventReceiver.setupAlarm(getApplicationContext());
            }
        });

    }
}
