package com.example.kislay.firebaseexample.camera;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kislay.firebaseexample.R;
import com.example.kislay.firebaseexample.adapters.DigitalRecordAdapter;
import com.example.kislay.firebaseexample.adapters.RealmDigitalRecordAdapter;
import com.example.kislay.firebaseexample.app.Prefs;
import com.example.kislay.firebaseexample.fragments.DigitalRecordFragment;
import com.example.kislay.firebaseexample.models.DigitalRecord;
import com.example.kislay.firebaseexample.realm.RealmController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sandrios.sandriosCamera.internal.SandriosCamera;
import com.sandrios.sandriosCamera.internal.configuration.CameraConfiguration;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class CameraActivity extends AppCompatActivity{
    String url;
    private static final int CAPTURE_MEDIA = 368;
    private LayoutInflater inflater;
    private String title;
    private Realm realm;
    private Activity activity;
    private DigitalRecordAdapter adapter;
    FirebaseUser user;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.withPicker:
                    new SandriosCamera(activity, CAPTURE_MEDIA)
                            .setShowPicker(true)
                            .setVideoFileSize(20)
                            .setMediaAction(CameraConfiguration.MEDIA_ACTION_BOTH)
                            .enableImageCropping(true)
                            .launchCamera();
                    break;
                case R.id.withoutPicker:
                    CameraConfiguration cf = new CameraConfiguration();

                     new SandriosCamera(activity, CAPTURE_MEDIA)
                            .setShowPicker(false)
                             .setVideoFileSize(1)
                            .setMediaAction(CameraConfiguration.MEDIA_ACTION_PHOTO)
                            .enableImageCropping(true)
                            .launchCamera();

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.activity_camera);


        activity = this;
        this.realm  =RealmController.with(this).getRealm();
        findViewById(R.id.withPicker).setOnClickListener(onClickListener);
        findViewById(R.id.withoutPicker).setOnClickListener(onClickListener);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_MEDIA && resultCode == RESULT_OK) {
            Log.e("File", "" + data.getStringExtra(CameraConfiguration.Arguments.FILE_PATH));
             url  =data.getStringExtra(CameraConfiguration.Arguments.FILE_PATH);

            inflater = CameraActivity.this.getLayoutInflater();
                    View content = inflater.inflate(R.layout.set_title, null);
                    final EditText editTitle = (EditText) content.findViewById(R.id.title);
                    AlertDialog.Builder builder = new AlertDialog.Builder(CameraActivity.this);
                    builder.setView(content)
                            .setTitle("Add Title")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if (editTitle.getText() == null || editTitle.getText().toString().equals("") || editTitle.getText().toString().equals(" ")) {
                                        Toast.makeText(CameraActivity.this, "Entry not saved, missing title", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Persist your data easily
                                        title = editTitle.getText().toString();
                                        DigitalRecord digitalRecord = new DigitalRecord();
                                        digitalRecord.setId((int) (RealmController.getInstance().getRecords().size() + System.currentTimeMillis()));
                                        digitalRecord.setEmail(user.getEmail());
                                        digitalRecord.setImageURL(url);
                                        digitalRecord.setDate(String.valueOf(new Date()));
                                        digitalRecord.setTitle(title);

                                        realm.beginTransaction();
                                        realm.copyToRealm(digitalRecord);
                                        realm.commitTransaction();
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    File fdelete = new File(url);
                                    if (fdelete.exists()) {
                                        if (fdelete.delete()) {
                                            System.out.println("file Deleted :" + url);
                                        } else {
                                            System.out.println("file not Deleted :" + url);
                                        }
                                    }
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();






            Toast.makeText(this, "Media captured.", Toast.LENGTH_SHORT).show();
        }
    }


//    public void setRealmData() {
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        ArrayList<DigitalRecord> digitalRecords = new ArrayList<>();
//
//        DigitalRecord digitalRecord;// = new DigitalRecord();
////        digitalRecord.setId((int) (1 + System.currentTimeMillis()));
////        digitalRecord.setTitle(title);
////        digitalRecord.setDate((new Date()).toString());
////        digitalRecord.setEmail(user.getEmail());
////        digitalRecord.setImageURL(url);
////        digitalRecords.add(digitalRecord);
//
//        digitalRecord = new DigitalRecord();
//        digitalRecord.setId((int) (2 + System.currentTimeMillis()));
//        digitalRecord.setTitle("Test");
//        digitalRecord.setDate((new Date()).toString());
//        digitalRecord.setEmail(user.getEmail());
//        digitalRecord.setImageURL("/sdcard/Pictures/com.example.kislay.firebaseexample/IMG_2017_05_02_23_48_53.jpg");
//        digitalRecords.add(digitalRecord);
//        for ( DigitalRecord b : digitalRecords) {
//            // Persist your data easily
//            realm.beginTransaction();
//            realm.copyToRealm(b);
//            realm.commitTransaction();
//        }
//
//        Prefs.with(this).setPreLoad(true);
//
//    }
}
