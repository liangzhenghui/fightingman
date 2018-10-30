/**
 * 2018年7月8日
 * liangzhenghui
 */
package fightingman.lxj.model;

import org.hibernate.validator.constraints.NotBlank;

public class Activity {
	@NotBlank(message="不能为空")
	private String activity_name;
	private String activity_place;
	private String activity_date;
	private String activity_time;
	private String activity_costStyle;
	public String getActivity_name() {
		return activity_name;
	}
	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
	public String getActivity_place() {
		return activity_place;
	}
	public void setActivity_place(String activity_place) {
		this.activity_place = activity_place;
	}
	public String getActivity_date() {
		return activity_date;
	}
	public void setActivity_date(String activity_date) {
		this.activity_date = activity_date;
	}
	public String getActivity_time() {
		return activity_time;
	}
	public void setActivity_time(String activity_time) {
		this.activity_time = activity_time;
	}
	public String getActivity_costStyle() {
		return activity_costStyle;
	}
	public void setActivity_costStyle(String activity_costStyle) {
		this.activity_costStyle = activity_costStyle;
	}
}
