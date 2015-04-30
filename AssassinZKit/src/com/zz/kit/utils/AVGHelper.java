package com.zz.kit.utils;

import android.app.Activity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ZhaoZhao on 2015/4/30.
 */
public class AVGHelper {

    // public AVGHelper() {
    // }
    //
    // /**
    // * 线程安全的单例模式
    // */
    // public static AVGHelper getInstance() {
    // return Holder.instance;
    //
    // }
    //
    // private static class Holder {
    // public static AVGHelper instance = new AVGHelper();
    // }

    public static boolean isChineseChars(int num) {
        return (num >= 11904 && num <= 65519);
    }

    public static int getStringAllLength(String str) {
        int len = 0;
        for (int i = 0; i < str.length(); i++) {
            if (isChineseChars(str.charAt(i))) {
                len += 2;
            } else {
                len++;
            }
        }
        return len;
    }

    public static String autoNewLine(String str, int bytenum) {
        String mstring = "";
        int len = 0;
        String tmp = "";
        for (int i = 0; i < str.length(); i++) {
            mstring += str.charAt(i);
            tmp += str.charAt(i);
            len = AVGHelper.getStringAllLength(tmp);
            if (len == bytenum || ((len + 1) == bytenum && AVGHelper.isChineseChars(str.charAt(i)))
                    || (len + 1 == bytenum && AVGHelper.isChineseChars(str.charAt(i + 1)))) {
                mstring += "\n";
                tmp = "";
            }
        }
        return mstring;
    }

    static int i = 1;

    public static void autoTyper(final Activity mActivity, final TextView label, int lineNum, int textDelayTime) {
        final String mString = autoNewLine(label.getText().toString(), lineNum);
        final int totalLen = mString.length();
        label.setText("");
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                UIUpdater(mActivity, label, mString.substring(0, i++));
                if (i == totalLen + 1) {
                    i = 0;
                    Logger.i("end");
                    timer.cancel();
                }

            }
        }, 100, textDelayTime);

    }

    private static void UIUpdater(Activity activity, final TextView textView, final String str) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(str);
            }
        });
    }

}
