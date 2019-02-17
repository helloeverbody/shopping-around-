package pojo;

/**
 * 实体类基类
 *
 */
public class Base {

	String condition;// 查询条件
	Integer limit;// 记录数
	Integer pages;
	String orderBy;// 排序条件
  

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public String toString() {
		return "Base{" +
				"condition='" + condition + '\'' +
				", limit=" + limit +
				", pages=" + pages +
				", orderBy='" + orderBy + '\'' +
				'}';
	}
}
