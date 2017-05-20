package extend;

public abstract class Test2 extends Test3{
	public void test(){
		System.out.println("2");
	}
	
	public void template(){
		help();
	}
	
	public abstract void help();
}
