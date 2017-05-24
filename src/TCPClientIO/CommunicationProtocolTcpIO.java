package TCPClientIO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.luan.LUANObject;

public class CommunicationProtocolTcpIO {
	private Socket socket;
	private String ip=null;
	private int port=0;
	public CommunicationProtocolTcpIO(String serverIP,int port){
		try{
			this.ip=serverIP;
			this.port=port;
			this.socket = new Socket(this.ip, this.port);
		}catch(Exception ex){
			ex.printStackTrace();
		} 
	}
	
	public void sendLUANObject(LUANObject luan){
		try {
			ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
			objectOutput.writeObject(luan);
			objectOutput.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	 public LUANObject reciveLUANObject(){
	    	LUANObject luan=null;
	    	try{
	    		ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
	    		luan=(LUANObject) objectInput.readObject();
	    	}catch(Exception ex){
	    		if(ex.getMessage().equals("Socket closed")){
					stopCurrentThread();
				}else{
					ex.printStackTrace();
		    		return null;
				}
	    	}
	    	return luan;
	    }
	 
	 public void closeConnection(){
		 try{
			 this.socket.close();
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
	 }
	 
	 public String getServerIP(){
		 return ip;
	 }
	 
	 public int getServerPort(){
		 return this.port;
	 }
	 
	 @SuppressWarnings("deprecation")
		private void stopCurrentThread(){
			try{
				Thread.currentThread().stop();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
}
