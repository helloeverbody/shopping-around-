package dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import dao.ShoppingCartDao;
import dao.refector.AbstractDao;
import pojo.ShoppingCart;
import util.JDBCUtil;



public class ShoppingCartDaoImpl extends AbstractDao implements ShoppingCartDao {

    private QueryRunner runner = null;// 查询运行器

    public ShoppingCartDaoImpl() {
        // this.runner = new QueryRunner(); //无参构造，通过connection
        this.runner = new QueryRunner(JDBCUtil.getDataSource()); // 通过数据源构造

    }

    @Override
    public List<ShoppingCart> productSelect(ShoppingCart shoppingCart) {

        String tableName  = "shopping_cart";
        return super.select(shoppingCart, tableName, runner);
    }

    @Override
    public int productInsert(ShoppingCart shoppingCart) {

        return super.insert(shoppingCart, "shopping_cart", runner);
    }

    @Override
    public boolean shoppingCartDelete(ShoppingCart shoppingCart) {

        int id = shoppingCart.getCart_id();
        String tableName = "shopping_cart";
        String idName = "cart_id";
        return super.delete(id, tableName, idName, runner);
    }

    @Override
    public boolean shoppingCartUpdate(ShoppingCart shoppingCart) {

        String tableName = "shopping_cart";
        String idName = "cart_id";
        return super.update(shoppingCart, tableName, idName, runner);
    }

    @Override
    public boolean shoppingCartDeleteByCondition(ShoppingCart shoppingCart) {
        // TODO 自动生成的方法存根
        String tableName = "shopping_cart";
        String condition = shoppingCart.getCondition();
        return super.deleteByCondition(tableName, condition, runner);
    }



}
