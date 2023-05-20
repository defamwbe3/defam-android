package Medium.DeFam.app.common.http;

import java.io.Serializable;

/**
 * Created by zmh on 2017/8/5.
 */

public class JsonBean <T> implements Serializable {
    //交易所返回
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
