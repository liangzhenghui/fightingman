/**
 * 2018年7月8日
 * liangzhenghui
 */
package fightingman.lxj.service;

import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import dao.JdbcService;
import fightingman.lxj.model.Activity;
import util.UUIDGenerator;
@Service
public class ActivityService {
	@Resource
	private JdbcService jdbcService;

	public int add(Activity activity) {
		String sql = "insert into b_activity(id,activity_place,activity_name,activity_costStyle,activity_time) values(?,?,?,?,?)";
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			String dateStr = activity.getActivity_date() + " " + activity.getActivity_time();
			date = time.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jdbcService.update(sql,
				new Object[] { UUIDGenerator.generateUUID(), activity.getActivity_place(), activity.getActivity_name(),
						activity.getActivity_costStyle(), date },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP });
	}

	public List list() {
		String sql = "select t.*,DATE_FORMAT(t.activity_time,'%Y-%m-%d %H:%m:%s') as activity_time_str from b_activity t where t.activity_time>now() ";
		return jdbcService.queryForList(sql, null);
	}

}
