package com.zz.kit.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.IntentFilter;
import android.os.Bundle;

import com.zz.assassinzkit.R;
import com.zz.kit.receiver.MyReceiver;
import com.zz.kit.utils.L;
import com.zz.kit.utils.Logger;
import com.zz.kit.widget.SpringProgressView;

public class TestActivity extends Activity {

    AlertDialog mDialog;
    MyReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpringProgressView progressView;
        progressView = (SpringProgressView) findViewById(R.id.spring_progress_view);
        progressView.setMaxCount(1000.0f);
        progressView.setCurrentCount(888);

        mDialog = new AlertDialog.Builder(this).setTitle("提示").setMessage("测试").setPositiveButton("确定", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                L.setUserName("!!!!!!!");
                L.out("test");

                Logger.init("ZZZZZZZZZ").setMethodCount(1);
                Logger.i("test");

                dialog.dismiss();

            }
        }).show();

        registerMyReceiver();

    }

    //动态注册，跟随activity生命周期
    private void registerMyReceiver() {
        L.setUserName("!!!!");
        L.out("registerMyReceiver");
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.AIRPLANE_MODE");
        registerReceiver(receiver, filter);
        return;
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mDialog.dismiss();
        unregisterReceiver(receiver);
    }

}
