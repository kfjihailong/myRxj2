package sinosoft.com.mynet.oneResult;

/**
 * 一套标准的结果返回的接口
 * Created by ji94 on 2017/4/25.
 */

public class BaseResultEntity<T> {
    //    错误判断
    private int ret;
    // 提示信息
    private String msg;
    //    返回的数据
    private T data;


    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
