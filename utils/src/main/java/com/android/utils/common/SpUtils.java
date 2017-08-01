package com.android.utils.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Map;

/**
 * description: sp 的操作工具类
 * author: Simon
 * created at 2017/8/1 下午4:01
 */

public class SpUtils {

    private static Context context = null;//上下文
    private static final String DEFAULT_NAME = "share_data";//默认名称
    private static String FILE_NAME = DEFAULT_NAME;//保存的名称

    //缓存的sp、editer
    private volatile static SharedPreferences sp;
    private volatile static SharedPreferences.Editor editor;

    private SpUtils() {
        new AssertionError("不允许初始化此实例!");
    }

    public static void init(Context context) {
        init(context, DEFAULT_NAME);
    }

    public static void init(Context context, String name) {
        if (context != null && !TextUtils.isEmpty(name)) {
            SpUtils.context = context;
            SpUtils.FILE_NAME = name;
        } else {
            throw new NullPointerException("context或者name无效！重新初始化=>SpUtils.init()");
        }
    }


    public static <T> void put(String key, T t) {
        checkConfig();//检查当前环境
        ifNotExistSpCreate();//创建sp
        ifNotExistEditorCreate();//创建editor
        checkValue(t);//验证当前的值
        //存储数据
        if (t instanceof String) {
            editor.putString(key, (String) t);
        } else if (t instanceof Integer) {
            editor.putInt(key, (Integer) t);
        } else if (t instanceof Boolean) {
            editor.putBoolean(key, (Boolean) t);
        } else if (t instanceof Float) {
            editor.putFloat(key, (Float) t);
        } else if (t instanceof Long) {
            editor.putLong(key, (Long) t);
        } else {
            editor.putString(key, t.toString());
        }
        //提交
        editor.commit();
    }

    private static <T> void checkValue(T t) {
        if (t == null) {
            throw new NullPointerException("传入的值不允许为null");
        }
    }

    private static void ifNotExistSpCreate() {
        if (sp == null) {
            synchronized (SpUtils.class) {
                if (sp == null) {
                    sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
                }
            }
        }
    }

    private static void ifNotExistEditorCreate() {
        if (editor == null) {
            synchronized (SpUtils.class) {
                if (editor == null) {
                    checkSp();
                    editor = sp.edit();
                }
            }
        }
    }


    private static void checkConfig() {
        //判断上下文环境和名称是否符合规范
        if (context == null || TextUtils.isEmpty(FILE_NAME))
            throw new RuntimeException("当前缺少必备的运行环境，是否运行=>SpUtils.init() ?");
    }

    private static void checkSp() {
        //判断上下文环境和名称是否符合规范
        if (sp == null)
            throw new RuntimeException("当前运行出错，错误信息：sp初始化失败！");
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @return
     */
    public static <T> T get(String key, T t) {
        checkConfig();//检查当前环境
        ifNotExistSpCreate();//创建sp
        checkValue(t);//验证当前的值
        checkSp();//检查sp
        if (t instanceof String) {
            return (T) sp.getString(key, (String) t);
        } else if (t instanceof Integer) {
            return (T) new Integer(sp.getInt(key, (Integer) t));
        } else if (t instanceof Boolean) {
            return (T) new Boolean(sp.getBoolean(key, (Boolean) t));
        } else if (t instanceof Float) {
            return (T) new Float(sp.getFloat(key, (Float) t));
        } else if (t instanceof Long) {
            return (T) new Long(sp.getLong(key, (Long) t));
        }

        return null;
    }

    //如果不传入default 则使用默认的请求方法
    public static String getString(String key) {
        return get(key, "");
    }

    public static int getInt(String key) {
        return get(key, 0);
    }

    public static boolean getBool(String key) {
        return get(key, false);
    }

    public static float getFloat(String key) {
        return get(key, 0f);
    }

    public static Long getLong(String key) {
        return get(key, 0L);
    }


    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        checkConfig();//检查当前环境
        ifNotExistSpCreate();//创建sp
        ifNotExistEditorCreate();//创建editor
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        checkConfig();//检查当前环境
        ifNotExistSpCreate();//创建sp
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        checkConfig();//检查当前环境
        ifNotExistSpCreate();//创建sp
        checkValue(key);//检查key
        checkSp();//检查sp
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public static Map<String, ?> getAll() {
        checkConfig();//检查当前环境
        ifNotExistSpCreate();//创建sp
        checkSp();//检查sp
        return sp.getAll();
    }


}
