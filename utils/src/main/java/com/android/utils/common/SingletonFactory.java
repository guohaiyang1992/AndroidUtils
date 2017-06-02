package com.android.utils.common;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 单例工厂
 * 用于使用单例的方式创建对象
 * <p>
 */
public class SingletonFactory {

    public static final Logger LOG = Logger.getLogger(SingletonFactory.class.getCanonicalName());

    private static final Map<Class<?>, Object> INSTANCE_MAP = Collections.synchronizedMap(new HashMap<Class<?>, Object>());  //同步的map

    private static final Object[] EMPTY_ARGS = new Object[0]; //没有参数

    /**
     * <pre>
     * 用此方法有一个前提:
     * 参数类必须有显示声明的无参构造器或没有任何显示声明的构造器  **常用**
     * </pre>
     */
    public static <T> T getInstance(Class<? extends T> clazz) {
        return getInstance(clazz, false, EMPTY_ARGS);
    }


    /**
     * 获取实例
     * <p>
     * 对于一般类型：是包装类的运行此方法   **常用**
     *
     * @param clazz    --需要实例化的类
     * @param args     --需要传入的参数(可多个)
     * @param <T>      --实例化的类型
     * @param isObject --是否全部是包装类（例如Interger）
     * @return
     */
    public static <T> T getInstance(Class<? extends T> clazz, boolean isObject, Object... args) {
        //后面使用了，需要检查参数
        checkClass(clazz);
        checkArgs(args);
        //获取实例
        Object object = INSTANCE_MAP.get(clazz);
        if (object == null) {
            Class<?>[] parameterTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                if (!isObject) {
                    parameterTypes[i] = changeClassToNormal(args[i].getClass());
                } else {
                    parameterTypes[i] = args[i].getClass();
                }

            }
            return getInstance(clazz, parameterTypes, args);
        }
        return clazz.cast(object);
    }


    /**
     * 用此方法有三个前提:
     * 1. 两个参数数组同时为null或同时不为null
     * 2. 两个数组的长度必须相等
     * 3. parameterTypes如果不空null, 则其各元素不能为null
     * <p>
     * 既有包装类型又有一般类型运行此方法   **常用**
     *
     * @param parameterTypes --属性的类型集合
     * @param clazz          --需要实例化的类
     * @param args           --需要传入的参数
     * @param <T>            --实例化的类型
     * @return
     */
    public static <T> T getInstance(Class<? extends T> clazz, Class<?>[] parameterTypes, Object[] args) {
        //后面使用了需要检查参数
        checkClass(clazz);
        checkArgs(args);
        checkType(parameterTypes);
        //获取实例
        Object object = INSTANCE_MAP.get(clazz);
        if (object == null) { // 检查实例,如是不存在就进行同步代码区  
            synchronized (clazz) { // 对其进行锁,防止两个线程同时进入同步代码区  
                // 双重检查,非常重要,如果两个同时访问的线程,当第一线程访问完同步代码区后,生成一个实例;当第二个已进入getInstance方法等待的线程进入同步代码区时,也会产生一个新的实例  
                if (object == null) {
                    try {
                        if (parameterTypes != null && args != null) {
                            if (parameterTypes.length == args.length) {
                                Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);
                                constructor.setAccessible(true);
                                T instance = clazz.cast(constructor.newInstance(args));
                                INSTANCE_MAP.put(clazz, instance);
                                return instance;
                            } else {
                                throw new IllegalArgumentException("参数个数不匹配");
                            }
                        } else if (parameterTypes == null && args == null) {
                            T instance = clazz.newInstance();
                            INSTANCE_MAP.put(clazz, instance);
                            return instance;
                        } else {
                            throw new IllegalArgumentException("两个参数数组必须同时为null或同时不为null");
                        }
                    } catch (Exception e) {
                        LOG.log(Level.SEVERE, "创建实例失败", e);
                    }
                }
            }
        }
        return (T) object;
    }

    /**
     * 检查函数
     *
     * @param clazz --检查class是否为null
     */
    private static void checkClass(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("需要实例化的类不能为null!");
        }
    }

    /**
     * 检查函数
     *
     * @param args --检查参数是否为null
     */
    private static void checkArgs(Object... args) {
        if (args == null) {
            throw new NullPointerException("传入的参数不能为null！");
        }
    }

    /**
     * 检查函数
     *
     * @param paramTypes --检查类型是否为null
     */
    private static void checkType(Class<?>... paramTypes) {
        if (paramTypes == null) {
            throw new NullPointerException("传入的类型不能为null！");
        }
    }

    /**
     * 将包装类转为默认的普通类型
     *
     * @param clazz --传入的类型
     * @return --转化后的类型
     */
    public static Class<?> changeClassToNormal(Class<?> clazz) {
        String className = clazz.getSimpleName();
        if (className.equals(Integer.class.getSimpleName())) {
            return int.class;
        } else if (className.equals(Float.class.getSimpleName())) {
            return float.class;
        } else if (className.equals(Double.class.getSimpleName())) {
            return double.class;
        } else if (className.equals(Long.class.getSimpleName())) {
            return long.class;
        } else if (className.equals(Character.class.getSimpleName())) {
            return char.class;
        } else if (className.equals(Boolean.class.getSimpleName())) {
            return boolean.class;
        } else if (className.equals(Short.class.getSimpleName())) {
            return short.class;
        } else if (className.equals(Byte.class.getSimpleName())) {
            return byte.class;
        }


        return clazz;
    }
} 