package Medium.DeFam.app.bean;

import java.io.Serializable;
import java.util.List;

public class KOLBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean implements Serializable {
        private String id;//":180,
        private String name;//": "凉粉小刀",
        private String link;//": "{\"其他帖\": {\"href\": \"https://twitter.com/liangfenxiaodao/status/1625850098155687936\", \"sortIndex\": \"\", \"src_tx\": \"uWIyC88-_normal\", \"name\": \"Dahuzi.eth\", \"screen_name\": \"Dahuzi_eth\", \"full_text\": \"分享下 #bgb 观点\\n\\n1. $bgb 这种我以前是从来不追的，后来和群友讨论我发现我讨厌的点恰恰是BG的优点，狼性文化，条线明显。我是一个很佛，不是很push的人\\n\\n2. 今年朋友让我买canto，我发现bg已不是合约所，有更多的优质币种\\n\\n3. 买在蓝海，卖在红海，流动性思考\\n\\n非投资建议，补充直播所说\\n\\n#bitget https://t.co/e7ocFpf1i0\", \"public_at\": \"2023-02-15 21:30:44\"}}",
        private String public_at;//": "2023-02-16 11:01:24",
        private String full_text;//": "借着胡子的分享也来奶一波Bitget。\n\nBitget最近热度十足，俨然一颗冉冉升起的新星。尤其在昨天某交易所卡Blur充值的时候，Bitget毫无延迟，更是赢得了无数赞誉。\n\n又有小道消息称该交易所近来要做IEO，如果认定现在是牛市初期，不妨先注册个账户等回调？\n\nhttps://t.co/zd1f2EbO0f",
        private String screen_name;//": "liangfenxiaodao",
        private String user_name;//": "(?, ?) 凉粉小刀 (168,168)",
        private String src_tx;//": "MiXtfO9w_normal",
        private String href;//": "https://twitter.com/liangfenxiaodao/status/1626054108720537601?cxt=HHwWgoDR_Zfm85AtAAAA",
        private String artical_id;//": "1626054108720537601",
        private String created_at;//": "2023-02-16 11:02:15",
        private String updated_at;//": null,
        private String deleted_at;//": 0,
        private String good;//": 0,
        private String forward;//": 0,
        private String collection;//": 0,
        private String comments;//": 0,
        SrcTxTextBean src_tx_text;
        SrcTxTextBean link_text;
        private String is_collection;//": 0,
        private String is_forward;//": 0,
        private String is_good;//": 0
        OtherTextBean main_text;
        OtherTextBean other_text;
        private String textstate;
        private String maintextstate;

        public String getMaintextstate() {
            return maintextstate;
        }

        public void setMaintextstate(String maintextstate) {
            this.maintextstate = maintextstate;
        }

        public String getTextstate() {
            return textstate;
        }

        public void setTextstate(String textstate) {
            this.textstate = textstate;
        }

        public OtherTextBean getMain_text() {
            return main_text;
        }

        public void setMain_text(OtherTextBean main_text) {
            this.main_text = main_text;
        }

        public OtherTextBean getOther_text() {
            return other_text;
        }

        public void setOther_text(OtherTextBean other_text) {
            this.other_text = other_text;
        }

        public String getIs_collection() {
            return is_collection;
        }

        public void setIs_collection(String is_collection) {
            this.is_collection = is_collection;
        }

        public String getIs_forward() {
            return is_forward;
        }

        public void setIs_forward(String is_forward) {
            this.is_forward = is_forward;
        }

        public String getIs_good() {
            return is_good;
        }

        public void setIs_good(String is_good) {
            this.is_good = is_good;
        }

        public SrcTxTextBean getLink_text() {
            return link_text;
        }

        public void setLink_text(SrcTxTextBean link_text) {
            this.link_text = link_text;
        }

        public SrcTxTextBean getSrc_tx_text() {
            return src_tx_text;
        }

        public void setSrc_tx_text(SrcTxTextBean src_tx_text) {
            this.src_tx_text = src_tx_text;
        }

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

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getPublic_at() {
            return public_at;
        }

        public void setPublic_at(String public_at) {
            this.public_at = public_at;
        }

        public String getFull_text() {
            return full_text;
        }

        public void setFull_text(String full_text) {
            this.full_text = full_text;
        }

        public String getScreen_name() {
            return screen_name;
        }

        public void setScreen_name(String screen_name) {
            this.screen_name = screen_name;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getSrc_tx() {
            return src_tx;
        }

        public void setSrc_tx(String src_tx) {
            this.src_tx = src_tx;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getArtical_id() {
            return artical_id;
        }

        public void setArtical_id(String artical_id) {
            this.artical_id = artical_id;
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

        public String getCollection() {
            return collection;
        }

        public void setCollection(String collection) {
            this.collection = collection;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }
    }

    public class SrcTxTextBean implements Serializable {
        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public class OtherTextBean implements Serializable {
        private String href;
        private String sortIndex;
        private String src_tx;
        private String name;
        private String screen_name;
        private String full_text;
        private String public_at;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getSortIndex() {
            return sortIndex;
        }

        public void setSortIndex(String sortIndex) {
            this.sortIndex = sortIndex;
        }

        public String getSrc_tx() {
            return src_tx;
        }

        public void setSrc_tx(String src_tx) {
            this.src_tx = src_tx;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScreen_name() {
            return screen_name;
        }

        public void setScreen_name(String screen_name) {
            this.screen_name = screen_name;
        }

        public String getFull_text() {
            return full_text;
        }

        public void setFull_text(String full_text) {
            this.full_text = full_text;
        }

        public String getPublic_at() {
            return public_at;
        }

        public void setPublic_at(String public_at) {
            this.public_at = public_at;
        }
    }

}
