package cn.sina.youxi.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * 类说明： JSON工具类
 * 
 * @author Cundong
 * @date 2012-2-9
 * @version 1.0
 */
public class JSONUtils {

	/**
	 * 将JSON String解析成JsonObject
	 * 
	 * @param jsonString
	 * @return 成功返回对应JsonObject，否则返回null
	 */
	public static JSONObject parse2JSONObject(String jsonString) {
		if (StringUtils.isBlank(jsonString))
			return null;
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}

	/**
	 * 将JSON String解析成JSONArray
	 * 
	 * @param jsonString
	 * @return 成功返回对应JSONArray，否则返回null
	 */
	public static JSONArray parse2JSONArray(String jsonString) {
		if (StringUtils.isBlank(jsonString))
			return null;

		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	/**
	 * 从JSONObject中获取字符串数据
	 * 
	 * @param itemObj
	 * @param key
	 * @return
	 */
	public static String getString(JSONObject itemObj, String key) {
		String paramValue = "";

		try {
			paramValue = (itemObj != null && itemObj.has(key) && !itemObj.isNull(key)) ? itemObj.getString(key) : "";
		} catch (JSONException e) {
			Log.e("JSONObject error", "get value from JSONObject error," + e.getStackTrace());
			e.printStackTrace();
		}
		return paramValue;
	}

	/**
	 * 从JSONObject中获取整形数据
	 * 
	 * @param itemObj
	 * @param key
	 * @return
	 */
	public static int getInt(JSONObject itemObj, String key) {
		int paramValue = 0;

		try {
			paramValue = (itemObj != null && itemObj.has(key)) ? itemObj.getInt(key) : 0;
		} catch (JSONException e) {
			Log.e("JSONObject error", "get value from JSONObject error," + e.getStackTrace());
			e.printStackTrace();
		}
		return paramValue;
	}

	public static boolean getBoolean(JSONObject itemObj, String key) {

		boolean paramValue = false;

		try {
			paramValue = (itemObj != null && itemObj.has(key)) ? itemObj.getBoolean(key) : false;
		} catch (JSONException e) {
			Log.e("JSONObject error", "get value from JSONObject error," + e.getStackTrace());
			e.printStackTrace();
		}
		return paramValue;
	}

	public static JSONObject getJSONObject(JSONObject itemObj, String key) {

		JSONObject dataObj = null;

		try {
			dataObj = (itemObj != null && itemObj.has(key)) ? itemObj.getJSONObject(key) : null;
		} catch (JSONException e) {
			Log.e("JSONObject error", "get value from JSONObject error," + e.getStackTrace());
			e.printStackTrace();
		}
		return dataObj;
	}

	public static List<String> getStringList(JSONArray jsonArray) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				list.add(jsonArray.getString(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	public static JSONArray getJSONArray(JSONObject itemObj, String key) {

		JSONArray jsonArray = null;
		try {
			jsonArray = (itemObj != null && itemObj.has(key)) ? itemObj.getJSONArray(key) : null;
		} catch (JSONException e) {
			Log.e("JSONObject error", "get value from JSONObject error," + e.getStackTrace());
			e.printStackTrace();
		}
		return jsonArray;
	}

	/**
	 * JSONArray转为ArrayList
	 * 
	 * @param jsonArray
	 * @return
	 */
	public static ArrayList<JSONObject> parse2List(JSONArray jsonArray) {
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		int objCounter = jsonArray != null ? jsonArray.length() : 0;

		try {
			for (int i = 0; i < objCounter; i++) {
				list.add(jsonArray.getJSONObject(i));
			}
		} catch (JSONException e) {
			Log.e("JSONObject error", "parse2List error," + e.getStackTrace());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * JSONArray转为ArrayList
	 * 
	 * @param jsonArray
	 * @param legnth
	 * @return
	 */
	public static ArrayList<JSONObject> parse2List(JSONArray jsonArray, int length) {
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		int objCounter = jsonArray != null ? jsonArray.length() : 0;

		if (objCounter > length)
			objCounter = length;

		try {
			for (int i = 0; i < objCounter; i++) {
				list.add(jsonArray.getJSONObject(i));
			}
		} catch (JSONException e) {
			Log.e("JSONObject error", "parse2List error," + e.getStackTrace());
			e.printStackTrace();
		}
		return list;
	}
}