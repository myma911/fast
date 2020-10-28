package cn.aaron911.common.result;


import java.util.Collections;
import java.util.List;

/**
 * 封装分页结果返回
 */
public class PageResult<T> {

    private long total = 0;
    private List<T> rows = Collections.emptyList();

    public PageResult() {
    }

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
