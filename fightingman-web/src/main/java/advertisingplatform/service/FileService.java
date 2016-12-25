package advertisingplatform.service;

import java.io.IOException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import advertisingplatform.model.File;

import com.alibaba.fastjson.JSONObject;

import dao.JdbcService;

/**
 * @author: liangzhenghui
 * @blog: http://my.oschina.net/liangzhenghui/blog
 * @email:715818885@qq.com 2015年1月31日 下午6:28:17
 */
public class FileService {
	private JdbcService jdbcService;
	

	/**
	 * 获取产品的所有图片
	 * @param productId
	 * @return
	 */
	public List getImagesOfProduct(String productId) {
		String sql = "select * from s_file where bussiness_id=? and delete_flag='0' order by create_time asc";
		Object[] args = new Object[] { productId};
		return jdbcService.queryForList(sql, args, new File());
	}

	public int saveFile(String bussiness_id, MultipartFile file) {
		String id = UUID.randomUUID().toString();
		String origin_name = file.getOriginalFilename();
		int i = origin_name.lastIndexOf(".");
		String type = origin_name.substring(i + 1);
		byte[] content = null;
		try {
			content = file.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String sql = "insert into s_file(id,bussiness_id,origin_name,type,content,create_time) values(?,?,?,?,?,?)";
		return jdbcService.update(sql, new Object[] { id, bussiness_id,
				origin_name, type, content, new Date() }, new int[] {
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.BLOB, Types.TIMESTAMP });
	}

	/**
	 * 根据产品id获取产品对应的封面图片
	 * 
	 * @param bussinessId
	 * @return
	 */
	public Map getContentByBussinessId(String bussinessId) {
		String sql = "select t.content from s_file t where t.bussiness_id=? and t.delete_flag='0' order by t.create_time asc";
		List list = jdbcService.queryForList(sql, new Object[] { bussinessId });
		Map map = null;
		if (null != list && list.size() > 0) {
			map = (Map) list.get(0);
		}
		return map;
	}

	public Map getCoverImageByBussinessId(String bussinessId) {
		String sql = "select t.content from s_file t where t.bussiness_id=?  and t.cover_image='1' and t.delete_flag='0' ";
		return jdbcService.queryForSingleRow(sql, new Object[] { bussinessId });
	}

	public Map getContentByFileId(String imgId) {
		String sql = "select t.content from s_file t where t.id=? and t.delete_flag='0' order by t.create_time asc";
		List list = jdbcService.queryForList(sql, new Object[] { imgId });
		Map map = null;
		if (null != list && list.size() > 0) {
			map = (Map) list.get(0);
		}
		return map;
	}

	public List getFilesByBussinessId(String id) {
		String sql = "select t.id,t.title,t.description from s_file t where t.bussiness_id=? and t.delete_flag='0' order by t.create_time asc";
		List list = jdbcService.queryForList(sql, new Object[] { id });
		return list;
	}

	public List getImagesOfProductByPage(int page, int size, String productId) {
		String sql = "select * from (select * from s_file where bussiness_id=? and delete_flag='0' order by create_time asc) t limit ?,?";
		Object[] args = new Object[] { productId, (page - 1) * size, size };
		return jdbcService.queryForList(sql, args, new File());
	}

	public int getImagesOfProductCount(String productId) {
		String sql = "select count(*) from s_file where bussiness_id = ? and delete_flag='0'";
		Object[] args = new Object[] { productId };
		return jdbcService.count(sql, args);
	}

	public Map getFileById(String imageId) {
		String sql = "select * from s_file where id=? and delete_flag='0'";
		return jdbcService.queryForSingleRow(sql, new Object[] { imageId });
	}

	public int editImage(JSONObject json) {
		int result = 0;
		String rollingImage = json.getString("rollingImage");
		String coverImage = json.getString("coverImage");
		String homeImage = json.getString("homeImage");
		String title = json.getString("title");
		String productId = json.getString("productId");
		String imageId = json.getString("imageId");
		String description = json.getString("description");
		if (StringUtils.isNotBlank(coverImage) && coverImage.equals("1")) {
			cancelAllCoverImagesOfProductId(productId);
		}
		String sql = "update s_file t set t.title=?,t.description=?,home_image=?,rolling_image=?,cover_image=? where t.id=? and t.delete_flag='0'";
		result = jdbcService.update(sql, new Object[] { title, description,
				homeImage, rollingImage, coverImage, imageId }, new int[] {
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR });
		return result;
	}

	public int cancelAllCoverImagesOfProductId(String productId) {
		String sql = "update s_file t set t.cover_image='0' where t.bussiness_id=? and t.delete_flag='0'";
		return jdbcService.update(sql, new Object[] { productId },
				new int[] { Types.VARCHAR });
	}

	public List getAllScrollingImages() {
		String sql = "select id,bussiness_id from s_file where  rolling_image='1' and delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.queryForList(sql, args);
	}
	
	public List getAllHomeImages() {
		String sql = "select id,bussiness_id from s_file where  home_image='1' and delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.queryForList(sql, args);
	}

	public int deleteImage(String id) {
		String sql = "delete from s_file where id= ?";
		return jdbcService.update(sql, new Object[] { id },
				new int[] { Types.VARCHAR });
	}

	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	

}
