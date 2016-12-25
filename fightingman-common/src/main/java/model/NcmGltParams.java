package model;
/**
 * 只有分页的时候才需要用到
 * @author liangzhenghui
 *
 */
public class NcmGltParams {
	private String rowsCount;
	private String name;

	public NcmGltParams(String rowsCount, String name) {
		this.rowsCount = rowsCount;
		this.name = name;
	}

	public String getRowsCount() {
		return rowsCount;
	}

	public void setRowsCount(String rowsCount) {
		this.rowsCount = rowsCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
