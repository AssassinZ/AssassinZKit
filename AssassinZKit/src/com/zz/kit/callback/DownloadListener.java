package com.zz.kit.callback;

public abstract interface DownloadListener {

	/**
	 * 开始下载
	 */
	public abstract void onStart();

	/**
	 * 下载中
	 * 
	 * @param current
	 * @param contentLength
	 */
	public abstract void onProgress(int newRate, long contentLength);

	/**
	 * 下载失败
	 */
	public abstract void onError(String msg);

	/**
	 * 停止下载
	 */
	public abstract void onStop();

	/**
	 * 下载完成
	 */
	public abstract void onComplete(String desPath);
}
