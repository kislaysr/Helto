package com.example.kislay.firebaseexample.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kislay.firebaseexample.R;
import com.example.kislay.firebaseexample.app.Prefs;
import com.example.kislay.firebaseexample.models.DigitalRecord;
import com.example.kislay.firebaseexample.models.MedicineReminder;
import com.example.kislay.firebaseexample.realm.RealmController;
import com.example.kislay.firebaseexample.realm.RealmMedicineReminderController;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kislay on 05-05-2017.
 */

public class MedicineReminderAdapter extends RealmRecylerViewAdapter<MedicineReminder> {
    final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public MedicineReminderAdapter(Context context) {

        this.context = context;
    }
    // create new views (invoked by the layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new MedicineReminderAdapter.CardViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        realm = RealmMedicineReminderController.getInstance().getRealm();

        // get the article
        final MedicineReminder medicineReminder = getItem(position);
        // cast the generic view holder to our specific one
        final CardViewHolder holder = (MedicineReminderAdapter.CardViewHolder) viewHolder;

        // set the title and the snippet
        holder.textMedicine.setText(medicineReminder.getMedicineName());
        holder.textInterval.setText(medicineReminder.getDaysInterval());
        holder.textOther.setText(medicineReminder.getOther());
        holder.textTime.setText(medicineReminder.getTime());



        //remove single match from realm
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                RealmResults<MedicineReminder> results = realm.where(MedicineReminder.class).findAll();

                // Get the book title to show it in toast message
                MedicineReminder b = results.get(position);
                String title = b.getMedicineName();

                // All changes to data must happen in a transaction
                realm.beginTransaction();

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
        public TextView textMedicine;
        public TextView textTime;
        public TextView textOther;
        public TextView textInterval;

        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_books);
            textMedicine = (TextView) itemView.findViewById(R.id.medicineName);
            textTime = (TextView) itemView.findViewById(R.id.time);
            textOther = (TextView) itemView.findViewById(R.id.other);
            textInterval = (TextView)itemView.findViewById(R.id.repeatingDays);
        }
    }


}