package TCPNioClient;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.luan.*;

public class CommunicationProtocolTcpNIO {
	private SocketChannel socketChannel=null;
	private FileDownloadingProtocol fdm;
	private int port=0;
	private String ip=null;
	public CommunicationProtocolTcpNIO(String ip,int port){
		this.ip=ip;
		this.port=port;
		try{
			socketChannel = SocketChannel.open();
	        socketChannel.configureBlocking(true);
	        socketChannel.connect(new InetSocketAddress(ip, port));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		StartFileDownloadingMechanism();
	}
	
	private void StartFileDownloadingMechanism(){
		this.fdm= new FileDownloadingProtocol();
		Thread fdmThread = new Thread(fdm,"FileDownloadingMechanism");
		fdmThread.start();
	}

    public void closeConnection(){
    	try{
    		this.socketChannel.close();
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    public int getServerPort(){
    	return this.port;
    }
    
    public String getServerIp(){
    	return this.ip;
    }
    
    public FileDownloadingProtocol getFileDownloadingProtocol(){
    	return this.fdm;
    }
	
	public  void sendLUANObject(LUANObject luan) {
        try {
        	ByteBuffer buffer = ByteBuffer.allocateDirect(500000);
        	buffer = ByteBuffer.wrap(luan.getBytes());
        	while (buffer.hasRemaining()) {
                socketChannel.write(buffer);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	public LUANObject receiveLUANObject(){
    	LUANObject luan =null;
    	byte[] headerBytes=null;
    	try{
    		ByteBuffer buffer = ByteBuffer.allocateDirect(500000);//1000024
    		socketChannel.read(buffer);
    		buffer.flip();
            headerBytes = new byte[buffer.remaining()];
            buffer.get(headerBytes);
            luan = new LUANObject(headerBytes);
            System.out.println("\n\nluan.toString() == "+new String(headerBytes,"ISO-8859-1")+"\n\n");
    	}catch(Exception ex){
    		if(ex instanceof java.nio.channels.AsynchronousCloseException){
				stopCurrentThread();
			}
    		else if(ex instanceof IOException && ex.getMessage().toString().equals("An existing connection was forcibly closed by the remote host") ){
        		closeConnection();
        		System.exit(0);
        		luan=null;
        	}else if(ex instanceof LUANException){
        		fdm.addByteArrayToByteArrList(headerBytes);
        		luan=null;
        	}else{
        		ex.printStackTrace();
        		luan=null;
        	}
    	}
    	return luan;
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
