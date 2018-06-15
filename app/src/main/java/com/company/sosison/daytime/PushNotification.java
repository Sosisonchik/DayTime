package com.company.sosison.daytime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.company.sosison.daytime.pojo.Consts;
import com.company.sosison.daytime.pojo.Task;

import java.net.URI;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PushNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,new Intent(context,MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
            builder
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setSound(alarmSound)
                    .setWhen(System.currentTimeMillis())
                    .setVibrate(new long[]{1000,1000,1000,1000,1000})
                    .setTicker("Новое уведомление")
                    .setContentText("Вы должны зделать то что запланировали")
                    .setContentTitle("Daytime")
                    .setSmallIcon(R.drawable.ic_done_black_24dp)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_assignment_black_24dp));
        nm.notify(0,builder.build());
    }
}