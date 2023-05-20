package Medium.DeFam.app.bean;

import java.util.List;

public class MapBean {
    private String id;//":22,
    private String name;//":"卡通妞妞",
    private String image;//":"https:\/\/api.cjlbzx.szyqa.com\/images\/20230218\/e6e4680dd594e7ff4e1de250a4fc62e0.jpeg",
    private String images;//":null,
    private String sort;//":0,
    private String sale_num;//":4,
    private String fragment_num;//":null,
    private String fragment_total;//":null,
    private String fragment_stock;//":null,
    private String stock;//":-2,
    private String stock_total;//":0,
    private String status;//":"normal",
    private String detail;//":"<font color=\"#b8b8b8\"><p>藏宝图需集齐所有碎片后方能完成合成，合成后的藏宝图可以提现。<\/p><font>",
    private String created_at;//":"2023-02-18 14:16:44",
    private String updated_at;//":"2023-02-18 14:16:44",
    private String deleted_at;//":null,
    private String fragment_ids;//":22,
    private List<UserMapBean> synthesis_needed;//

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSale_num() {
        return sale_num;
    }

    public void setSale_num(String sale_num) {
        this.sale_num = sale_num;
    }

    public String getFragment_num() {
        return fragment_num;
    }

    public void setFragment_num(String fragment_num) {
        this.fragment_num = fragment_num;
    }

    public String getFragment_total() {
        return fragment_total;
    }

    public void setFragment_total(String fragment_total) {
        this.fragment_total = fragment_total;
    }

    public String getFragment_stock() {
        return fragment_stock;
    }

    public void setFragment_stock(String fragment_stock) {
        this.fragment_stock = fragment_stock;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getStock_total() {
        return stock_total;
    }

    public void setStock_total(String stock_total) {
        this.stock_total = stock_total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getFragment_ids() {
        return fragment_ids;
    }

    public void setFragment_ids(String fragment_ids) {
        this.fragment_ids = fragment_ids;
    }

    public List<UserMapBean> getSynthesis_needed() {
        return synthesis_needed;
    }

    public void setSynthesis_needed(List<UserMapBean> synthesis_needed) {
        this.synthesis_needed = synthesis_needed;
    }

    public class UserMapBean {
        private String id;//":12,
        private String name;//":"卡通4",
        private String image;//":"https:\/\/api.cjlbzx.szyqa.com\/images\/20230218\/7c5f67c2bdc9ba99d080f85e39735bc9.jpeg",
        private String value;//":"1",
        private String number;//":

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
