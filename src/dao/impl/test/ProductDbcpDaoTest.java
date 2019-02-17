package dao.impl.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ProductDao;
import dao.impl.ProductDbcpDaoImpl;
import pojo.Product;


/**
 * @Title: ProductDbcpDaoTest
 * @Description: 测试Dbutils类
 * @author yhfu
 * @date 2018年10月23日 上午9:27:42
 */
public class ProductDbcpDaoTest {


	static void queryProductByConnection(ProductDao productDao, Product product) {
		List<Product> productList = new ArrayList<Product>();
		productList = productDao.productSelect(product);
		System.out.println("productList:" + productList);
	}

	static int insertProduct(ProductDao productDao, Product product) {
		return productDao.productInsert(product);
	}

	static boolean deleteProduct(ProductDao productDao, Product product) {
		return productDao.productDelete(product);
	}

	static boolean updateProduct(ProductDao productDao, Product product) {
		return productDao.productUpdate(product);
	}

	public static void main(String[] args) throws SQLException {
		ProductDao productDao = new ProductDbcpDaoImpl();
		Product product = new Product();
		product.setCondition("");
		product.setProd_name("4已修 改 ");
		product.setProd_shop_id(1);
		product.setProd_price(45d);
		product.setProd_clas_id(2);
		product.setProd_id(5);

		// 两表查询
		ProductDao productDao2 = new ProductDbcpDaoImpl();
		product.setCondition(" and shop_id=prod_shop_id ORDER BY prod_shop_id");
		List<Product> productList = new ArrayList<Product>();
		productList = productDao2.prodShopSelect(product);
		

		List<List<Product>> list = new ArrayList<List<Product>>();
		List<Product> listTemp = new ArrayList<Product>();
		listTemp.add(productList.get(0));
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
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println("productList:" + list.get(i).size() + list.get(i));
		}

		/*for (Product product2 : productList) {
			
			System.out.println("productList:" + product2 + product2.getShop_name());
		}*/
		

		// product.setCondition("and prod_id=3");
		// queryProductByConnection(productDao, product);
		// 测试不通过数据源排序
		// queryProductByConnection(productDao, product);
		// 插入
		// System.out.println("result: " + productDao.productInsert(product));
		// 删除
		// System.out.println(deleteProduct(productDao, product));

		// 修改
		// System.out.println(updateProduct(productDao, product));

		//System.out.println("de");

	}

}
