/**
 * 2018年8月29日
 * liangzhenghui
 */
package proxy;

import java.util.Date;

public class MyLogger {
	public static void start() {
		System.out.println(new Date() + "say hello start…");
	}

	public static void end() {
		System.out.println(new Date() + "say hello end");
	}
}
