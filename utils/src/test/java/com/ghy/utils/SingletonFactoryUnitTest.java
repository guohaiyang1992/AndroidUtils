package com.ghy.utils;

import com.android.utils.common.SingletonFactory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by simon on 2017/6/1.
 * SingletonFactory的单元测试类，主要测试它的生成方法
 */

public class SingletonFactoryUnitTest {
    //无参数创建方法
    @Test
    public void create_instance() {
        A a = SingletonFactory.getInstance(A.class);
        assertNotNull(a);
    }

    // 非包装类型创建方法
    @Test
    public void create_instance1() {
        A a = SingletonFactory.getInstance(A.class, false, 2);
        assertNotNull(a);
    }

    // 包装类型创建方法
    @Test
    public void create_instance2() {
        Integer b = new Integer(1);
        A a = SingletonFactory.getInstance(A.class, true, b);
        assertNotNull(a);
    }

    // 包装和非包装类型创建方法
    @Test
    public void create_instance3() {
        Integer b = new Integer(1);
        int c = 2;
        A a = SingletonFactory.getInstance(A.class, new Class[]{int.class, Integer.class}, new Object[]{c, b});
        assertNotNull(a);
    }

    //模拟的类
    public static class A {
        int a = 0;
        Integer b = 2;

        public A(int a) {
            this.a = a;
        }

        public A() {
        }

        public A(Integer b) {
            this.b = b;
        }

        public A(int a, Integer b) {
            this.a = a;
            this.b = b;
        }
    }


}
