package com.example.kislay.firebaseexample.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kislay.firebaseexample.R;
import com.example.kislay.firebaseexample.app.Prefs;
import com.example.kislay.firebaseexample.fragments.DigitalRecordFragment;
import com.example.kislay.firebaseexample.models.DigitalRecord;
import com.example.kislay.firebaseexample.realm.RealmController;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kislay on 02-05-2017.
 */

public class DigitalRecordAdapter extends RealmRecylerViewAdapter<DigitalRecord> {
    final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public DigitalRecordAdapter(Context context) {

        this.context = context;
    }
    // create new views (invoked by the layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_digital_record, parent, false);
        return new CardViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        realm = RealmController.getInstance().getRealm();

        // get the article
        final DigitalRecord digitalRecord = getItem(position);
        // cast the generic view holder to our specific one
        final CardViewHolder holder = (CardViewHolder) viewHolder;

        // set the title and the snippet
        holder.textTitle.setText(digitalRecord.getTitle());
        holder.textDate.setText(digitalRecord.getDate());

        // load the background image
        try{
            if (digitalRecord.getImageURL() != null) {

                Picasso.with(context).load("file://"+digitalRecord.getImageURL()).into(holder.imageBackground, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("Picasso","success");
                    }

                    @Override
                    public void onError() {
                        Log.e("Picasso","failed--"+digitalRecord.getImageURL());
                    }
                });

            }
        }catch (Exception e){
            Log.e("Picasso:",e.toString());
        }


        //remove single match from realm
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                RealmResults<DigitalRecord> results = realm.where(DigitalRecord.class).findAll();

                // Get the book title to show it in toast message
                DigitalRecord b = results.get(position);
                String title = b.getTitle();

                // All changes to data must happen in a transaction
                realm.beginTransaction();

                File fdelete = new File(results.get(position).getImageURL());
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        System.out.println("file Deleted :" + results.get(position).getTitle());
                    } else {
                        System.out.println("file not Deleted :" + results.get(position).getTitle());
                    }
                }
                // remove single match
                results.remove(position);
                realm.commitTransaction();

                if (results.size() == 0) {
                    Prefs.with(context).setPreLoad(false);
                }

                notifyDataSetChanged();

                Toast.makeText(context, title + " is removed from Realm", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //update single match from realm
        /*holder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });*/
    }

    // return the size of your data set (invoked by the layout manager)
    public int getItemCount() {

        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView textTitle;
        public TextView textDate;
        public TextView textDescription;
        public ImageView imageBackground;

        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_books);
            textTitle = (TextView) itemView.findViewById(R.id.digitalRecordTitle);
            textDate = (TextView) itemView.findViewById(R.id.dateCreated);
            imageBackground = (ImageView) itemView.findViewById(R.id.image_background);
        }
    }


}


