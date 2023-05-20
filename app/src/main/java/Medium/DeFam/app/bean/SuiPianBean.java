package Medium.DeFam.app.bean;

import java.util.List;

public class SuiPianBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean {
  private String id;//":80,
        private String fragment_id;//":12,
        private String name;//":"卡通4",
        private String image;//":"https:\/\/api.cjlbzx.szyqa.com\/images\/20230218\/7c5f67c2bdc9ba99d080f85e39735bc9.jpeg",
        private String number;//":135,
        private String user_id;//":30,
        private String created_at;//":"2023-02-18 14:53:36",
        private String updated_at;//":"2023-02-18 14:53:36",
        private String deleted_at;//":null,
        private String is_exchange;//":0,
        private String source_type;//":

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFragment_id() {
            return fragment_id;
        }

        public void setFragment_id(String fragment_id) {
            this.fragment_id = fragment_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getIs_exchange() {
            return is_exchange;
        }

        public void setIs_exchange(String is_exchange) {
            this.is_exchange = is_exchange;
        }

        public String getSource_type() {
            return source_type;
        }

        public void setSource_type(String source_type) {
            this.source_type = source_type;
        }
    }
}
