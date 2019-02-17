package service.impl;


import java.util.List;

import dao.ShopDao;
import dao.impl.ShopDbcpDaoImpl;
import pojo.Shop;
import service.ShopService;

/**
 * @Title: ShopServiceImpl
 * @Description: 商家业务实现类
 * @author yhfu
 * @date 2018年10月24日 下午3:50:40
 */
public class ShopServiceImpl implements ShopService {

	/**
	 * 查找客户业务
	 */
	public List<Shop> shopFind(Shop shop) {

		ShopDao shopDao = new ShopDbcpDaoImpl();

		return shopDao.shopSelect(shop);
	}

	/**
	 * 增加客户业务
	 */
	public int shopAdd(Shop shop) {
		
		ShopDao shopDao = new ShopDbcpDaoImpl();
		
		return shopDao.shopInsert(shop);
	}

	/**
	 * 删除客户业务
	 */
	public boolean shopDel(Shop shop) {
		ShopDao shopDao = new ShopDbcpDaoImpl();

		return shopDao.shopDelete(shop);
	}

	/**
	 * 保存客户业务
	 */
	public boolean shopSave(Shop shop) {
		ShopDao shopDao = new ShopDbcpDaoImpl();

		return shopDao.shopUpdate(shop);
	}

	/**
	 * 查询客户总数业务
	 */
	public int shopCount(Shop shop) {
		ShopDao shopDao = new ShopDbcpDaoImpl();
		return shopDao.shopCount(shop);
	}

}
