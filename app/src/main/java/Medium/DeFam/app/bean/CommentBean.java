package Medium.DeFam.app.bean;

import java.io.Serializable;
import java.util.List;

public class CommentBean {
    private String total;
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public class DataBean implements Serializable {
        private String id;//":21,
        private String article_id;//":1,
        private String type;//":1,
        private String content;//":"第一条子评论的回复",
        private String zu_id;//":21,
        private String parent_id;//":0,
        private String to_member_id;//":null,
        private String to_content;//":null,
        private String member_id;//":1,
        private String ip;//":"113.122.227.205",
        private String status;//":1,
        private String good;//":7,
        private String forward;//":0,
        private String comment;//":3,
        private String created_at;//":"2022-12-17 14:24:54",
        private String updated_at;//":"2022-12-17 14:24:54",
        private String deleted_at;//":0,
        private String totree;//":"1",
        private String level;//":"1",
        private List<ChildrenBean> children;//":Array[2],
        private String is_good;//":0,
        private String created_ats;//":
        private MemberBean member;//

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getZu_id() {
            return zu_id;
        }

        public void setZu_id(String zu_id) {
            this.zu_id = zu_id;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getTo_member_id() {
            return to_member_id;
        }

        public void setTo_member_id(String to_member_id) {
            this.to_member_id = to_member_id;
        }

        public String getTo_content() {
            return to_content;
        }

        public void setTo_content(String to_content) {
            this.to_content = to_content;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGood() {
            return good;
        }

        public void setGood(String good) {
            this.good = good;
        }

        public String getForward() {
            return forward;
        }

        public void setForward(String forward) {
            this.forward = forward;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
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

        public String getTotree() {
            return totree;
        }

        public void setTotree(String totree) {
            this.totree = totree;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public String getIs_good() {
            return is_good;
        }

        public void setIs_good(String is_good) {
            this.is_good = is_good;
        }

        public String getCreated_ats() {
            return created_ats;
        }

        public void setCreated_ats(String created_ats) {
            this.created_ats = created_ats;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }
    }

    public class ChildrenBean implements Serializable {
        private String id;//":45,
        private String article_id;//":1,
        private String type;//":1,
        private String content;//":"asdasdasdasd",
        private String zu_id;//":21,
        private String parent_id;//":21,
        private String to_member_id;//":1,
        private String to_content;//":"第一条子评论的回复",
        private String member_id;//":13,
        private String ip;//":"113.122.227.205",
        private String status;//":1,
        private String good;//":0,
        private String forward;//":0,
        private String comment;//":0,
        private String created_at;//":"2022-12-19 17:32:49",
        private String updated_at;//":"2022-12-19 17:32:49",
        private String deleted_at;//":0,
        private String totree;//":"1,2",
        private String level;//":"2",
        private String is_good;//":0,
        private MemberBean member;//
        private MemberBean tomember;//
        private String created_ats;//
        private PLBean pl;

        public PLBean getPl() {
            return pl;
        }

        public void setPl(PLBean pl) {
            this.pl = pl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getZu_id() {
            return zu_id;
        }

        public void setZu_id(String zu_id) {
            this.zu_id = zu_id;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getTo_member_id() {
            return to_member_id;
        }

        public void setTo_member_id(String to_member_id) {
            this.to_member_id = to_member_id;
        }

        public String getTo_content() {
            return to_content;
        }

        public void setTo_content(String to_content) {
            this.to_content = to_content;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGood() {
            return good;
        }

        public void setGood(String good) {
            this.good = good;
        }

        public String getForward() {
            return forward;
        }

        public void setForward(String forward) {
            this.forward = forward;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
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

        public String getTotree() {
            return totree;
        }

        public void setTotree(String totree) {
            this.totree = totree;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getIs_good() {
            return is_good;
        }

        public void setIs_good(String is_good) {
            this.is_good = is_good;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public MemberBean getTomember() {
            return tomember;
        }

        public void setTomember(MemberBean tomember) {
            this.tomember = tomember;
        }

        public String getCreated_ats() {
            return created_ats;
        }

        public void setCreated_ats(String created_ats) {
            this.created_ats = created_ats;
        }
    }

    public class PLBean implements Serializable {
 private String id;//":21,
        private String article_id;//":1,
        private String type;//":1,
        private String content;//":"第一条子评论的回复",
        private String zu_id;//":21,
        private String parent_id;//":0,
        private String to_member_id;//":null,
        private String to_content;//":null,
        private String member_id;//":1,
        private String ip;//":"113.122.227.205",
        private String status;//":1,
        private String good;//":8,
        private String forward;//":0,
        private String comment;//":3,
        private String created_at;//":"2022-12-17 14:24:54",
        private String updated_at;//":"2022-12-17 14:24:54",
        private String deleted_at;//":0,
        private String totree;//":"1",
        private String level;//":"1",
        private String created_ats;//":

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getZu_id() {
            return zu_id;
        }

        public void setZu_id(String zu_id) {
            this.zu_id = zu_id;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getTo_member_id() {
            return to_member_id;
        }

        public void setTo_member_id(String to_member_id) {
            this.to_member_id = to_member_id;
        }

        public String getTo_content() {
            return to_content;
        }

        public void setTo_content(String to_content) {
            this.to_content = to_content;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGood() {
            return good;
        }

        public void setGood(String good) {
            this.good = good;
        }

        public String getForward() {
            return forward;
        }

        public void setForward(String forward) {
            this.forward = forward;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
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

        public String getTotree() {
            return totree;
        }

        public void setTotree(String totree) {
            this.totree = totree;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getCreated_ats() {
            return created_ats;
        }

        public void setCreated_ats(String created_ats) {
            this.created_ats = created_ats;
        }
    }

    public class MemberBean implements Serializable {
        private String id;//":1,
        private String nickname;//":"兰湘子",
        private String avatar;//":"http:\/\/api.cjlbzx.szyqa.com\/images\/20221229\/3b5586b47746fe3dc408b155c9eefce2.png",
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
}
