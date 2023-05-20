package Medium.DeFam.app.bean;

import java.util.List;

public class NotifyBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean {
 private String id;//":327,
        private String type;//":3,
        private String mold;//":"good",
        private String article_id;//":3,
        private String content;//":"赞了你的评论",
        private String to_member_id;//":12,
        private String member_id;//":1,
        private String created_at;//":"2023-01-03 14:13:59",
        private String updated_at;//":"2023-01-03 14:13:59",
        private String deleted_at;//":0,
        private String is_read;//":0,
        private MemberBean to_member_info;//

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

        public String getMold() {
            return mold;
        }

        public void setMold(String mold) {
            this.mold = mold;
        }

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public MemberBean getTo_member_info() {
            return to_member_info;
        }

        public void setTo_member_info(MemberBean to_member_info) {
            this.to_member_info = to_member_info;
        }
    }
    public class MemberBean {
        private String id;//":12,
        private String member_no;//":"202212191458191842",
        private String mobile;//":"18559601229",
        private String is_auth;//":0,
        private String nickname;//":"185****1229",
        private String avatar;//":"https:\/\/api.cjlbzx.szyqa.com\/images\/20230102\/82e9c495d6cfaf341e5725fb80c386ee.png",
        private String gender;//":"secret",
        private String status;//":"normal",
        private String invite_code;//":"DMEGLK",
        private String parent_id;//":0,
        private String balance;//":"0.00",
        private String integral;//":"0.00",
        private String unionid;//":"",
        private String openid;//":"",
        private String realname;//":"",
        private String card_no;//":"",
        private String group_count;//":0,
        private String created_at;//":"2022-12-19 14:58:19",
        private String updated_at;//":"2023-01-03 09:42:52",
        private String deleted_at;//":null,
        private String creator_id;//":0,
        private String is_enterprise;//":1,
        private String follow_num;//":5,
        private String fans_num;//":1,
        private String pc_cover_address;//":"https:\/\/api.cjlbzx.szyqa.com\/images\/20230102\/e42038608c448c2463e308a80fe8b2e3.png",
        private String sign;//":"对方是否",
        private String region_id;//":"",
        private String region_name;//":"",
        private String address;//":"第三方",
        private String zan;//":0,
        private String wap_cover_address;//":"",
        private String url;//":"www.bilibili.com",
        //private String parent;//":null,
        private String zan_num;//":3,
        private String article_num;//":3,
        private String activity_num;//":1,
        private String collection_num;//":4,
        private String is_read;//":

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMember_no() {
            return member_no;
        }

        public void setMember_no(String member_no) {
            this.member_no = member_no;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getIs_auth() {
            return is_auth;
        }

        public void setIs_auth(String is_auth) {
            this.is_auth = is_auth;
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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getCard_no() {
            return card_no;
        }

        public void setCard_no(String card_no) {
            this.card_no = card_no;
        }

        public String getGroup_count() {
            return group_count;
        }

        public void setGroup_count(String group_count) {
            this.group_count = group_count;
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

        public String getCreator_id() {
            return creator_id;
        }

        public void setCreator_id(String creator_id) {
            this.creator_id = creator_id;
        }

        public String getIs_enterprise() {
            return is_enterprise;
        }

        public void setIs_enterprise(String is_enterprise) {
            this.is_enterprise = is_enterprise;
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

        public String getPc_cover_address() {
            return pc_cover_address;
        }

        public void setPc_cover_address(String pc_cover_address) {
            this.pc_cover_address = pc_cover_address;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getRegion_id() {
            return region_id;
        }

        public void setRegion_id(String region_id) {
            this.region_id = region_id;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZan() {
            return zan;
        }

        public void setZan(String zan) {
            this.zan = zan;
        }

        public String getWap_cover_address() {
            return wap_cover_address;
        }

        public void setWap_cover_address(String wap_cover_address) {
            this.wap_cover_address = wap_cover_address;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

/*        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }*/

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
