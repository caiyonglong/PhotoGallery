package com.ckt.cyl.photogallery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by D22434 on 2017/7/31.
 */

public class StartupReceiver extends BroadcastReceiver {
    private static final String TAG = "StartupReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Receiver broadcast intent: " + intent.getAction());

        //设备重启后启动定时器
        boolean isOn = QueryPreferences.isAlarmOn(context);
        PollService.setServiceAlarm(context, isOn);
    }
}
