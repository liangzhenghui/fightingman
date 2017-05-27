package adapter;
//refrences from http://blog.csdn.net/zhangjg_blog/article/details/18735243
public class TestAdapter {  
	  
    public static void main(String[] args) {  
          
        GBSocketInterface gbSocket = new GBSocket();  
          
        Hotel hotel = new Hotel();  
          
        SocketAdapter socketAdapter = new SocketAdapter(gbSocket);  
          
        hotel.setSocket(socketAdapter);  
          
        hotel.charge();  
    }  
}  