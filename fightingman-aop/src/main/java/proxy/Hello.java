/**
 * 2018年8月29日
 * liangzhenghui
 */
package proxy;

public class Hello implements IHello {

	@Override
	public void sayHello(String str) {
		System.out.println("hello");
	}

}
