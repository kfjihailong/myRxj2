package sinosoft.com.mynet.twoListener;

import rx.Observable;

/**
 * 成功后的正确处理方式
 * Created by ji94 on 2017/4/25.
 */

public abstract class HttpOnNextListener<T> {
    /**
     * rejava 制定下一个的方法
     */

    public abstract void onNext(T t);

    /**
     * 缓冲返回结果
     */

    public void onCacheNext(String string) {

    }

    /**
     * 成功后的ober返回，扩展链接式调用 观察者的模式
     */
    public void onNext(Observable observable) {

    }

    /**
     * 失败或错误调用的方法
     */
    public void onError(Throwable e) {

    }

    /***
     * 取消回调方法
     *
     * */
    public void onCancel() {

    }

}
