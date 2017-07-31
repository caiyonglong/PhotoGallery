package com.ckt.cyl.photogallery;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.ckt.cyl.photogallery.activities.PhotoGalleryActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by D22434 on 2017/7/31.
 */

public class PollService extends IntentService {
    private static final String TAG = "PollService";
    private static final long POLL_INTERVAL_MS = TimeUnit.MINUTES.toMillis(1);

    public static final String ACTION_SHOW_NOTIFICATON =
            "com.ckt.cyl.photogallery.SHOW_NOTIFICATION";
    public static final String PERM_PRIVATE =
            "com.ckt.cyl.photogallery.PRIVATE";
    public static final String REQUEST_CODE = "REQUEST_CODE";
    public static final String NOTIFICATION = "NOTIFICATION";

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (isOn) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), POLL_INTERVAL_MS, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
        //存储定时器状态
        QueryPreferences.setAlarmOn(context, isOn);
    }

    public static boolean isServiceAlarmOn(Context context) {
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent
                .getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    public PollService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (!isNetWorkAvailableAndConnected()) {

        }
//        Log.d(TAG, "Received an intent：" + intent);
        String query = QueryPreferences.getStoreQuery(this);
        String lastId = QueryPreferences.getLastResultId(this);
        List<GalleryItem> items;
        if (query == null) {
            items = new FlickerFetchr().fetchRecentPhotos();
        } else {
            items = new FlickerFetchr().searchPhotos(query);
        }

        if (items.size() == 0) {
            return;
        }

        String resultId = items.get(0).getId();
        if (resultId.equals(lastId)) {
            Log.d(TAG, "get an old ：" + resultId);
        } else {
            Log.d(TAG, "get a new ：" + resultId);

            Resources resources = getResources();
            Intent i = PhotoGalleryActivity.newIntent(this);
            PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

            Notification notification = new NotificationCompat.Builder(this)
                    .setTicker(resources.getString(R.string.new_pic_title))
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle(resources.getString(R.string.new_pic_title))
                    .setContentText(resources.getString(R.string.new_pic_text))
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();
            //发送广播
            setBackgroundNotification(0, notification);
        }

        QueryPreferences.setLastResultId(this, resultId);
    }

    private void setBackgroundNotification(int i, Notification notification) {
        Intent it = new Intent(ACTION_SHOW_NOTIFICATON);
        it.putExtra(REQUEST_CODE, i);
        it.putExtra(NOTIFICATION, notification);
        sendOrderedBroadcast(it, PERM_PRIVATE, null, null,
                Activity.RESULT_OK, null, null);
    }

    private boolean isNetWorkAvailableAndConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetWorkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetWorkConnected = isNetWorkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
        return isNetWorkConnected;
    }
}
