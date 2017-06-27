package salesplatform.model;

import java.util.List;

/**分页对象
 * Created by liangzhenghui on 2015/12/26 0026.
 */
public class Page<T> {
    private int pageNum;
    private int pageSize;
    private int totalPage;
    private List<T> data;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
