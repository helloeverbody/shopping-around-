package service.impl;

import java.util.List;

import dao.ShoppingCartDao;
import dao.impl.ShoppingCartDaoImpl;
import pojo.ShoppingCart;
import service.ShoppingCartService;


public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Override
	public List<ShoppingCart> shoppingCartFind(ShoppingCart shoppingCart) {
		ShoppingCartDao shoppingCartDao = new ShoppingCartDaoImpl();

		return shoppingCartDao.productSelect(shoppingCart);
	}

	@Override
	public int shoppingCartAdd(ShoppingCart shoppingCart) {
		ShoppingCartDao shoppingCartDao = new ShoppingCartDaoImpl();
		return shoppingCartDao.productInsert(shoppingCart);
	}

	@Override
	public boolean shoppingCartDel(ShoppingCart shoppingCart) {
		ShoppingCartDao shoppingCartDao = new ShoppingCartDaoImpl();
		return shoppingCartDao.shoppingCartDelete(shoppingCart);
	}

	@Override
	public boolean shoppingCartSave(ShoppingCart shoppingCart) {
		ShoppingCartDao shoppingCartDao = new ShoppingCartDaoImpl();
		return shoppingCartDao.shoppingCartUpdate(shoppingCart);
	}

	@Override
	public boolean shoppingCartDelByCondition(ShoppingCart shoppingCart) {
		ShoppingCartDao shoppingCartDao = new ShoppingCartDaoImpl();
		return shoppingCartDao.shoppingCartDeleteByCondition(shoppingCart);
	}

}
