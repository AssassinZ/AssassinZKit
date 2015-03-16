package com.zz.kit.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zz.kit.callback.DownloadListener;

public class Downloader {

	private static AtomicBoolean STOPED = new AtomicBoolean(false);

	private static final int ONSTART = 0x1;
	private static final int ONPROGRESS = 0x2;
	private static final int ONERROR = 0x3;
	private static final int ONSTOP = 0x4;
	private static final int ONCOMPLETE = 0x5;

	private Context mContext;
	private DownloadListener mListener;
	private String downloadUrl = "";
	private long startPos = 0;

	private String SDPATH;
	private String FILESPATH;
	private String desPath;
	private String tempPath;

	public Downloader() {
	}

	/**
	 * 线程安全的单例模式
	 */
	public static Downloader getInstance() {
		return Holder.instance;
	}

	private static class Holder {
		public static Downloader instance = new Downloader();
	}

	public static void stop() {
		STOPED.set(true);
	}

	/**
	 * @param downloadUrl
	 *            下载URL
	 * @param context
	 *            上下文
	 * @param downloadListener
	 *            下载回调
	 */
	public void downloadFile(Context context, String downloadUrl,
			DownloadListener downloadListener) {
		init(context, downloadUrl, downloadListener);
		new downloadThread().start();
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 * @param downloadUrl
	 * @param downloadListener
	 */
	private void init(Context context, String downloadUrl,
			DownloadListener downloadListener) {
		this.mContext = context;
		this.downloadUrl = downloadUrl;
		if (downloadListener != null) {
			this.mListener = downloadListener;
		} else {
			this.mListener = new DownloadListener() {

				@Override
				public void onStart() {
				}

				@Override
				public void onStop() {
				}

				@Override
				public void onProgress(int newRate, long contentLength) {
				}

				@Override
				public void onError(String msg) {
				}

				@Override
				public void onComplete(String desPath) {
				}
			};
		}
		STOPED.set(false);

		// SDPATH = DirectoryManager.getInstance().getDirPath(DirType.DOWNLOAD)
		// + "/";
		SDPATH = Environment.getExternalStorageDirectory().getPath() + "/";
		FILESPATH = "sina" + "/" + "game" + "/";

		if (desPath == null || desPath.equalsIgnoreCase("")) {
			desPath = SDPATH + FILESPATH + "sinagamesdk.apk";
			// desPath = "/storage/emulated/0/sina/game/sinagamesdk.apk";
		}
	}

	private void start() {
		mHandler.sendEmptyMessage(ONSTART);

		File tmpfile = null;
		File desfile = null;
		HttpURLConnection conn = null;
		InputStream ins = null;
		RandomAccessFile randomAccessFile = null;

		try {
			desfile = new File(desPath);

			if (desfile.exists()) {
				mHandler.sendEmptyMessage(ONCOMPLETE);
				return;
			}

			File folder = desfile.getParentFile();
			if (!folder.exists()) {
				folder.mkdirs();
			}

			tempPath = desPath + ".tmp";
			tmpfile = new File(tempPath);
			if (!tmpfile.exists()) {
				// tmpfile.createNewFile();
				startPos = 0;
			} else {
				startPos = tmpfile.length();
				// FileInputStream fis = new FileInputStream(tmpfile);
				// startPos = fis.available();
				// fis.close();
			}
			conn = (HttpURLConnection) new URL(downloadUrl).openConnection();
			conn.setConnectTimeout(100 * 1000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Range", "bytes=" + startPos + "-");
			ins = conn.getInputStream();
			long contentLength = conn.getContentLength() + startPos;
			randomAccessFile = new RandomAccessFile(tempPath, "rwd");
			randomAccessFile.seek(startPos);

			byte[] buff = new byte[1024];
			int len = 0;
			int oldRate = 0;
			while (!STOPED.get() && (len = ins.read(buff)) != -1) {
				randomAccessFile.write(buff, 0, len);
				startPos += len;
				if (startPos > contentLength) {
					startPos = contentLength;
				}

				int newRate = Math
						.round((float) startPos / contentLength * 100);

				if (!STOPED.get()) {
					if (newRate == 0 || ((newRate - oldRate) >= 1)) {

						Log.d(this.getClass().getName(), "startPos:" + startPos
								+ ",contentLength:" + contentLength);
						Log.d(this.getClass().getName(),
								"newRate:" + newRate + ",oldRate:" + oldRate
										+ ",flag:" + STOPED.get());

						Message message = mHandler.obtainMessage(ONPROGRESS);
						Bundle bundle = new Bundle();
						bundle.putInt("newRate", newRate);
						bundle.putLong("contentLength", contentLength);
						message.setData(bundle);
						mHandler.sendMessage(message);
						oldRate = newRate;
					}
				}
			}

			if (STOPED.get()) {
				mHandler.sendEmptyMessage(ONSTOP);
			} else {
				tmpfile.renameTo(desfile);
				mHandler.sendEmptyMessage(ONCOMPLETE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message message = mHandler.obtainMessage(ONERROR);
			Bundle bundle = new Bundle();
			bundle.putString("msg", e.toString());
			message.setData(bundle);
			mHandler.sendMessage(message);
		} finally {
			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	private class downloadThread extends Thread {
		@Override
		public void run() {
			Downloader.getInstance().start();
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ONSTART:
				mListener.onStart();
				break;
			case ONPROGRESS:
				int newRate = msg.getData().getInt("newRate");
				long contentLength = msg.getData().getLong("contentLength");
				mListener.onProgress(newRate, contentLength);
				break;
			case ONERROR:
				String str = msg.getData().getString("msg");
				mListener.onError(str);
				break;
			case ONSTOP:
				mListener.onStop();
				break;
			case ONCOMPLETE:
				mListener.onComplete(desPath);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * @param desPath
	 *            约定为"/"分隔符
	 */
	public void setDesPath(String desPath) {
		if (desPath == null || desPath.equalsIgnoreCase("")) {
			this.desPath = SDPATH + FILESPATH + "sinagamesdk.apk";
		} else {
			this.desPath = desPath;
		}
	}

}
