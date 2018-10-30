package fightingman.lxj.model;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @Date:2018年9月23日
 * @Author:liangzhenghui
 * @Github:https://github.com/liangzhenghui
 * @Email:liangzhenghui@gmail.com
 * @Description:
 */
public class Demo {
	@NotBlank(message="不能为空")
	private String test;

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
}
