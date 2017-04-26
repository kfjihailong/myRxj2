package sinosoft.com.mynet.sixResulte;

import java.io.PrintStream;
import java.util.List;

/**
 * 直接请求返回数据格式
 * Created by ji94 on 2017/4/25.
 */

public class RetrofitEntity {
    private int ret;
    private String msg;
    private List<SubJectResulte> data;

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

    public List<SubJectResulte> getData() {
        return data;
    }

    public void setData(List<SubJectResulte> data) {
        this.data = data;
    }
}
