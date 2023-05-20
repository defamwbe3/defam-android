package Medium.DeFam.app.bean;

import java.io.Serializable;
import java.util.List;

public class ZiXunDetailBean implements Serializable {
    private String id;//":1,
    private String title;//":"资讯",
    private String subtitle;//":"资讯简介",
    private List<String> image;//
    private List<String> images;//
    private String platform;//":"pingtai",
    private String category_id;//":1,
    private List<String> tags;//
    private String is_top;//":1,
    private String is_recommend;//":0,
    private String author;//":"佚名",
    private String link;//":null,
    private String source;//":"1",
    private String good;//":5,
    private String comments;//":1,
    private String read;//":341,
    private String forward;//":1,
    private String memo;//":"111",
    private String created_at;//":"2022-12-09 13:28:59",
    private String updated_at;//":"2022-12-17 16:20:53",
    private String deleted_at;//":0,
    private String collection;//":1,
    private String is_good;//":0,
    private List<String> image_text;//
    private String is_collection;//":


    private String content;//":
    private List<Tag> tag;//
    private Category category;//":
    private String share_link;

    public String getShare_link() {
        return share_link;
    }

    public void setShare_link(String share_link) {
        this.share_link = share_link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Tag> getTag() {
        return tag;
    }

    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public class Category implements Serializable {
  private String id;//":1,
        private String name;//":"平台精选",
        private String sort;//":0,
        private String status;//":"normal",
        private String created_at;//":"2022-12-09 15:05:08",
        private String updated_at;//":"2022-12-09 16:17:11",
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
    public class Tag implements Serializable {
        private String id;//":1,
        private String name;//":"web",
        private String status;//":"normal",
        private String memo;//":null,
        private String created_at;//":"2022-12-10 09:21:04",
        private String updated_at;//":"2022-12-10 09:21:04",
        private String deleted_at;//":0,

        private Pivot pivot;//":

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public Pivot getPivot() {
            return pivot;
        }

        public void setPivot(Pivot pivot) {
            this.pivot = pivot;
        }
    }
    public class Pivot implements Serializable {
        private String id;//":40,
        private String realinfo_id;//":1,
        private String tag_id;//":

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRealinfo_id() {
            return realinfo_id;
        }

        public void setRealinfo_id(String realinfo_id) {
            this.realinfo_id = realinfo_id;
        }

        public String getTag_id() {
            return tag_id;
        }

        public void setTag_id(String tag_id) {
            this.tag_id = tag_id;
        }
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

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public List<String> getImage_text() {
        return image_text;
    }

    public void setImage_text(List<String> image_text) {
        this.image_text = image_text;
    }

    public String getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(String is_collection) {
        this.is_collection = is_collection;
    }
}
