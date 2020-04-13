package com.coolweather.coolweatherjetpack.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.coolweather.coolweatherjetpack.R;

/**
 * author: wangc
 * date: 2020/1/13 10:51 
*/
public class Welcome extends Activity {
    private final static String TARGET_ONE = "1";
    private final static String TARGET_TWO = "2";

    public final static String TEXT_EXTRA = "text";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Intent intent = getIntent();
        if (null == intent)
            return;

        for (int a = 10;a<10;a++){

        }
        Uri uri = intent.getData();
        if (null == uri)
            return;

        String pageTarget = uri.getQueryParameter("page");
        String pageText = uri.getQueryParameter("text");
        Log.e("+++", "pageTarget:" + pageTarget);
        Log.e("+++", "pageText" + pageText);
        if (TextUtils.isEmpty(pageTarget))
            pageTarget = "";
        if (TextUtils.isEmpty(pageText))
            pageText = "";

        Intent launchIntent;
        switch (pageTarget) {
            default:
            case TARGET_ONE:
                Toast.makeText(this, "去页面：" + pageTarget + "\n" + "text： " + pageText, Toast.LENGTH_LONG).show();
                break;
            case TARGET_TWO:
                Toast.makeText(this, "去页面：" + pageTarget + "\n" + "text： " + pageText, Toast.LENGTH_LONG).show();
                startActivity(new Intent(Welcome.this, WebViewActivity.class));
                break;
        }
//        launchIntent.putExtra(TEXT_EXTRA, pageText);
//        startActivity(launchIntent);
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }
}
