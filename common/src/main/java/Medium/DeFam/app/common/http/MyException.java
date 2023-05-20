package Medium.DeFam.app.common.http;


public class MyException extends IllegalStateException {

    private JsonBean errorBean;

    public MyException(JsonBean responseBean) {
        super("{\"code\":" + responseBean.getCode() + ",\"message\":\"" + responseBean.getMessage() + "\"}");
        errorBean = responseBean;
    }

    public JsonBean getErrorBean() {
        return errorBean;
    }
}

