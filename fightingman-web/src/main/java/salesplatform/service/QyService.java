package salesplatform.service;

import java.sql.Types;
import java.util.Date;

import dao.JdbcService;
import util.UUIDGenerator;
/**
 * 企业服务
 * @author liangzhenghui
 *
 */
public class QyService {
	private JdbcService jdbcService;
	/**
	 * 保存企业基本信息以及返回企业id
	 * @param qymc
	 * @param qydz
	 * @param lxdh
	 * @param jd
	 * @param wd
	 * @return
	 */
	public String saveQyxx(String qymc, String qydz, String lxdh, String jd, String wd) {
		String id = UUIDGenerator.generateUUID();
		StringBuilder sql = new StringBuilder();
		sql.append("insert into s_qyxx(id,qymc,lxdh,qydz,jd,wd,create_time,delete_flag)");
		sql.append("values(?,?,?,?,?,?,?,?)");
		jdbcService.update(sql.toString(), new Object[]{id,qymc,lxdh,qydz,jd,wd,new Date(),'0'},
				new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR});
		return id;
	}
	
	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}
}
