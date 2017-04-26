package sinosoft.com.mynet;

import android.app.Application;
import android.content.Context;

import sinosoft.com.mynet.elevenApp.RxRetrofitApp;

/**
 * Created by ji94 on 2017/4/26.
 */

public class MyApplication extends Application {
    public static Context app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = getApplicationContext();
        RxRetrofitApp.init(this, BuildConfig.DEBUG);
    }
}
