package service;

import java.util.List;

import pojo.Admin;


/**
 * 客户业务层接口
 *
 */
public interface AdminService {
	public List<Admin> adminFind(Admin admin); // 查找业务

	public int adminAdd(Admin admin); // 增加业务

	public boolean adminDel(Admin admin); // 删除业务

	public boolean adminSave(Admin admin); // 保存业务

	public int adminCount(Admin admin); // 查询数据总数


}
