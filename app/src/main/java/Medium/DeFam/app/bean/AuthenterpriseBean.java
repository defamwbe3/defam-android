package Medium.DeFam.app.bean;

public class AuthenterpriseBean {
     private String id;//":9,
    private String title;//":"123",
    private String website;//":"123",
    private String twitter_url;//":"123",
    private String telegram_url;//":"123",
    private String discord;//":"123",
    private String applicant;//":"123",
    private String contact;//":"123",
    private String member_id;//":14,
    private String introduction;//":"123",
    private String flag;//":0,
    private String audit_msg;//":null,
    private String audit_id;//":0,
    private String created_at;//":"2022-12-31 21:00:15",
    private String updated_at;//":"2022-12-31 21:00:15",
    private String deleted_at;//":0,
    private String platform;//":"",
    private String is_follow;//":"",
    private MemberBean member;

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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTwitter_url() {
        return twitter_url;
    }

    public void setTwitter_url(String twitter_url) {
        this.twitter_url = twitter_url;
    }

    public String getTelegram_url() {
        return telegram_url;
    }

    public void setTelegram_url(String telegram_url) {
        this.telegram_url = telegram_url;
    }

    public String getDiscord() {
        return discord;
    }

    public void setDiscord(String discord) {
        this.discord = discord;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAudit_msg() {
        return audit_msg;
    }

    public void setAudit_msg(String audit_msg) {
        this.audit_msg = audit_msg;
    }

    public String getAudit_id() {
        return audit_id;
    }

    public void setAudit_id(String audit_id) {
        this.audit_id = audit_id;
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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(String is_follow) {
        this.is_follow = is_follow;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public class MemberBean {
 private String id;//":14,
        private String nickname;//":"888",
        private String avatar;//":"http:\/\/api.cjlbzx.szyqa.com\/images\/20221231\/210d1fb27505f6bc54622fb7823ed75b.png",
        private String zan_num;//":0,
        private String article_num;//":2,
        private String activity_num;//":0,
        private String collection_num;//":2,
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
