package com.example.kislay.firebaseexample.notification.broadcast_recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kislay on 06-05-2017.
 */

public class NotificationStartServiceReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationEventReceiver.setupAlarm(context);
    }
}
