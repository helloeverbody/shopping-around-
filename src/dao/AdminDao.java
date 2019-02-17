package dao;

import java.util.List;

import pojo.Admin;


public interface AdminDao {

	public List<Admin> adminSelect(Admin admin);// 查询

	public int adminInsert(Admin admin);// 添加 添加成功返回id，否则返回-1

	public boolean adminDelete(Admin admin);// 删除

	public boolean adminUpdate(Admin admin);// 修改

	public int adminCount(Admin admin); // 返回所查询总数
}
