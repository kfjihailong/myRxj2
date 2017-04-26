package sinosoft.com.mynet.oneResult;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;
import sinosoft.com.mynet.threeException.HttpTimeException;
import sinosoft.com.mynet.twoListener.HttpOnNextListener;

/**
 * func1 是rejava的一个接口，第一个泛型参数是，第一输入类型，第二个是返回类型
 * <p>
 * Created by ji94 on 2017/4/25.
 */

public abstract class BaseApi<T> implements Func1<BaseResultEntity<T>, T> {
    // rx生命周期管理
    private SoftReference<RxAppCompatActivity> rxAppCompatActivity;
    //     回调监听
    private SoftReference<HttpOnNextListener> listener;
    /*是否能取消加载框*/
    private boolean cancel;
    //    是否显示加载框
    private boolean showProgress;
    //    是否需要缓冲处理
    private boolean cache;
    //    基础URL
    private final static String baseUrl = "https://www.izaodao.com/Api/";
    //    方法 -  如果需要缓缓存 必须设置这个参数
    private String mothed;
    //    超时时间默认6秒
    private int connectionTime = 6;
    //    有网络情况下本地缓存时间默认60秒
    private int cookieNetWorkTime = 60;
    //    无网络的情况下本地缓存时间默认是30天;
    private int cookieNoNetWorkTime = 24 * 60 * 60 * 30;
    //    失败后尝试重新刷新次数
    private int retryConut = 1;
    //    失败后retry延迟
    private long retryDelay = 100;
    //    失败后retry叠加延迟
    private long retryIncreateDalay = 10;


    public BaseApi(RxAppCompatActivity rxAppCompatActivity, HttpOnNextListener listener) {
        setRxAppCompatActivity(rxAppCompatActivity);
        setListener(listener);
        setShowProgress(true);
        setCache(true);
    }

    public abstract Observable getObservable(Retrofit retrofit);

    public  RxAppCompatActivity getRxAppCompatActivity() {
        return rxAppCompatActivity.get();
    }

    public void setRxAppCompatActivity(RxAppCompatActivity rxAppCompatActivity) {
        this.rxAppCompatActivity = new SoftReference(rxAppCompatActivity);
    }

    public SoftReference<HttpOnNextListener> getListener() {
        return listener;
    }
    public void setListener(HttpOnNextListener listener) {
        this.listener = new SoftReference(listener);
    }


    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public String getUrl() {
        return baseUrl + mothed;
    }

    public String getMothed() {
        return mothed;
    }

    public void setMothed(String mothed) {
        this.mothed = mothed;
    }

    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public void setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
    }

    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
    }

    public void setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
    }

    public int getRetryConut() {
        return retryConut;
    }

    public void setRetryConut(int retryConut) {
        this.retryConut = retryConut;
    }

    public long getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
    }

    public long getRetryIncreateDalay() {
        return retryIncreateDalay;
    }

    public void setRetryIncreateDalay(long retryIncreateDalay) {
        this.retryIncreateDalay = retryIncreateDalay;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }
//    重载了一个call方法，这个不用解释了吧，大名鼎鼎的call 就是把结果一个一个输出出来
    @Override
    public T call(BaseResultEntity<T> httpResult) {
        if (httpResult.getRet() == 0) {
            throw new HttpTimeException(httpResult.getMsg());
        }
        return httpResult.getData();
    }
}