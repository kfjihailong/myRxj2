package sinosoft.com.mynet.fiveIntHttp;

import java.util.List;

import retrofit.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import sinosoft.com.mynet.oneResult.BaseResultEntity;
import sinosoft.com.mynet.sixResulte.RetrofitEntity;
import sinosoft.com.mynet.sixResulte.SubJectResulte;

/**
 * 这个很明显是生成 别观察值 对象
 * Created by ji94 on 2017/4/25.
 */

public interface HttpPostService {
    @POST("AppFiftyToneGraph/videoLink")
    Call<RetrofitEntity> getAllVedio(@Body boolean once_no);

    @POST("AppFiftyToneGraph/videoLink")
    Observable<RetrofitEntity> getAllVedioBy(@Body boolean once_no);

    @FormUrlEncoded
    @POST("AppFiftyToneGraph/videoLink")
    Observable<BaseResultEntity<List<SubJectResulte>>> getAllVedioBys(@Field("once") boolean once_no);
}
