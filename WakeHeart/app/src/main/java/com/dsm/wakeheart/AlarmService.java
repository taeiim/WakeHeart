package com.dsm.wakeheart;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.dsm.wakeheart.Activity.AlarmCustomDialog;

public class AlarmService extends Service {
    private Context context;
    private Boolean sleep;


    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent dialogIntent = new Intent(this, AlarmCustomDialog.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(dialogIntent);

//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, dialogIntent, PendingIntent.FLAG_ONE_SHOT);
//        try {
//            pendingIntent.send();
//        } catch (Exception e) {
//
//
//        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
