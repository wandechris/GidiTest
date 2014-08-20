/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.test.gidi.reciever;

import com.test.gidi.R;
import com.test.gidi.ViewActivity;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * PingService creates a notification that includes 2 buttons: one to snooze the
 * notification, and one to dismiss it.
 */
public class PingService extends IntentService {

    private NotificationManager mNotificationManager;
    private String mMessage;
   // private String mid;
    private int mMillis;
    NotificationCompat.Builder builder;
    int id;

    public PingService() {
        super("com.test.gidi.pingme");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        
    	
        mMessage = intent.getStringExtra("name");
        
       // mid = intent.getStringExtra("id");
       // Long u = Long.parseLong(mid);
        
       
        
        int int_id = 1;
        
        mMillis = intent.getIntExtra("extra", 10000);
        
        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
            
       // issueNotification(intent, mMessage);
        
        String action = intent.getAction();
        // This section handles the 3 possible actions:
        // ping, snooze, and dismiss.
        
        if(action.equals("set")) {
        	Log.v("service", mMessage);
            issueNotification(intent, mMessage,int_id);
        } else if (action.equals("cancel")) {
            nm.cancel(int_id);
           
            // Sets a snooze-specific "done snoozing" message.
            issueNotification(intent, "snooze",int_id);

        } else if (action.equals("Dismiss")) {
            nm.cancel(int_id);
        }
    }

    private void issueNotification(Intent intent, String msg, int mid) {
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        // Sets up the Snooze and Dismiss action buttons that will appear in the
        // expanded view of the notification.
        Intent dismissIntent = new Intent(this, PingService.class);
        dismissIntent.setAction("Dismiss");
        PendingIntent piDismiss = PendingIntent.getService(this, 0, dismissIntent, 0);


        // Constructs the Builder object.
        id = mid;
        builder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setContentTitle(msg+" is starting")
               // .setContentText(loc)
                
                .setDefaults(Notification.DEFAULT_ALL) // requires VIBRATE permission
                /*
                 * Sets the big view "big text" style and supplies the
                 * text (the user's reminder message) that will be displayed
                 * in the detail area of the expanded notification.
                 * These calls are ignored by the support library for
                 * pre-4.1 devices.
                 */
                .setStyle(new NotificationCompat.BigTextStyle()
                     .bigText(msg))
                .addAction (R.drawable.ic_stat_dismiss,
                        getString(R.string.dismiss), piDismiss);

        /*
         * Clicking the notification itself displays ResultActivity, which provides
         * UI for snoozing or dismissing the notification.
         * This is available through either the normal view or big view.
         */
         Intent resultIntent = new Intent(this, ViewActivity.class);
         resultIntent.putExtra("name", msg);
         resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

         // Because clicking the notification opens a new ("special") activity, there's
         // no need to create an artificial back stack.
         PendingIntent resultPendingIntent =
                 PendingIntent.getActivity(
                 this,
                 0,
                 resultIntent,
                 PendingIntent.FLAG_UPDATE_CURRENT
         );

         builder.setContentIntent(resultPendingIntent);
         startTimer(mMillis);
    }

    private void issueNotification(NotificationCompat.Builder builder, int id) {
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        // Including the notification ID allows you to update the notification later on.
        mNotificationManager.notify(id, builder.build());
    }

 // Starts the timer according to the number of seconds the user specified.
    private void startTimer(int millis) {
        try {
            Thread.sleep(millis);

        } catch (InterruptedException e) {
            Log.d("Gidi", "Sleep Error");
        }
        Log.d("Gidi", "Finished");
        issueNotification(builder, id);
    }
}
