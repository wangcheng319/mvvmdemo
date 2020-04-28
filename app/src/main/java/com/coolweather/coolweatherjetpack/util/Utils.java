package com.coolweather.coolweatherjetpack.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: wangc
 * @CreateDate: 2020/4/15 15:39
 */
public class Utils {
    static String       ISDOUBLE;
    static String       SIMCARD;
    static String       SIMCARD_1;
    static String       SIMCARD_2;
    static boolean      isDouble;
    public static void initIsDoubleTelephone(Context context)
    {
        boolean isDouble = true;
        Method method = null;
        Object result_0 = null;
        Object result_1 = null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try
        {
            // 只要在反射getSimStateGemini 这个函数时报了错就是单卡手机（这是我自己的经验，不一定全正确）
            method = TelephonyManager.class.getMethod("getSimStateGemini", new Class[]
                    { int.class });
            // 获取SIM卡1
            result_0 = method.invoke(tm, new Object[]
                    { new Integer(0) });
            // 获取SIM卡2
            result_1 = method.invoke(tm, new Object[]
                    { new Integer(1) });
        } catch (SecurityException e)
        {
            isDouble = false;
            e.printStackTrace();
            // System.out.println("1_ISSINGLETELEPHONE:"+e.toString());
        } catch (NoSuchMethodException e)
        {
            isDouble = false;
            e.printStackTrace();
            // System.out.println("2_ISSINGLETELEPHONE:"+e.toString());
        } catch (IllegalArgumentException e)
        {
            isDouble = false;
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            isDouble = false;
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            isDouble = false;
            e.printStackTrace();
        } catch (Exception e)
        {
            isDouble = false;
            e.printStackTrace();
            // System.out.println("3_ISSINGLETELEPHONE:"+e.toString());
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        if (isDouble)
        {
            // 保存为双卡手机
            editor.putBoolean(ISDOUBLE, true);
            // 保存双卡是否可用
            // 如下判断哪个卡可用.双卡都可以用
            if (result_0.toString().equals("5") && result_1.toString().equals("5"))
            {
                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1"))
                {
                    editor.putString(SIMCARD, "0");
                }
                editor.putBoolean(SIMCARD_1, true);
                editor.putBoolean(SIMCARD_2, true);

                LogUtils.i("双卡可用");

            } else if (!result_0.toString().equals("5") && result_1.toString().equals("5"))
            {// 卡二可用
                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1"))
                {
                    editor.putString(SIMCARD, "1");
                }
                editor.putBoolean(SIMCARD_1, false);
                editor.putBoolean(SIMCARD_2, true);

                LogUtils.i("卡二可用");

            } else if (result_0.toString().equals("5") && !result_1.toString().equals("5"))
            {// 卡一可用
                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1"))
                {
                    editor.putString(SIMCARD, "0");
                }
                editor.putBoolean(SIMCARD_1, true);
                editor.putBoolean(SIMCARD_2, false);

                LogUtils.i("卡一可用");

            } else
            {// 两个卡都不可用(飞行模式会出现这种种情况)
                editor.putBoolean(SIMCARD_1, false);
                editor.putBoolean(SIMCARD_2, false);
                LogUtils.i("飞行模式");
            }
        } else
        {
            // 保存为单卡手机
            editor.putString(SIMCARD, "0");
            editor.putBoolean(ISDOUBLE, false);
        }
        editor.commit();
    }


    public static void printTelephonyManagerMethodNamesForThisDevice(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class<?> telephonyClass;
        try {
            telephonyClass = Class.forName(telephony.getClass().getName());
            Method[] methods = telephonyClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                LogUtils.i("\n" + methods[i] + " declared by " + methods[i].getDeclaringClass());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // 获取双卡双待 SIM 卡序列号
    public static void getSubscriberId(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class<?> telephonyClass;
        Object result = null;
        Object result0 = null;
        Object result1 = null;
        try {
            telephonyClass = Class.forName(telephony.getClass().getName());
            Method m1 = telephonyClass.getMethod("getSubscriberId");
            Method m2 = telephonyClass.getMethod("getSubscriberId", new Class[]{int.class});
            result = m1.invoke(telephony);
            result0 = m2.invoke(telephony, 0);
            result1 = m2.invoke(telephony, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.i( " getSubscriberId : " + telephony.getSubscriberId() + "\n"
                + " result : " + result + "\n"
                + " result0 : " + result0 + "\n"
                + " result1 : " + result1 + "\n");
    }
}
