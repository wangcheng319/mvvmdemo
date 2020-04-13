package com.coolweather.coolweatherjetpack.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wangc on 2016/11/16
 * E-MAIL:274281610@QQ.COM
 */

public class L {

    //是否打印
    private static boolean isLog = false;
    //打印标签
    private static String logtag = "logtag";
    /**
     * logcat在实现上对于message的内存分配大概是4k左右,
     * 所以超过的内容都直接被丢弃,设置文本长度超过3000分多条打印
     */
    private final static int LOG_MAXLENGTH = 3000;

    //记录上次的logLocation
    private static String lastLogMethod = "";

    /**
     * 打印文本
     *
     * @param text
     */
    public static void i(String text) {
        if (isLog) {
            Log.i(logtag, logContent(text) + logLocation());
        }

    }

    /**
     * 打印异常文本
     *
     * @param text
     */
    public static void e(String text) {
        if (isLog) {
            Log.e(logtag, logContent(text) + logLocation());
        }
    }

    /**
     * 打印异常
     *
     * @param msg
     * @param e
     */
    public static void e(String msg, Exception e) {
        if (isLog) {
            Log.e(logtag, msg + logLocation(), e);
        }
    }


    /**
     * 打印json格式文本
     *
     * @param json
     */
    public static void json(String json) {
        if (isLog) {
            json("", json);
        }
    }

    /**
     * 打印json格式文本
     *
     * @param prefix 前缀文本
     * @param json
     */
    public static void json(String prefix, String json) {
        if (isLog) {
            String text = prefix + fomatJson(json);
            Log.i(logtag, logContent(text) + logLocation());
        }
    }


    /**
     * 打印内容
     *
     * @param text
     * @return
     */
    private static String logContent(String text) {
        if (text.length() < 50) {//内容长度不超过50，前面加空格加到50
            int minLeng = 50 - text.length();
            // Log.i(logtag, "leng========" + leng + "   " + text.length());
            if (minLeng > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < minLeng; i++) {
                    stringBuilder.append(" ");
                }
                text = text + stringBuilder.toString();
            }
        } else if (text.length() > LOG_MAXLENGTH) {//内容超过logcat单条打印上限，分批打印
            //Log.i(logtag,"text长度========="+text.length());
            int logTime = text.length() / LOG_MAXLENGTH;
            for (int i = 0; i < logTime; i++) {
                String leng = text.substring(i * LOG_MAXLENGTH, (i + 1) * LOG_MAXLENGTH);
                //提示
                if (i == 0) {
                    Log.i(logtag, "打印分" + (logTime+1) + "条显示 :" + leng);
                } else {
                    Log.i(logtag, "接上条↑" + leng);
                }

            }
            text = text.substring(logTime * LOG_MAXLENGTH, text.length());
        }
        return text;
    }

    /**
     * 定位打印的方法
     *
     * @return
     */
    private static StringBuilder logLocation() {
        StackTraceElement logStackTrace = getLogStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("   ☞.(").append(logStackTrace.getFileName()).append(":").append(logStackTrace.getLineNumber() + ")");
        //Log.i(logtag, "leng========" + stringBuilder + "   " + lastLogMethod);
        if (stringBuilder.toString().equals(lastLogMethod)) {
            stringBuilder = new StringBuilder("");
        } else {
            lastLogMethod = stringBuilder.toString();
        }

        return stringBuilder;
    }


    /**
     * json格式化
     *
     * @param jsonStr
     * @return
     */
    private static String fomatJson(String jsonStr) {
        try {
            jsonStr = jsonStr.trim();
            if (jsonStr.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(jsonStr);
                return jsonObject.toString(2);
            }
            if (jsonStr.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(jsonStr);
                return jsonArray.toString(2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Json格式有误: " + jsonStr;
    }


    /**
     * 获取打印的方法栈
     *
     * @return
     */
    private static StackTraceElement getLogStackTrace() {
        StackTraceElement logTackTraces = null;
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTraces.length; i++) {
            StackTraceElement stackTrace = stackTraces[i];
            if (stackTrace.getClassName().equals(L.class.getName())) {
                //getLogStackTrace--logLocation--i/e/json--方法栈,所以方法栈的位置是当前方法栈后的第3个
                logTackTraces = stackTraces[i + 3];
                i = stackTraces.length;
            }
        }
        return logTackTraces;
    }


    public static void setLogtag(String logtag) {
        L.logtag = logtag;
    }


    public static void setIsLog(boolean isLog) {
        L.isLog = isLog;
    }
}
