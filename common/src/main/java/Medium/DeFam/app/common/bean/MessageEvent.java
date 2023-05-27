package Medium.DeFam.app.common.bean;

import java.io.Serializable;

public class MessageEvent<T> implements Serializable {

    private int code = -1;

    private T data;

    public MessageEvent(int code) {
        this.code = code;
    }

    public MessageEvent(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}
