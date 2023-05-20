package Medium.DeFam.app.bean;

import java.io.Serializable;
import java.util.List;

public class WenZhangDetailBean implements Serializable{
    private String id;//":35,
    private String title;//":"七里香",
    private String subtitle;//":
    private List<String> images;//":null,
    private String type;//":"long",
    private String link;//":null,
    private String category_id;//":2,
    private String member_id;//":1,
    private String platform;//":"h5",
    private String is_top;//":0,
    private String is_recommend;//":1,
    private String is_original;//":1,
    private String good;//":2,
    private String read;//":48,
    private String forward;//":0,
    private String comments;//":0,
    private String memo;//":null,
    private String created_at;//":"2022-12-17 16:02:41",
    private String updated_at;//":"2022-12-17 16:39:09",
    private String deleted_at;//":0,
    private String status;//":"normal",
    private String collection;//":1,
    private String is_good;//":0,
    private String belongs_to;//":"others",
    private String is_follow;//":1,
    private String is_collection;//":
    private List<String> image;
    private List<String> tags;
    private List<String> image_text;
    private MemberBean member;
    private String share_link;
    private String textstate;
    //详情独有
    private String content;
    private CategoryBean category;


    public String getShare_link() {
        return share_link;
    }
    public class CategoryBean implements Serializable{
        private String id;//": 2,
        private String name;//": "Panews",
        private String sort;//": 0,
        private String status;//": "normal",
        private String created_at;//": "2022-12-09 16:16:10",
        private String updated_at;//": "2022-12-09 16:17:15",
        private String deleted_at;//": 0

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

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
        this.category = category;
    }

    public String getTextstate() {
        return textstate;
    }

    public void setTextstate(String textstate) {
        this.textstate = textstate;
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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
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

    public String getIs_original() {
        return is_original;
    }

    public void setIs_original(String is_original) {
        this.is_original = is_original;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getIs_good() {
        return is_good;
    }

    public void setIs_good(String is_good) {
        this.is_good = is_good;
    }

    public String getBelongs_to() {
        return belongs_to;
    }

    public void setBelongs_to(String belongs_to) {
        this.belongs_to = belongs_to;
    }

    public String getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(String is_follow) {
        this.is_follow = is_follow;
    }

    public String getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(String is_collection) {
        this.is_collection = is_collection;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getImage_text() {
        return image_text;
    }

    public void setImage_text(List<String> image_text) {
        this.image_text = image_text;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public class MemberBean implements Serializable {
        private String id;//":1,
        private String nickname;//":"123213123",
        private String avatar;//":"https:\/\/api.cjlbzx.szyqa.com\/images\/20221230\/a0b561273b5552f1fc5f983ab485db3b.png",
        private String zan_num;//":4,
        private String article_num;//":5,
        private String activity_num;//":11,
        private String collection_num;//":8,
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
