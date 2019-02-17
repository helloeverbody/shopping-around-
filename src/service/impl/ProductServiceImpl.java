package service.impl;

import java.util.ArrayList;
import java.util.List;

import dao.ProductDao;
import dao.impl.ProductDbcpDaoImpl;
import pojo.Product;
import service.ProductService;


/**
 * @Title: ProductServiceImpl
 * @Description: 商品业务实现
 * @author yhfu
 * @date 2018年10月23日 上午9:23:22
 */
public class ProductServiceImpl implements ProductService {

	/**
	 * 查找商品业务
	 */
	public List<Product> productFind(Product product) {

		ProductDao productDao = new ProductDbcpDaoImpl();

		return productDao.productSelect(product);
	}

	/**
	 * 增加商品业务
	 */
	public int productAdd(Product product) {
		
		ProductDao productDao = new ProductDbcpDaoImpl();
		
		return productDao.productInsert(product);
	}

	/**
	 * 删除商品业务
	 */
	public boolean productDel(Product product) {
		ProductDao productDao = new ProductDbcpDaoImpl();

		return productDao.productDelete(product);
	}

	/**
	 * 删除通过condition 批量
	 * @param product
	 * @return
	 */
	public boolean productDelByCondition(Product product) {
		ProductDao productDao = new ProductDbcpDaoImpl();

		return productDao.productDeleteByCondition(product);
	}

	/**
	 * 保存商品业务
	 */
	public boolean productSave(Product product) {
		ProductDao productDao = new ProductDbcpDaoImpl();

		return productDao.productUpdate(product);
	}



	/**
	 * 查询商品总数业务
	 */
	public int productCount(Product product) {
		ProductDao productDao = new ProductDbcpDaoImpl();
		return productDao.productCount(product);
	}

	/**
	 * 店铺商品两表查询
	 */
	public List<List<Product>> productShopFind(Product product) {

		ProductDao productDao = new ProductDbcpDaoImpl();
		List<Product> productList = new ArrayList<Product>();
		productList = productDao.prodShopSelect(product);

		List<List<Product>> list = new ArrayList<List<Product>>();
		List<Product> listTemp = new ArrayList<Product>();
		if (!productList.isEmpty()) { // 查询不为空
			listTemp.add(productList.get(0));
		}
		if (productList.size() == 1) {
			list.add(listTemp);
		}
		for (int i = 1; i < productList.size(); i++) {

			if (productList.get(i).getShop_id() == productList.get(i - 1).getShop_id()) {
				listTemp.add(productList.get(i));
			} else {
				list.add(listTemp);
				listTemp = null;
				listTemp = new ArrayList<Product>();
				listTemp.add(productList.get(i));
			}

			if (i == productList.size() - 1) {
				list.add(listTemp);
			}
		}

		/*
		 * ProductDao productDao = new ProductDbcpDaoImpl(); List<Product>
		 * productList = new ArrayList<Product>(); List<Shop> shopList = new
		 * ArrayList<Shop>(); productList = productDao.prodShopSelect(product);
		 * 
		 * List<Product> productListTemp = new ArrayList<Product>();
		 * productListTemp.add(productList.get(0)); Shop shop = new Shop(); if
		 * (productList.size() == 1) { shop.setProductList(productListTemp);
		 * shopList.add(shop); } for (int i = 1; i < productList.size(); i++) {
		 * if (productList.get(i).getShop_id() == productList.get(i -
		 * 1).getShop_id()) { productListTemp.add(productList.get(0)); } else {
		 * shop.setProductList(productListTemp); shopList.add(shop);
		 * productListTemp = null; productListTemp = new ArrayList<Product>();
		 * productListTemp.add(productList.get(i)); } if (i ==
		 * productList.size() - 1) { shop.setProductList(productListTemp);
		 * shopList.add(shop); }
		 * 
		 * }
		 */
		return list;
	}

	public static void main(String[] args) {
		ProductService productService = new ProductServiceImpl();
		Product product = new Product();
		product.setCondition(" and shop_id=prod_shop_id and prod_name = '%a%' ORDER BY prod_shop_id");
		List<List<Product>> shopList = new ArrayList<List<Product>>();

		shopList = productService.productShopFind(product);
		for (List<Product> list : shopList) {
			System.out.println("productList:" + list.size() + list);
		}
	}


}
