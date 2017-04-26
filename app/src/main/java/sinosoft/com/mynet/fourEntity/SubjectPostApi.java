package sinosoft.com.mynet.fourEntity;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import retrofit2.Retrofit;
import rx.Observable;
import sinosoft.com.mynet.fiveIntHttp.HttpPostService;
import sinosoft.com.mynet.oneResult.BaseApi;
import sinosoft.com.mynet.twoListener.HttpOnNextListener;

/**
 * Created by ji94 on 2017/4/25.
 */

public class SubjectPostApi extends BaseApi{

    //    接口需要传入的参数 可自定义不同类型
    private boolean all;

    public SubjectPostApi(RxAppCompatActivity rxAppCompatActivity, HttpOnNextListener listener) {
        super(rxAppCompatActivity, listener);
        setShowProgress(true);
        setCancel(true);
        setCache(true);
        setMothed("AppFiftyToneGraph/videoLink");
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24*60*60);
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }
//设置参数
    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpPostService service = retrofit.create(HttpPostService.class);
        return service.getAllVedioBys(isAll());

    }
}
