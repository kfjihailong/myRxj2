package sinosoft.com.mynet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.List;

import sinosoft.com.mynet.fourEntity.SubjectPostApi;
import sinosoft.com.mynet.oneResult.BaseResultEntity;
import sinosoft.com.mynet.sevenHttp.HttpManager;
import sinosoft.com.mynet.sixResulte.SubJectResulte;
import sinosoft.com.mynet.twoListener.HttpOnNextListener;

public class MainActivity extends RxAppCompatActivity {
    private TextView tvMsg;
    private Button btn_simple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        btn_simple = (Button) findViewById(R.id.btn_simple);
        btn_simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleDo();
            }
        });

    }


    /*************************************************封装完请求*******************************************************/

    private void simpleDo() {
//        一个接口实现类 其实最核心的目的就是实现，retrofit的方法url注入的。
        SubjectPostApi postEntity = new SubjectPostApi(this, simpleOnNextListener);
        postEntity.setAll(true);
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(postEntity);
    }

    //   在主线程的函数回调，用于回写数据。
    HttpOnNextListener simpleOnNextListener = new HttpOnNextListener<List<SubJectResulte>>() {
        @Override
        public void onNext(List<SubJectResulte> subjects) {
            tvMsg.setText("网络返回：\n" + subjects.toString());
        }

        @Override
        public void onCacheNext(String cache) {
            /*缓存回调*/
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<BaseResultEntity<List<SubJectResulte>>>() {
            }.getType();
            BaseResultEntity resultEntity = gson.fromJson(cache, type);
            tvMsg.setText("缓存返回：\n" + resultEntity.getData().toString());
        }

        /*用户主动调用，默认是不需要覆写该方法*/
        @Override
        public void onError(Throwable e) {
            super.onError(e);
            tvMsg.setText("失败：\n" + e.toString());
        }

        /*用户主动调用，默认是不需要覆写该方法*/
        @Override
        public void onCancel() {
            super.onCancel();
            tvMsg.setText("取消請求");
        }
    };


}
