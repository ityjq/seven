package com.pinyougou.pojo.vo;

import com.pinyougou.pojo.TbAddress;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbUser;

import java.io.Serializable;
import java.util.List;

public class  UserInFo implements Serializable{

    private TbUser user;

    private TbAddress address;

    private String provinceId;
    private String cityId;
    private String townId;

    public TbUser getUser() {
        return user;
    }

    public void setUser(TbUser user) {
        this.user = user;
    }

    public TbAddress getAddress() {
        return address;
    }

    public void setAddress(TbAddress address) {
        this.address = address;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId;
    }
}
