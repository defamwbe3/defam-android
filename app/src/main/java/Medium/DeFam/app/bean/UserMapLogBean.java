package Medium.DeFam.app.bean;

import java.util.List;

public class UserMapLogBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean {
 private String id;//":5,
        private String type;//":"map",
        private String member_id;//":1,
        private String map_name;//":"测试",
        private String image;//":"https:\/\/api.cjlbzx.szyqa.com\/images\/20221221\/226b6bb8a9e1b86bdf39c3ee1cc5da3c.jpg",
        private String num;//":1,
        private String detail;//":"<p>111111<\/p>",
        private String is_exchange;//":0,
        private String need_num;//":2,
        private String map_id;//":2,
        private String created_at;//":"2022-12-28 14:56:23",
        private String updated_at;//":"2022-12-28 14:56:23",
        private String deleted_at;//":null,
        private String source_type;//":null,
        private String status;//":0,
        private String suipian_name;//":

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

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getMap_name() {
            return map_name;
        }

        public void setMap_name(String map_name) {
            this.map_name = map_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getIs_exchange() {
            return is_exchange;
        }

        public void setIs_exchange(String is_exchange) {
            this.is_exchange = is_exchange;
        }

        public String getNeed_num() {
            return need_num;
        }

        public void setNeed_num(String need_num) {
            this.need_num = need_num;
        }

        public String getMap_id() {
            return map_id;
        }

        public void setMap_id(String map_id) {
            this.map_id = map_id;
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

        public String getSource_type() {
            return source_type;
        }

        public void setSource_type(String source_type) {
            this.source_type = source_type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSuipian_name() {
            return suipian_name;
        }

        public void setSuipian_name(String suipian_name) {
            this.suipian_name = suipian_name;
        }
    }
}
