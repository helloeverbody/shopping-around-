package service.impl;

import java.util.List;

import dao.AdminDao;
import dao.impl.AdminDbcpDaoImpl;
import pojo.Admin;
import service.AdminService;


/**
 * @Title: AdminServiceImpl
 * @Description: 管理员业务实现类
 * @author yhfu
 * @date 2018年10月24日 下午3:50:40
 */
public class AdminServiceImpl implements AdminService {

	/**
	 * 查找客户业务
	 */
	public List<Admin> adminFind(Admin admin) {

		AdminDao adminDao = new AdminDbcpDaoImpl();

		return adminDao.adminSelect(admin);
	}

	/**
	 * 增加客户业务
	 */
	public int adminAdd(Admin admin) {
		
		AdminDao adminDao = new AdminDbcpDaoImpl();
		
		return adminDao.adminInsert(admin);
	}

	/**
	 * 删除客户业务
	 */
	public boolean adminDel(Admin admin) {
		AdminDao adminDao = new AdminDbcpDaoImpl();

		return adminDao.adminDelete(admin);
	}

	/**
	 * 保存客户业务
	 */
	public boolean adminSave(Admin admin) {
		AdminDao adminDao = new AdminDbcpDaoImpl();

		return adminDao.adminUpdate(admin);
	}

	/**
	 * 查询客户总数业务
	 */
	public int adminCount(Admin admin) {
		AdminDao adminDao = new AdminDbcpDaoImpl();
		return adminDao.adminCount(admin);
	}

}
