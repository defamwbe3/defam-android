package Medium.DeFam.app.bean;

import java.io.Serializable;
import java.util.List;

public class HuoDongDetailBean implements Serializable {

    private String id;//":3,
    private String title;//":"活动2",

    private String after_content;//":"活动后",
    private String image;//":"http:\/\/auction.szyqa.com\/images\/20221109\/96af9c9b5bab48f82bb9a51d9cce3e64.jpg",
    private List<String> tags;//":"web,html",
    private String category_id;//":null,
    private String category;//":1,
    private String number;//":8,
    private String registered_number;//":3,
    private String is_airdrop;//":0,
    private String special;//":null,
    private String link_id;//":1,
    private String activity_address;//":null,
    private String start_time;//":"2022-12-16 18:43:44",
    private String end_time;//":"2023-12-16 18:44:04",
    private String json_data;//":null,
    private String member_id;//":1,
    private String is_top;//":1,
    private String is_recommend;//":0,
    private String created_at;//":"2022-12-16 18:43:44",
    private String updated_at;//":"2022-12-16 18:43:44",
    private String good;//":1,
    private String read;//":111,
    private String forward;//":0,
    private String comments;//":0,
    private String collection;//":null,
    private String status;//":"hidden",
    private String is_show;//":0,
    private String is_full;//":0,
    private MemberBean member;
    private LinkBean link;
    private String is_registered;//":0,
    private String is_collection;//":0,
    private String status_text;//":
    private String share_link;

    //详情中单独存在
    private String content;//":"活动2",
    private String is_good;


    public String getShare_link() {
        return share_link;
    }

    public void setShare_link(String share_link) {
        this.share_link = share_link;
    }
    public String getIs_good() {
        return is_good;
    }

    public void setIs_good(String is_good) {
        this.is_good = is_good;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public String getAfter_content() {
        return after_content;
    }

    public void setAfter_content(String after_content) {
        this.after_content = after_content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRegistered_number() {
        return registered_number;
    }

    public void setRegistered_number(String registered_number) {
        this.registered_number = registered_number;
    }

    public String getIs_airdrop() {
        return is_airdrop;
    }

    public void setIs_airdrop(String is_airdrop) {
        this.is_airdrop = is_airdrop;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }

    public String getActivity_address() {
        return activity_address;
    }

    public void setActivity_address(String activity_address) {
        this.activity_address = activity_address;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getJson_data() {
        return json_data;
    }

    public void setJson_data(String json_data) {
        this.json_data = json_data;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getIs_top() {
        return is_top;
    }

    public void setIs_top(String is_top) {
        this.is_top = is_top;
    }

    public String getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(String is_recommend) {
        this.is_recommend = is_recommend;
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

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getIs_full() {
        return is_full;
    }

    public void setIs_full(String is_full) {
        this.is_full = is_full;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public LinkBean getLink() {
        return link;
    }

    public void setLink(LinkBean link) {
        this.link = link;
    }

    public String getIs_registered() {
        return is_registered;
    }

    public void setIs_registered(String is_registered) {
        this.is_registered = is_registered;
    }

    public String getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(String is_collection) {
        this.is_collection = is_collection;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public class MemberBean implements Serializable {
        private String id;//":1,
        private String nickname;//":"123213123",
        private String avatar;//":"http:\/\/api.cjlbzx.szyqa.com\/images\/20221230\/a0b561273b5552f1fc5f983ab485db3b.png",
        private String is_enterprise;//":3,
        private String zan_num;//":4,
        private String article_num;//":5,
        private String activity_num;//":

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
    }

    public class LinkBean implements Serializable {
        private String id;//":1,
        private String name;//":"链接",
        private String url;//":"www.baidu.com",
        private String created_at;//":null,
        private String updated_at;//":"2022-12-17 15:06:55",
        private String deleted_at;//":

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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
    }
}
