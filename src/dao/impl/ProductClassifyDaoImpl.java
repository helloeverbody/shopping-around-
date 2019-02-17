package dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import dao.ProductClassifyDao;
import dao.refector.AbstractDao;
import pojo.ProductClassify;
import util.JDBCUtil;

public class ProductClassifyDaoImpl extends AbstractDao implements ProductClassifyDao {

    private QueryRunner runner = null;       // 查询运行器
    private Connection connection = null;   // 数据库连接对象

    public ProductClassifyDaoImpl() {
        // this.runner = new QueryRunner(); //无参构造，通过connection
        this.runner = new QueryRunner(JDBCUtil.getDataSource()); // 通过数据源构造

    }

    public ProductClassifyDaoImpl(Connection connection) {
        this.runner = new QueryRunner();  //无参，通过connection构造
        this.connection = connection;
    }

    @Override
    public List<ProductClassify> productSelect(ProductClassify productClassify) {
        List<ProductClassify> list = new ArrayList<>();
        String tableName = "product_classify";
        String condition = productClassify.getCondition();
        Integer pages = productClassify.getPages();
        Integer limit = productClassify.getLimit();
        pages = pages == null ? 1:pages;
        limit = limit == null ? 0:limit;
        int sortBegin = (pages-1) * limit;
        System.out.println("pages:"+pages);
        System.out.println("limit:"+limit);
        System.out.println("sortBegin:"+sortBegin);
        String sql = "select * ,(@i:=@i+1) as pc_sort_show from product_classify,(select @i:="+sortBegin+") as i where 1=1 ";

        if (condition != null) {
            sql += condition;
        } else {
        }

        System.out.println(sql);

        try {
            if (connection == null) {
                list = runner.query(sql, new BeanListHandler<ProductClassify>(ProductClassify.class));

                return list;
            } else {
                return runner.query(connection, sql, new BeanListHandler<ProductClassify>(ProductClassify.class));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        //return super.select(productClassify, tableName, runner, connection);
    }

    @Override
    public int productInsert(ProductClassify productClassify) {
        String prefix = "Pc";
        String sql = "SELECT MAX(pc_sort) FROM `product_classify` where 1=1 and pc_shop_id=" + productClassify.getPc_shop_id();
        if(productClassify.getPc_sort_show()!=null){
            productClassify.setPc_sort_show(null);
        }
        try {
            if (connection == null) {
                Integer maxSort = runner.query(sql, new ScalarHandler<Integer>());
                maxSort = maxSort == null ? 0:maxSort;
                System.out.println("maxSort:"+maxSort);
                productClassify.setPc_sort(maxSort+1);
                return super.insert(productClassify, "product_classify", runner);
            } else {
                return super.insert(productClassify, "product_classify", prefix, runner, connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }

    @Override
    public boolean productClassifyDelete(ProductClassify productClassify) {

        int id = productClassify.getPc_id();
        String tableName = "product_classify";
        String idName = "pc_id";
        return delete(id, tableName, idName, runner, connection);
    }

    @Override
    public boolean productClassifyUpdate(ProductClassify productClassify) {

        String tableName = "product_classify";
        String idName = "pc_id";
        String prefix = "Pc";
        productClassify.setPc_sort_show(null);
        if (connection == null) {
            return update(productClassify, tableName, idName, runner);
        } else {
            return update(productClassify, tableName, idName, prefix, runner, connection);
        }

    }

    /**
     * 通过condition删除
     *
     * @param productClassify
     * @return
     */
    public boolean productClassifyDeleteByCondition(ProductClassify productClassify) {
        String tableName = "product_classify";
        return super.deleteByCondition(tableName, productClassify.getCondition(), runner, connection);
    }

    /**
     * 获取分类总数
     *
     * @param productClassify
     * @return
     */
    public int productClassifyCount(ProductClassify productClassify) {
        String idName = "pc_id";
        String tableName = "product_classify";
        String condition = productClassify.getCondition();
        return super.getAllCount(idName, tableName, condition, runner, connection);
    }

    @Override
    public boolean operateBysql(String sql) {

        return super.operateBysql(sql, runner, connection);


    }


    public static void main(String[] args) {

        ProductClassifyDao productClassifyDao = new ProductClassifyDaoImpl();


        ProductClassify productClassify = new ProductClassify();

        productClassify.setPc_shop_id(1);
        productClassify.setPages(1);
        productClassify.setLimit(10);
        productClassify.setCondition("and pc_shop_id =1 order by pc_sort asc limit 0,10");
        List<ProductClassify> list = new ArrayList<>();
        list = productClassifyDao.productSelect(productClassify);

        for (ProductClassify productClassify1 : list) {
            System.out.println(productClassify1);
        }
        System.out.println(list);
    }

    public  void insertTest () {
        ProductClassifyDao productClassifyDao = new ProductClassifyDaoImpl();
        ProductClassify productClassify = new ProductClassify();
        productClassify.setPc_shop_id(2);  //添加时必须加上
        productClassify.setPc_name("添加测试");
        int id = productClassifyDao.productInsert(productClassify);
        System.out.println("id:"+id);
    }



}
