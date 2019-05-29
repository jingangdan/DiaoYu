package com.project.dyuapp.entity;

/**
 * @author ${shipeiyun}
 * @created on 2017/12/9 0009
 * @description 兑换商品
 * @change ${}
 */
public class ExchangeInfoEntity {

    /**
     * total_money : 10000
     */
    private int total_money;
    /**
     * addressid : 2
     * member_list_id : 1
     * username : 13552525371
     * phone : 18611466224
     * provinceid : 130000
     * cityid : 130100
     * countyid : 130104
     * townid : 130103005
     * address : 万达商业广场
     * is_default : 1
     * addtime : 1508985960
     * province : 河北省
     * city : 石家庄市
     * county : 桥西区
     */

    private AddressBean address;

    public int getTotal_money() {
        return total_money;
    }

    public void setTotal_money(int total_money) {
        this.total_money = total_money;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public static class AddressBean {
        private String addressid;
        private String member_list_id;
        private String username;
        private String phone;
        private String provinceid;
        private String cityid;
        private String countyid;
        private String townid;
        private String address;
        private String is_default;
        private String addtime;
        private String province;
        private String city;
        private String county;

        public String getAddressid() {
            return addressid;
        }

        public void setAddressid(String addressid) {
            this.addressid = addressid;
        }

        public String getMember_list_id() {
            return member_list_id;
        }

        public void setMember_list_id(String member_list_id) {
            this.member_list_id = member_list_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProvinceid() {
            return provinceid;
        }

        public void setProvinceid(String provinceid) {
            this.provinceid = provinceid;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getCountyid() {
            return countyid;
        }

        public void setCountyid(String countyid) {
            this.countyid = countyid;
        }

        public String getTownid() {
            return townid;
        }

        public void setTownid(String townid) {
            this.townid = townid;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }
    }
}
