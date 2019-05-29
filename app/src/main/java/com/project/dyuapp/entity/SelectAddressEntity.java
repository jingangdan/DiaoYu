package com.project.dyuapp.entity;

/**
 * @author ${shipeiyun}
 * @created on 2017/12/7 0007
 * @description 选择地址  收货地址
 * @change ${}
 */
public class SelectAddressEntity {
    /**
     * addressid : 1
     * member_list_id : 1
     * username : 13552525371
     * phone : 18611466224
     * provinceid : 130000
     * cityid : 130100
     * countyid : 130102
     * townid : 130102002
     * address : 怀特商业广场
     * is_default : 0
     * addtime : 1508985960
     * xqaddress : 河北省 石家庄市 长安区 青园街道  怀特商业广场
     */

    private boolean isCheck;
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
    private String xqaddress;

    public SelectAddressEntity(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

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

    public String getXqaddress() {
        return xqaddress;
    }

    public void setXqaddress(String xqaddress) {
        this.xqaddress = xqaddress;
    }
}
