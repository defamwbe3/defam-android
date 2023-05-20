package Medium.DeFam.app.bean;

import java.util.List;

public class BannerBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean {
 private String id;//":5,
        private String title;//":"资讯2",
        private String image;//":"http:\/\/cjlbzx.demo.cc\/images\/20221216\/de59896d121cfc4967e3f03f9aaf1d59.jpg",
        private String is_jump;//":0,
        private String jump_url;//":"",
        private String code;//":"realinfo",
        private String created_at;//":"2022-12-16 15:45:52",
        private String updated_at;//":

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getIs_jump() {
            return is_jump;
        }

        public void setIs_jump(String is_jump) {
            this.is_jump = is_jump;
        }

        public String getJump_url() {
            return jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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
    }
}
