package Medium.DeFam.app.bean;

import java.util.List;

public class GuanZhuBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean {
        private String id;//":47,
        private String type;//":1,
        private String member_id;//":1,
        private String to_member_id;//":2,
        private String created_at;//":"2022-12-30 10:47:01",
        private String updated_at;//":"2022-12-30 10:47:01",
        private String deleted_at;//":0,
        private String is_gz;
        MemberBean tomember;

        public String getIs_gz() {
            return is_gz;
        }

        public void setIs_gz(String is_gz) {
            this.is_gz = is_gz;
        }

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

        public String getTo_member_id() {
            return to_member_id;
        }

        public void setTo_member_id(String to_member_id) {
            this.to_member_id = to_member_id;
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

        public MemberBean getTomember() {
            return tomember;
        }

        public void setTomember(MemberBean tomember) {
            this.tomember = tomember;
        }
    }
    public class MemberBean {
        private String id;//":12,
        private String nickname;//":"185****1229",
        private String avatar;//":"https:\/\/api.cjlbzx.szyqa.com\/icon\/avatar.png",
        private String follow_num;//":0,
        private String fans_num;//":1,
        private String is_enterprise;//":1,
        private String zan_num;//":1,
        private String article_num;//":2,
        private String activity_num;//":1,
        private String collection_num;//":3,
        private String is_read;//":

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getFollow_num() {
            return follow_num;
        }

        public void setFollow_num(String follow_num) {
            this.follow_num = follow_num;
        }

        public String getFans_num() {
            return fans_num;
        }

        public void setFans_num(String fans_num) {
            this.fans_num = fans_num;
        }

        public String getIs_enterprise() {
            return is_enterprise;
        }

        public void setIs_enterprise(String is_enterprise) {
            this.is_enterprise = is_enterprise;
        }

        public String getZan_num() {
            return zan_num;
        }

        public void setZan_num(String zan_num) {
            this.zan_num = zan_num;
        }

        public String getArticle_num() {
            return article_num;
        }

        public void setArticle_num(String article_num) {
            this.article_num = article_num;
        }

        public String getActivity_num() {
            return activity_num;
        }

        public void setActivity_num(String activity_num) {
            this.activity_num = activity_num;
        }

        public String getCollection_num() {
            return collection_num;
        }

        public void setCollection_num(String collection_num) {
            this.collection_num = collection_num;
        }

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }
    }
}
