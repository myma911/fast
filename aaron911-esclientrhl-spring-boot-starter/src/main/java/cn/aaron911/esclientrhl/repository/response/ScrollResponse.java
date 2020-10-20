package cn.aaron911.esclientrhl.repository.response;

import java.util.List;


public class ScrollResponse<T> {
	private String scrollId;
    private List<T> list;

    public ScrollResponse(String scrollId, List<T> list) {
    	this.scrollId = scrollId;
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getScrollId() {
        return scrollId;
    }

    public void setScrollId(String scrollId) {
        this.scrollId = scrollId;
    }
}
