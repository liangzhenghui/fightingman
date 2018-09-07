package threadTest;

public class ThreadTest {
	public static void main(String args[]){
		ThreadObject thread=new ThreadObject();
		thread.start();
		//target - the object whose run method is invoked when this thread is started. If null, this classes run method does nothing.
		Thread threadRunnable=new Thread(new RunnableTest());
		threadRunnable.start();
		System.out.println("main thread");
	}
}
