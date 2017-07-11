package com.pack.pack.application.service;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.pack.pack.application.R;
import com.pack.pack.model.web.notification.FeedMsg;

import java.util.List;
import java.util.Random;

/**
 * Created by Saurav on 11-07-2017.
 */
public final class NotificationUtil {

    private NotificationUtil() {
    }

    public static boolean isApplicationRunningInBackgroud(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = activityManager.getRunningAppProcesses();
            for(ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessInfos) {
                if(runningAppProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for(String pkg : runningAppProcessInfo.pkgList) {
                        if(pkg.equals(context.getPackageName())) {
                            return false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
            if(runningTasks == null || runningTasks.isEmpty()) {
                return true;
            }
            ComponentName componentName = runningTasks.get(0).topActivity;
            if(componentName.getPackageName().equals(context.getPackageName())) {
                return false;
            }
        }
        return true;
    }

    public static void showNotificationMessage(String title, String message, Context context) {
        if(message == null) {
            message = "";
        }
        final int NOTIFICATION_ID = new Random().nextInt();//Math.abs(feedMsg.getKey())%10000;
        final NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(
                        Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(title)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setContentText(message);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(alarmSound);

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
        notificationBuilder.setLargeIcon(largeIcon);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }


    /*private void showNotification(FeedMsg feedMsg, Context context) {
        String message =  feedMsg.getTitle();
        if(message == null) {
            return;
        }
        if(message == null) {
            message = "";
        }
        final int NOTIFICATION_ID = new Random().nextInt();//Math.abs(feedMsg.getKey())%10000;
        final NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(
                        Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(feedMsg.getTitle())
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setContentText(message);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(alarmSound);

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
        notificationBuilder.setLargeIcon(largeIcon);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }*/
}
