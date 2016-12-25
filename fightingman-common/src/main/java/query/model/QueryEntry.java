package query.model;
/**
 * 查询实体
 * @author: liangzhenghui
 * @blog: http://my.oschina.net/liangzhenghui/blog
 * @email:715818885@qq.com
 * 2015年7月31日 上午11:26:27
 */
public class QueryEntry {
	private String id;
	private String title;
	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getTableName() {
		return tableName;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	private String tableName;
}
