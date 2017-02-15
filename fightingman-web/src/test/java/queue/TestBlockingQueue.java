package queue;

import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TestBlockingQueue {  
	  
    public static void main(String[] args) {  
        final BlockingQueue<Integer> queue=new LinkedBlockingQueue<Integer>(3);  
        final Random random=new Random();  
          
        class Producer implements Runnable{  
            @Override  
            public void run() {  
                while(true){  
                    try {  
                        int i=random.nextInt(100);  
                        long start=new Date().getTime();
                        queue.put(i);//,put方法往底部添加数据,若 当队列达到容量时候，会自动阻塞的
                        long end=new Date().getTime();
                        System.out.println("等待"+String.valueOf(end-start)+"毫秒");
                        Iterator it=queue.iterator();
                        while(it.hasNext()){
                        	System.out.println(it.next());
                        }
                        if(queue.size()==3)  
                        {  
                            System.out.println("full");  
                           
                        }  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        }  
          
        class Consumer implements Runnable{  
            @Override  
            public void run() {  
                while(true){  
                    try {  
                        queue.take();//从队列头部取出数据,当队列为空时，也会自动阻塞  
                        Thread.sleep(3000);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        }  
          
        new Thread(new Producer()).start();  
        new Thread(new Consumer()).start();  
    }  
  
}  