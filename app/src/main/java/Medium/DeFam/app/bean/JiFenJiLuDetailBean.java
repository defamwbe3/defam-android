package Medium.DeFam.app.bean;

import java.io.Serializable;
import java.util.List;

public class JiFenJiLuDetailBean implements Serializable {

    private String id;//":107,
    private String order_no;//":"202212301616513313",
    private String member_id;//":1,
    private String mobile;//":"15588113773",
    private String nickname;//":"123213123",
    private String amount;//":"10.00",
    private String status;//":"paid",
    private String pay_status;//":"paid",
    private String delivery_status;//":"undelivered",
    private String pay_type;//":"integral",
    private String pay_type_name;//":"积分支付",
    private String address_id;//":0,
    private String address_mobile;//":"",
    private String address_name;//":"",
    private String region_name;//":"",
    private String address;//":"",
    private String full_address;//":"",
    private String delivery_company;//":"",
    private String delivery_no;//":"",
    private String remark;//":null,
    private String paid_at;//":"2022-12-30 16:16:51",
    private String delivered_at;//":null,
    private String received_at;//":null,
    private String finished_at;//":null,
    private String closed_at;//":null,
    private String created_at;//":"2022-12-30 16:16:51",
    private String updated_at;//":"2022-12-30 16:16:51",
    private String deleted_at;//":null,
    private String status_name;//":"已支付",
    private List<String> region_id;
    private List<GoodsBean> goods;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_type_name() {
        return pay_type_name;
    }

    public void setPay_type_name(String pay_type_name) {
        this.pay_type_name = pay_type_name;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getAddress_mobile() {
        return address_mobile;
    }

    public void setAddress_mobile(String address_mobile) {
        this.address_mobile = address_mobile;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
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

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public String getDelivery_company() {
        return delivery_company;
    }

    public void setDelivery_company(String delivery_company) {
        this.delivery_company = delivery_company;
    }

    public String getDelivery_no() {
        return delivery_no;
    }

    public void setDelivery_no(String delivery_no) {
        this.delivery_no = delivery_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPaid_at() {
        return paid_at;
    }

    public void setPaid_at(String paid_at) {
        this.paid_at = paid_at;
    }

    public String getDelivered_at() {
        return delivered_at;
    }

    public void setDelivered_at(String delivered_at) {
        this.delivered_at = delivered_at;
    }

    public String getReceived_at() {
        return received_at;
    }

    public void setReceived_at(String received_at) {
        this.received_at = received_at;
    }

    public String getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(String finished_at) {
        this.finished_at = finished_at;
    }

    public String getClosed_at() {
        return closed_at;
    }

    public void setClosed_at(String closed_at) {
        this.closed_at = closed_at;
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

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public List<String> getRegion_id() {
        return region_id;
    }

    public void setRegion_id(List<String> region_id) {
        this.region_id = region_id;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public class GoodsBean implements Serializable {
        private String id;//":92,
        private String order_id;//":107,
        private String order_no;//":"",
        private String member_id;//":0,
        private String goods_id;//":28,
        private String goods_name;//":"测试商品",
        private String goods_image;//":"https:\/\/api.cjlbzx.szyqa.com\/images\/20221214\/c02e37cf8c958e54f6ed810a3c00fcc7.jpg",
        private String goods_images;//":"",
        private String price;//":"10.00",
        private String goods_num;//":1,
        private String amount;//":"10.00",
        private String delivery_company;//":"",
        private String delivery_no;//":"",
        private String delivery_status;//":"undelivered",
        private String delivered_at;//":null,
        private String created_at;//":"2022-12-30 16:16:51",
        private String updated_at;//":"2022-12-30 16:16:51",
        private String deleted_at;//":null,
        private String goods_sku_text;//":"规格2,规格2值3",
        private String sku_id;//":

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getGoods_images() {
            return goods_images;
        }

        public void setGoods_images(String goods_images) {
            this.goods_images = goods_images;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDelivery_company() {
            return delivery_company;
        }

        public void setDelivery_company(String delivery_company) {
            this.delivery_company = delivery_company;
        }

        public String getDelivery_no() {
            return delivery_no;
        }

        public void setDelivery_no(String delivery_no) {
            this.delivery_no = delivery_no;
        }

        public String getDelivery_status() {
            return delivery_status;
        }

        public void setDelivery_status(String delivery_status) {
            this.delivery_status = delivery_status;
        }

        public String getDelivered_at() {
            return delivered_at;
        }

        public void setDelivered_at(String delivered_at) {
            this.delivered_at = delivered_at;
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

        public String getGoods_sku_text() {
            return goods_sku_text;
        }

        public void setGoods_sku_text(String goods_sku_text) {
            this.goods_sku_text = goods_sku_text;
        }

        public String getSku_id() {
            return sku_id;
        }

        public void setSku_id(String sku_id) {
            this.sku_id = sku_id;
        }
    }

}
