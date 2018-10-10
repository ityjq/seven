package com.pinyougou.user.service.impl;

import com.pinyougou.pojo.TbUser;
import com.pinyougou.pojo.TbAddress;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.common.HttpClient;
import com.pinyougou.common.PageResult;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.pojo.TbUserExample.Criteria;
import com.pinyougou.pojo.vo.UserInFo;
import com.pinyougou.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.*;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service(timeout = 30000)
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbAddressMapper addressMapper;

    @Autowired
    private TbAreasMapper areasMapper;

    @Autowired
    private TbCitiesMapper citiesMapper;

    @Autowired
    private TbProvincesMapper provincesMapper;

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbSellerMapper tbSellerMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbSeckillOrderMapper tbSeckillOrderMapper;

    @Autowired
    private TbSeckillGoodsMapper tbSeckillGoodsMapper;





    @Override
    public boolean checkSmsCode(String phone, String smsCode) {
        String code = (String) redisTemplate.boundHashOps("sms").get(phone);
        if (code.equals(smsCode)) {
            return true;
        }
        return false;
    }


    @Override
    public void sendSms(String phone) {

        //1、生成随机数（验证码）
        //有一个问题：0.02345678*100000   = 023456   转成整型： 23456
        String code = (long) (Math.random() * 1000000) + "";
        System.err.println("验证码：" + code);
        //2、把验证码保存起来
        redisTemplate.boundHashOps("sms").put(phone, code);

        //3、调用发短信的接口，发送短信（为了节约，等整合的时候我再调用）
        try {
            HttpClient httpClient = new HttpClient("http://localhost:8088/sms/sendSms");
            Map map = new HashMap<>();
            map.put("phone_number", phone);
            map.put("sign_name", "黑马");
            map.put("template_code", "SMS_145599976");
            Map m = new HashMap();
            m.put("code", code);
            map.put("template_param", JSON.toJSONString(m));

            httpClient.setParameter(map);
            httpClient.post();

            System.err.println("请求的状态码：" + httpClient.getStatusCode());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询全部
     */
    @Override
    public List<TbUser> findAll() {
        return userMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbUser> page = (Page<TbUser>) userMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbUser user) {

        String md5Password = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(md5Password);
        user.setCreated(new Date());
        user.setUpdated(new Date());

        userMapper.insertSelective(user);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbUser user) {
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbUser findOne(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            userMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbUser user, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbUserExample example = new TbUserExample();
        Criteria criteria = example.createCriteria();

        if (user != null) {
            if (user.getUsername() != null && user.getUsername().length() > 0) {
                criteria.andUsernameLike("%" + user.getUsername() + "%");
            }
            if (user.getPassword() != null && user.getPassword().length() > 0) {
                criteria.andPasswordLike("%" + user.getPassword() + "%");
            }
            if (user.getPhone() != null && user.getPhone().length() > 0) {
                criteria.andPhoneLike("%" + user.getPhone() + "%");
            }
            if (user.getEmail() != null && user.getEmail().length() > 0) {
                criteria.andEmailLike("%" + user.getEmail() + "%");
            }
            if (user.getSourceType() != null && user.getSourceType().length() > 0) {
                criteria.andSourceTypeLike("%" + user.getSourceType() + "%");
            }
            if (user.getNickName() != null && user.getNickName().length() > 0) {
                criteria.andNickNameLike("%" + user.getNickName() + "%");
            }
            if (user.getName() != null && user.getName().length() > 0) {
                criteria.andNameLike("%" + user.getName() + "%");
            }
            if (user.getStatus() != null && user.getStatus().length() > 0) {
                criteria.andStatusLike("%" + user.getStatus() + "%");
            }
            if (user.getHeadPic() != null && user.getHeadPic().length() > 0) {
                criteria.andHeadPicLike("%" + user.getHeadPic() + "%");
            }
            if (user.getQq() != null && user.getQq().length() > 0) {
                criteria.andQqLike("%" + user.getQq() + "%");
            }
            if (user.getIsMobileCheck() != null && user.getIsMobileCheck().length() > 0) {
                criteria.andIsMobileCheckLike("%" + user.getIsMobileCheck() + "%");
            }
            if (user.getIsEmailCheck() != null && user.getIsEmailCheck().length() > 0) {
                criteria.andIsEmailCheckLike("%" + user.getIsEmailCheck() + "%");
            }
            if (user.getSex() != null && user.getSex().length() > 0) {
                criteria.andSexLike("%" + user.getSex() + "%");
            }

        }

        Page<TbUser> page = (Page<TbUser>) userMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Map> findDingdan(String name) {
        List<Map> list=new ArrayList<>();
        //根据用户名查出来id
        TbOrderExample orderExample=new TbOrderExample();
        orderExample.createCriteria().andUserIdEqualTo(name);
        List<TbOrder> orders = orderMapper.selectByExample(orderExample);
        for (TbOrder order : orders) {
            //根据商家名查出店铺名
            TbSeller tbSeller = tbSellerMapper.selectByPrimaryKey(order.getSellerId());
            //根据订单id获取，订单商品
            TbOrderItemExample orderItemExample=new TbOrderItemExample();
            orderItemExample.createCriteria().andOrderIdEqualTo(order.getOrderId());
            List<TbOrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
            Map map=new HashMap();
            map.put("seller",tbSeller);
            map.put("order",order);
            map.put("orderItemList",orderItems);
            list.add(map);

        }
        return list;


    }

    @Override
    public List<Map> findSeckill(String name) {
        List<Map> list=new ArrayList<>();
        TbSeckillOrderExample tbSeckillOrderExample=new TbSeckillOrderExample();
        tbSeckillOrderExample.createCriteria().andUserIdEqualTo(name);
        List<TbSeckillOrder> tbSeckillOrders = tbSeckillOrderMapper.selectByExample(tbSeckillOrderExample);
        for (TbSeckillOrder tbSeckillOrder : tbSeckillOrders) {
            TbSeckillGoodsExample tbSeckillGoodsExample=new TbSeckillGoodsExample();
            tbSeckillGoodsExample.createCriteria().andSellerIdEqualTo(tbSeckillOrder.getSellerId());
            List<TbSeckillGoods> tbSeckillGoods = tbSeckillGoodsMapper.selectByExample(tbSeckillGoodsExample);
            Map map=new HashMap();
            map.put("SeckillOrder",tbSeckillOrder);
            map.put("SeckillGoods",tbSeckillGoods);
            list.add(map);
        }
        return list;
    }









    @Override
    public UserInFo myInFo(String name) {
        UserInFo userInFo = new UserInFo();
        TbUserExample example = new TbUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(name);
        List<TbUser> users = userMapper.selectByExample(example);
//		TbUser user = userMapper.selectByPrimaryKey(1L);
        TbUser user = users.get(0);
        System.out.println(user);
        userInFo.setUser(user);


//        TbAddress address = addressMapper.selectByPrimaryKey(user.getId());
        TbAddressExample addressExample = new TbAddressExample();
        TbAddressExample.Criteria addressExampleCriteria = addressExample.createCriteria();
        addressExampleCriteria.andUserIdEqualTo(name);
        List<TbAddress> addressList = addressMapper.selectByExample(addressExample);
        TbAddress address = addressList.get(0);


        userInFo.setAddress(address);
        String provinceId = address.getProvinceId();
        TbProvinces Provinces = provincesMapper.selectByPrimaryKey(Integer.valueOf(provinceId));
        userInFo.setProvinceId(Provinces.getProvince());

        TbCities cities = citiesMapper.selectByPrimaryKey(Integer.valueOf(address.getCityId()));
        userInFo.setCityId(cities.getCity());

        TbAreas areas = areasMapper.selectByPrimaryKey(Integer.valueOf(address.getTownId()));
        userInFo.setTownId(areas.getArea());
        return userInFo;
    }

    @Override
    public void updateUser(UserInFo userInFo) {

        TbProvincesExample provinces = new TbProvincesExample();
        TbProvincesExample.Criteria criteria = provinces.createCriteria();
        criteria.andProvinceEqualTo(userInFo.getProvinceId());
        List<TbProvinces> tbProvinces = provincesMapper.selectByExample(provinces);

        TbCitiesExample cities = new TbCitiesExample();
        TbCitiesExample.Criteria criteriacities = cities.createCriteria();
        criteriacities.andCityEqualTo(userInFo.getCityId());
        List<TbCities> tbCities = citiesMapper.selectByExample(cities);

        TbAreasExample areas = new TbAreasExample();
        TbAreasExample.Criteria areasCriteria = areas.createCriteria();
        areasCriteria.andAreaEqualTo(userInFo.getTownId());
        List<TbAreas> tbAreas = areasMapper.selectByExample(areas);


        userMapper.updateByPrimaryKey(userInFo.getUser());

        TbAddress address = userInFo.getAddress();
        address.setCityId(String.valueOf(tbCities.get(0).getId()));
        address.setProvinceId(String.valueOf(tbProvinces.get(0).getId()));
        address.setTownId(String.valueOf(tbAreas.get(0).getId()));
        addressMapper.updateByPrimaryKey(userInFo.getAddress());


    }

    @Override
    public List<TbAddress> address(String name) {
        TbAddressExample addressExample = new TbAddressExample();
        TbAddressExample.Criteria criteria = addressExample.createCriteria();
        criteria.andUserIdEqualTo(name);
        List<TbAddress> addressList = addressMapper.selectByExample(addressExample);
        for (int i = 0; i < addressList.size(); i++) {

            addressList.get(i).setProvinceId(provincesMapper.selectByPrimaryKey(Integer.valueOf(addressList.get(i).getProvinceId())).getProvince());

            addressList.get(i).setCityId(citiesMapper.selectByPrimaryKey(Integer.valueOf(addressList.get(i).getCityId())).getCity());

            addressList.get(i).setTownId(areasMapper.selectByPrimaryKey(Integer.valueOf(addressList.get(i).getTownId())).getArea());
        }



        return addressList;
    }
}
