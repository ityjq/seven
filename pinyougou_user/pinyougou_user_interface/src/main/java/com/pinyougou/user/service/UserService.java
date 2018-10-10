package com.pinyougou.user.service;
import com.pinyougou.common.PageResult;
import com.pinyougou.pojo.TbAddress;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.pojo.vo.UserInFo;

import java.util.List;
import java.util.Map;


/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface UserService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbUser> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbUser user);
	
	
	/**
	 * 修改
	 */
	public void update(TbUser user);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbUser findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(TbUser user, int pageNum, int pageSize);

	/**
	 * 发短信的
	 */
	void sendSms(String phone);

	/**
	 * 根据手机号来校验验证码
	 * @param phone
	 * @param smsCode
	 * @return
	 */
	boolean checkSmsCode(String phone, String smsCode);

	public List<Map> findDingdan(String name);


	List<Map> findSeckill(String name);




	UserInFo myInFo(String name);

	void updateUser(UserInFo userInFo);

	List<TbAddress> address(String name);
}
