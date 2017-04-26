package sinosoft.com.mynet.sevenHttp;

import android.util.Log;

import com.trello.rxlifecycle.android.ActivityEvent;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sinosoft.com.mynet.eightSubscribers.ProgressSubscriber;
import sinosoft.com.mynet.elevenApp.RxRetrofitApp;
import sinosoft.com.mynet.oneResult.BaseApi;
import sinosoft.com.mynet.tenCookie.CookieInterceptor;
import sinosoft.com.mynet.twoListener.HttpOnNextListener;

/**
 * Created by ji94 on 2017/4/25.
 */

public class HttpManager {
    private volatile static HttpManager INSTANCE;


    public HttpManager() {
    }

    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
    public void doHttpDeal(BaseApi basePar) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(basePar.getConnectionTime(), TimeUnit.SECONDS);
//        添加拦截器 仅仅是打印，为了例子简单，我吧元项目的 db持久化数据存储删除了。暂时删除了。
//        builder.addInterceptor(new CookieInterceptor(basePar.isCache(), basePar.getUrl()));
        if (RxRetrofitApp.isDebug()) {
            builder.addInterceptor(getHttpLoggingInterceptor());
        }

        /*创建retrofit对象*/
        Retrofit retrofit = new Retrofit.Builder()
//                客户端塞进去，这里是OKhttp和retrofit结合
                .client(builder.build())
// 适配器就是解释在rxjava中是不是见过Call方法的，看到了吗。
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(basePar.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        /*rx处理 订阅者*/
        ProgressSubscriber subscriber = new ProgressSubscriber(basePar);
//        被订阅者
        Observable observable = basePar.getObservable(retrofit)
                /*失败后的retry配置*/
                .retryWhen(new RetryWhenNetworkException(basePar.getRetryConut(),
                        basePar.getRetryDelay(), basePar.getRetryIncreateDalay()))
                /*生命周期管理*/
//                .compose(basePar.getRxAppCompatActivity().bindToLifecycle())
                .compose(basePar.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.PAUSE))
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*结果判断 内容解析*/
                .map(basePar);

 /*链接式对象返回 */
        SoftReference<HttpOnNextListener> httpOnNextListener = basePar.getListener();
        if (httpOnNextListener != null && httpOnNextListener.get() != null) {
            httpOnNextListener.get().onNext(observable);
        }

        /*数据回调 订阅*/
        observable.subscribe(subscriber);

    }

    //    打印模板
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("RxRetrofit", "Retrofit====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }
}
