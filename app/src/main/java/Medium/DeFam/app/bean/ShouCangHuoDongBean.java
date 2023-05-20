package Medium.DeFam.app.bean;

import java.util.List;


public class ShouCangHuoDongBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean {
        private String id;//":14,
        private String type;//":1,
        private String action_id;//":2,
        private String to_member_id;//":null,
        private String member_id;//":1,
        private String created_at;//":"2022-12-22 13:21:03",
        private String updated_at;//":"2022-12-22 13:21:03",
        private String deleted_at;//":0,
        HuoDongDetailBean action_txt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAction_id() {
            return action_id;
        }

        public void setAction_id(String action_id) {
            this.action_id = action_id;
        }

        public String getTo_member_id() {
            return to_member_id;
        }

        public void setTo_member_id(String to_member_id) {
            this.to_member_id = to_member_id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getDeleted_at() {
            return deleted_at;
        }

        public void setDeleted_at(String deleted_at) {
            this.deleted_at = deleted_at;
        }

        public HuoDongDetailBean getAction_txt() {
            return action_txt;
        }

        public void setAction_txt(HuoDongDetailBean action_txt) {
            this.action_txt = action_txt;
        }
    }
}
