package com.zz.kit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "BootCompleteReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
                //启动程序
//                Intent newIntent = new Intent(context, TestActivity.class);
//                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //注意，必须添加这个标记，否则启动会失败
//                context.startActivity(newIntent);

            }
            if (intent.getAction().equals(Intent.ACTION_MY_PACKAGE_REPLACED)) {
                Log.i(TAG, "ACTION_MY_PACKAGE_REPLACED");
//                Intent service = new Intent(context, MyService.class);
//                context.startService(service);
            }
            if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
                Log.i(TAG, "ACTION_AIRPLANE_MODE_CHANGED");
//                Intent service = new Intent(context, MyService.class);
//                context.startService(service);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }


    }

}
