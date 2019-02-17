package service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.omg.PortableInterceptor.INACTIVE;

import dao.ProductClassifyDao;
import dao.impl.ProductClassifyDaoImpl;
import pojo.ProductClassify;
import service.ProductClassifyService;
import util.JDBCUtil;

public class ProductClassifyServiceImpl implements ProductClassifyService {

	@Override
	public List<ProductClassify> productClassifyFind(ProductClassify productClassify) {
		ProductClassifyDao productClassifyDao = new ProductClassifyDaoImpl();

		return productClassifyDao.productSelect(productClassify);
	}

	@Override
	public int productClassifyAdd(ProductClassify productClassify) {
		ProductClassifyDao productClassifyDao = new ProductClassifyDaoImpl();
		return productClassifyDao.productInsert(productClassify);
	}

	@Override
	public boolean productClassifyDel(ProductClassify productClassify) {
		ProductClassifyDao productClassifyDao = new ProductClassifyDaoImpl();
		return productClassifyDao.productClassifyDelete(productClassify);
	}

	/**
	 * 修改
	 * @param productClassify
	 * @return
	 */
	public boolean productClassifySave(ProductClassify productClassify) {
		ProductClassifyDao productClassifyDao;
		Connection connection = null;
		boolean flag = false;
		Integer origSort = null;//初始排序值
		Integer nowSort = null;  //修改的排序值
		try {
			connection = JDBCUtil.getConnection();
			connection.setAutoCommit(false);	//开启事务
			productClassifyDao = new ProductClassifyDaoImpl(connection);
			if(productClassify.getPc_sort_show() != null){
				ProductClassify origProductClassify = new ProductClassify(); //待修改的分类
				productClassify.setCondition(" and pc_id ="+productClassify.getPc_id());
				ProductClassify nowProductClassify = new ProductClassify();  //要替代的类
				origProductClassify = productClassifyDao.productSelect(productClassify).get(0);

				int origShop_id = origProductClassify.getPc_shop_id();  //获取shop_id
				String nowCondition = " and pc_shop_id="+origProductClassify.getPc_shop_id()+" order by pc_sort asc"+" limit "+(productClassify.getPc_sort_show()-1)+",1";
				nowProductClassify.setCondition(nowCondition);
				System.out.println("nowCondition:"+nowCondition);
				nowProductClassify = productClassifyDao.productSelect(nowProductClassify).get(0);



				origSort = origProductClassify.getPc_sort();    //初始排序值
				nowSort = nowProductClassify.getPc_sort();      //修改的排序值
				System.out.println("初始排序值："+origSort);
				System.out.println("修改的排序值："+nowSort);
				String sql = "update product_classify set pc_sort = pc_sort+1 " ;
				String condition = "where pc_sort>="+nowSort+" and pc_sort<"+origSort;
				if(origSort<nowSort){
					sql = "update product_classify set pc_sort = pc_sort-1 ";
					condition = "where pc_sort>"+origSort+" and pc_sort<="+nowSort;
				}
				sql = sql + condition;
				System.out.println("sql:"+sql);
				if(productClassifyDao.operateBysql(sql)){
					productClassify.setPc_sort(nowSort);
					flag = productClassifyDao.productClassifyUpdate(productClassify);
				}else{
					connection.rollback();
				}
				connection.commit();  //提交事务
			}else {

				flag = productClassifyDao.productClassifyUpdate(productClassify);
				connection.commit(); //提交事务
			}

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();  //回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			if(connection != null){
				try {
					connection.close(); //关闭连接
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}



		return flag ;
	}

	/**
	 * 删除通过condition
	 * @param productClassify
	 * @return
	 */
	public boolean productClassifyDelByCondition(ProductClassify productClassify) {
		ProductClassifyDao productClassifyDao = new ProductClassifyDaoImpl();

		return productClassifyDao.productClassifyDeleteByCondition(productClassify);
	}

	/**
	 * 获取查询总数
	 * @param productClassify
	 * @return
	 */
	public int productClassifyCount(ProductClassify productClassify) {
		ProductClassifyDao productClassifyDao = new ProductClassifyDaoImpl();
		return productClassifyDao.productClassifyCount(productClassify);
	}


	public static void main(String[] args) {
		ProductClassifyService productClassifyService = new ProductClassifyServiceImpl();
		ProductClassify productClassify = new ProductClassify();
		productClassify.setPc_id(18);
		productClassify.setPc_shop_id(1);
		productClassify.setPc_sort_show(4);
		productClassify.setPc_name("分类ceshi");
		productClassify.setCondition(" and pc_id="+productClassify.getPc_id());
		boolean flag = productClassifyService.productClassifySave(productClassify);
		System.out.println(flag);


	}

}
