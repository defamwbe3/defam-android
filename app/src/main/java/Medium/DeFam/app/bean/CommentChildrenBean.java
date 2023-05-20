package Medium.DeFam.app.bean;

import java.util.List;

public class CommentChildrenBean {
    private String total;
    private List<CommentBean.ChildrenBean> data;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<CommentBean.ChildrenBean> getData() {
        return data;
    }

    public void setData(List<CommentBean.ChildrenBean> data) {
        this.data = data;
    }
}
