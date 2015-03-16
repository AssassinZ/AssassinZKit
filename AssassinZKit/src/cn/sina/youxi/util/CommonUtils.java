package cn.sina.youxi.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * 类说明： 公用工具类（包含R文件映射）
 * 
 * @author Cundong
 * @date 2012-2-7
 * @version 1.0
 */
public class CommonUtils {

	/**
	 * java上的回车换行(通用)
	 */
	public static final String ENTER = System.getProperty("line.separator");

	/**
	 * 获取当前应用的名称
	 * 
	 * @param instance
	 * @return
	 */
	public static String getAppName(Activity instance) {
		final PackageManager pm = instance.getApplicationContext()
				.getPackageManager();
		ApplicationInfo ai;
		try {
			ai = pm.getApplicationInfo(instance.getPackageName(), 0);
		} catch (final NameNotFoundException e) {
			ai = null;
		}
		return (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
	}

	/**
	 * 获取当前App的图标
	 * 
	 * @param
	 * @return
	 */
	public static Drawable getAppIcon(Activity instance) {
		PackageManager pManager = instance.getPackageManager();
		return pManager.getApplicationIcon(instance.getApplicationInfo());
	}

	/**
	 * 获取当前App的名字
	 * 
	 * @param
	 * @return
	 */
	public static String getPackageName(Activity instance) {
		PackageManager pManager = instance.getPackageManager();
		return pManager.getApplicationLabel(instance.getApplicationInfo())
				.toString();
	}

	/**
	 * 获取资源ID
	 * 
	 * @param context
	 * @param name
	 * @param defType
	 * @return
	 */
	private static int getId(Context context, String name, String defType) {
		return context.getResources().getIdentifier(name, defType,
				context.getPackageName());
	}

	/**
	 * 获取图片id
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static int getDrawable(Context context, String name) {
		return getId(context, name, "drawable");
	}

	/**
	 * 获取View中控件Id
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static int getId(Context context, String name) {
		return getId(context, name, "id");
	}

	/**
	 * 获取layout
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static int getLayout(Context context, String name) {
		return getId(context, name, "layout");
	}

	public static int[] getStyleable(Context context, String name) {
		int id = getId(context, name, "declare-styleable");
		return context.getResources().getIntArray(id);
	}

	/**
	 * 获取id
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static int getStringID(Context context, String name) {
		return getStringID(context, name, "string");
	}

	/**
	 * 获取资源ID
	 * 
	 * @param context
	 * @param name
	 * @param defType
	 * @return
	 */
	private static int getStringID(Context context, String name, String defType) {
		return context.getResources().getIdentifier(name, defType,
				context.getPackageName());
	}

	/**
	 * 获取字符串
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static String getString(Context context, String name) {
		return context.getString(getId(context, name, "string"));
	}

	/**
	 * 获取字符串
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static String getString(Context context, String name,
			String formatArgs) {
		return context.getString(getId(context, name, "string"), formatArgs);
	}

	/**
	 * 获取样式
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static int getStyle(Context context, String name) {
		return getId(context, name, "style");
	}

	/**
	 * 获取dimen
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static int getDimen(Context context, String name) {
		return getId(context, name, "dimen");
	}

	/**
	 * 获取color
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static int getColor(Context context, String name) {
		return context.getResources().getColor(getId(context, name, "color"));
	}

	/**
	 * 获取attr
	 * 
	 * @param context
	 * @param name
	 */
	public static int getAttr(Context context, String name) {
		return getId(context, name, "attr");
	}

	/**
	 * 获取anim
	 * 
	 * @param context
	 * @param name
	 */
	public static int getAnim(Context context, String name) {
		return getId(context, name, "anim");
	}

	/**
	 * 拼接存储在本地的文件名字
	 * 
	 * @param
	 * @return
	 */
	public static String encodeFileName(String input) {
		return MD5Util.encrypt(input);
	}

	public static void initResourctID(Class<?> clazz) {
		StringBuilder sb = new StringBuilder();
		R2String(clazz, sb, null);

		createFile(sb.toString());
		/*
		 * Class<?> R = Class.forName(rName); Class<?>[] clss =
		 * R.getDeclaredClasses(); for (int i = 0; i < clss.length; i++) {
		 * Class<?> cls = clss[i]; Field[] flds = cls.getDeclaredFields(); for
		 * (int j = 0; j < flds.length; j++) { Field fld = flds[j];
		 * fld.setAccessible(true); Object obj = cls.newInstance(); String name
		 * = cls.getSimpleName() + "." + fld.getName(); int id =
		 * context.getResourceId(name); fld.set(obj, id); } }
		 */
	}

	/**
	 * 把当前R.java文件反射出来，生成String
	 * 
	 * @param clazz
	 * @param sb
	 * @param value
	 * @return
	 */
	private static void R2String(Class clazz, StringBuilder sb, String value) {
		if (clazz == null)
			throw new NullPointerException("Copy2R Method init R.java resouce "
					+ clazz.getSimpleName() + " error");

		if (sb == null)
			throw new NullPointerException(
					"Copy2R Method StringBuilder in sb error");

		sb.append("public final class ").append(clazz.getSimpleName())
				.append(" {").append(ENTER);

		if (!TextUtils.isEmpty(value)) {
			value = value + "|" + clazz.getSimpleName();
		} else {
			value = clazz.getSimpleName();
		}

		// 获取class
		Class[] classes = clazz.getDeclaredClasses();
		if (classes.length > 0) {
			int count = 0;
			while (count < classes.length) {
				R2String(classes[count], sb, value);
				count++;
			}
		}

		// 获取当前class 下所有字段
		Field[] fields = clazz.getDeclaredFields();
		if (fields.length > 0) {
			int count = 0;
			while (count < fields.length) {
				String fieldPath = value + "|" + fields[count].getName();
				sb.append("public static String ")
						.append(fields[count].getName()).append("=")
						.append(fieldPath).append(";").append(ENTER);
				count++;
			}
		}
		sb.append(ENTER).append("}").append(ENTER);
	}

	public static void createFile(String content) {
		// String name = CommonUtils.class.getName();
		// String path =
		// CommonUtils.class.getClass().getResource("/").getPath();
		// String property = System.getProperty("user.dir");
		File file = new File("R.java");
		String path2 = file.getPath();
		String absolutePath2 = file.getAbsolutePath();
		String absolutePath = file.getAbsolutePath();
		FileOutputStream os = null;
		PrintWriter pw = null;

		// 没有文件就创建文件
		if (!file.exists()) {
			try {
				file.createNewFile();
				os = new FileOutputStream(file);
				os.write(content.getBytes());
				pw = new PrintWriter(os);
				pw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (pw != null) {
					pw.close();
				}
			}
		}

	}

	public static int getResId(Context ctx, String id) {
		if (TextUtils.isEmpty(id))
			throw new NullPointerException("resourece id  is null!");

		if (ctx == null)
			throw new NullPointerException("Context is null");

		String[] ids = id.split("\\|");
		if (ids.length != 3)
			return -1;
		String name = ids[2];
		String type = ids[1];
		return ctx.getResources().getIdentifier(name, type,
				ctx.getPackageName());
	}
}