package pojo;

import java.util.ArrayList;
import java.util.List;

public class BasePojo<T> {

	private boolean success;
	private String msg;
	private Integer page; // 页码
	private Integer total; // 总页数
	private Integer limit; //当前页数条数
	private List<T> data = new ArrayList<T>();

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<T> getList() {
		return data;
	}
	public void setList(List<T> list) {
		this.data = list;
		this.total = list.size();
		this.limit = this.data.size();
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {

		this.limit = limit;
	}

	@Override
	public String toString() {
		return "BasePojo{" +
				"success=" + success +
				", msg='" + msg + '\'' +
				", page=" + page +
				", total=" + total +
				", limit=" + limit +
				", data=" + data +
				'}';
	}
}
