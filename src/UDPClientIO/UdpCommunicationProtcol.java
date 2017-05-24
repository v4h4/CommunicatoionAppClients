package UDPClientIO;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.luan.LUANObject;

public class UdpCommunicationProtcol{
	private DatagramSocket datagramServerSocket = null;
	private int serverPort;
	private InetAddress serverIP=null;
	  //
	//  private boolean lock=false;
	public UdpCommunicationProtcol(int port,String ip){
		try {
			this.datagramServerSocket=new DatagramSocket();
			this.serverIP=InetAddress.getByName("127.0.0.1");
			this.serverPort=port;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
			
	public LUANObject reciveDatagramLUANObject(){
		LUANObject luan=null;
		byte[] inputData = new byte[60000];
		try{
			DatagramPacket datagramPackage = new DatagramPacket(inputData, inputData.length);
			datagramServerSocket.receive(datagramPackage);
			if(datagramPackage.getData()!=null){
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(datagramPackage.getData());
				ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
				luan=(LUANObject) objectInputStream.readObject();
			}
		}catch(Exception ex){
			if(ex.getMessage().equals("socket closed")){
				stopCurrentThread();
			}else{
				ex.printStackTrace();
				luan=null;
			}
		}
		return luan;
	}
	
	public void sendDatagramLUANObject(LUANObject luan){
		if(luan!=null){
			byte[] outputData = new byte[60000];
			try{
				ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(outputData.length);
		        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(byteOutputStream));
		        objectOutputStream.writeObject(luan);
		        objectOutputStream.flush();
		        byte[] byteArray = byteOutputStream.toByteArray();
		        DatagramPacket packetOutput = new DatagramPacket(byteArray, byteArray.length,serverIP,serverPort);
		        datagramServerSocket.send(packetOutput);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}		
	}
	
	public String getServerIP(){
		return serverIP.getHostAddress();
	}
	
	public int getServerPort(){
		return this.serverPort;
	}
	
	public void closeConnection(){
		try{
			this.datagramServerSocket.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
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
