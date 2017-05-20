package salesplatform.service;

import java.sql.Types;

import javax.annotation.Resource;

import dao.JdbcService;
import util.UUIDGenerator;

public class AppService {
	private JdbcService jdbcService;

	private FileService fileService;

	public int createAppVersion(String versionCode, String versionName, String origin_name, String type, byte[] bytes) {
		String id = UUIDGenerator.generateUUID();
		String fileName = "tumblr" + versionName + ".apk";
		String sql = "insert into s_appversion(id,versionCode,versionName) values(?,?,?)";
		jdbcService.update(sql, new Object[] { id, versionCode, versionName },
				new int[] { Types.VARCHAR, Types.VARCHAR,Types.VARCHAR });
		return fileService.saveFile(id, origin_name, fileName, type, bytes);
	}

	public int deleteAppService(String id) {
		String sql = "delete from s_appversion where id=?";
		return jdbcService.update(sql, new Object[] { id }, new int[] { Types.VARCHAR });
	}

	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	public FileService getFileService() {
		return fileService;
	}

	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
}
