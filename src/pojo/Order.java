package pojo;

import java.util.List;

/**
 * 订单实体类
 */
public class Order extends Base {
    Integer orde_id;          // 订单id
    String orde_name;     // 订单名
    String orde_head;     // 订单图片
    String orde_number;   // 订单编号
    Integer orde_shop_id;     // 商家id
    Integer orde_cust_id;     // 客户编号
    String orde_status;   // 订单状态 订单状态:0未支付，1已支付，2已发货，3待评价，4已完成。
    Double orde_amount;   // 订单金额
    String orde_pay;      // 支付方式
    String orde_create_time; //订单创建时间
    String orde_pay_status;  //支付状态
    String orde_receiver;    //收货人名称
    String orde_phone;       //收货人手机方式


    List<OrderDetail> details; // 订单详情

    public Integer getOrde_id() {
        return orde_id;
    }

    public void setOrde_id(Integer orde_id) {
        this.orde_id = orde_id;
    }

    public String getOrde_name() {
        return orde_name;
    }

    public void setOrde_name(String orde_name) {
        this.orde_name = orde_name;
    }

    public String getOrde_head() {
        return orde_head;
    }

    public void setOrde_head(String orde_head) {
        this.orde_head = orde_head;
    }

    public String getOrde_number() {
        return orde_number;
    }

    public void setOrde_number(String orde_number) {
        this.orde_number = orde_number;
    }

    public Integer getOrde_shop_id() {
        return orde_shop_id;
    }

    public void setOrde_shop_id(Integer orde_shop_id) {
        this.orde_shop_id = orde_shop_id;
    }

    public Integer getOrde_cust_id() {
        return orde_cust_id;
    }

    public void setOrde_cust_id(Integer orde_cust_id) {
        this.orde_cust_id = orde_cust_id;
    }

    public String getOrde_status() {
        return orde_status;
    }

    public void setOrde_status(String orde_status) {
        this.orde_status = orde_status;
    }

    public Double getOrde_amount() {
        return orde_amount;
    }

    public void setOrde_amount(Double orde_amount) {
        this.orde_amount = orde_amount;
    }

    public String getOrde_pay() {
        return orde_pay;
    }

    public void setOrde_pay(String orde_pay) {
        this.orde_pay = orde_pay;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    public String getOrde_create_time() {
        return orde_create_time;
    }

    public void setOrde_create_time(String orde_create_time) {
        this.orde_create_time = orde_create_time;
    }

    public String getOrde_pay_status() {
        return orde_pay_status;
    }

    public void setOrde_pay_status(String orde_pay_status) {
        this.orde_pay_status = orde_pay_status;
    }

    public String getOrde_receiver() {
        return orde_receiver;
    }

    public void setOrde_receiver(String orde_receiver) {
        this.orde_receiver = orde_receiver;
    }

    public String getOrde_phone() {
        return orde_phone;
    }

    public void setOrde_phone(String orde_phone) {
        this.orde_phone = orde_phone;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orde_id=" + orde_id +
                ", orde_name='" + orde_name + '\'' +
                ", orde_head='" + orde_head + '\'' +
                ", orde_number='" + orde_number + '\'' +
                ", orde_shop_id=" + orde_shop_id +
                ", orde_cust_id=" + orde_cust_id +
                ", orde_status='" + orde_status + '\'' +
                ", orde_amount=" + orde_amount +
                ", orde_pay='" + orde_pay + '\'' +
                ", orde_create_time='" + orde_create_time + '\'' +
                ", details=" + details +
                '}';
    }


}
