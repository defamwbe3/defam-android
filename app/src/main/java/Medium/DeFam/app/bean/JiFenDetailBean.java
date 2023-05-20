package Medium.DeFam.app.bean;

import java.io.Serializable;
import java.util.List;

public class JiFenDetailBean implements Serializable {
     private String id;//":29,
    private String goods_no;//":"202212131403292677",
    private String name;//":"测试",
    private String image;//":"https:\/\/api.cjlbzx.szyqa.com\/images\/20221214\/7a38aba8cb674d923dc18f4c9c17c8f8.jpg",
    private String images;//":null,
    private String sort;//":0,
    private String price;//":"100.00",
    private String sale_num;//":5,
    private String stock;//":106,
    private String status;//":"normal",
    private String detail;//":null,
    private String created_at;//":"2022-12-13 14:03:30",
    private String updated_at;//":"2022-12-16 10:41:58",
    private String deleted_at;//":null,
    private String sku_type;//":1,
    private String is_recommen;//":0,
    private List<SkuBean> sku;
    private List<SkuPriceBean> sku_price;
    public class SkuBean implements Serializable{
  private String id;//":21,
        private String goods_id;//":28,
        private String name;//":"规格值",
        private String parent_id;//":0,
        private List<ChildrenBean> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }
    }
    public class ChildrenBean implements Serializable{
        private boolean isCheck = false;
        private String id;//":22,
        private String goods_id;//":28,
        private String name;//":"规格1",
        private String parent_id;//":

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }
    public List<SkuBean> getSku() {
        return sku;
    }

    public void setSku(List<SkuBean> sku) {
        this.sku = sku;
    }

    public class SkuPriceBean implements Serializable{
        private String id;//":18,
        private String goods_id;//":29,
        private String goods_sku_ids;//":"",
        private String goods_sku_text;//":"",
        private String price;//":111,
        private String stock;//":111,
        private String stock_warning;//":0,
        private String sn;//":null,
        private String sales;//":0,
        private String weigh;//":0,
        private String weight;//":0,
        private String image;//":"http:\/\/szyq.com\/images\/20221213\/ee5199f13039f68d7cc3948499b2aef6.jpg",
        private String status;//":0,
        private String created_at;//":"2022-12-13 14:03:29",
        private String updated_at;//":null,
        private String deleted_at;//":0,
        private String first_stock;//":


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_sku_ids() {
            return goods_sku_ids;
        }

        public void setGoods_sku_ids(String goods_sku_ids) {
            this.goods_sku_ids = goods_sku_ids;
        }

        public String getGoods_sku_text() {
            return goods_sku_text;
        }

        public void setGoods_sku_text(String goods_sku_text) {
            this.goods_sku_text = goods_sku_text;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getStock_warning() {
            return stock_warning;
        }

        public void setStock_warning(String stock_warning) {
            this.stock_warning = stock_warning;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public String getWeigh() {
            return weigh;
        }

        public void setWeigh(String weigh) {
            this.weigh = weigh;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public String getFirst_stock() {
            return first_stock;
        }

        public void setFirst_stock(String first_stock) {
            this.first_stock = first_stock;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_no() {
        return goods_no;
    }

    public void setGoods_no(String goods_no) {
        this.goods_no = goods_no;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSale_num() {
        return sale_num;
    }

    public void setSale_num(String sale_num) {
        this.sale_num = sale_num;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
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

    public String getSku_type() {
        return sku_type;
    }

    public void setSku_type(String sku_type) {
        this.sku_type = sku_type;
    }

    public String getIs_recommen() {
        return is_recommen;
    }

    public void setIs_recommen(String is_recommen) {
        this.is_recommen = is_recommen;
    }

    public List<SkuPriceBean> getSku_price() {
        return sku_price;
    }

    public void setSku_price(List<SkuPriceBean> sku_price) {
        this.sku_price = sku_price;
    }
}
