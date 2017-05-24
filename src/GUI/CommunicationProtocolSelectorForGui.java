package GUI;

import java.io.File;
//import java.rmi.server.UnicastRemoteObject;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import org.luan.LUANObject;

import RMI.RmiInterface;
import TCPClientIO.CommunicationProtocolTcpIO;
import TCPNioClient.CommunicationProtocolTcpNIO;
import UDPClientIO.UdpCommunicationProtcol;

public class CommunicationProtocolSelectorForGui {
	private CommunicationProtocolTcpIO tcpComIO=null; 
	private CommunicationProtocolTcpNIO tcpComNIO=null;
	private UdpCommunicationProtcol udpCom=null;
	private RmiInterface rmiInterface=null;
	private long milliSecondsTimer=0;
	private PanelForDownload panelForDownload=null;
	
	public CommunicationProtocolSelectorForGui(CommunicationProtocolTcpIO tcpComIO){
		this.tcpComIO=tcpComIO;
	}
	
	public CommunicationProtocolSelectorForGui(CommunicationProtocolTcpNIO tcpComNIO){
		this.tcpComNIO=tcpComNIO;
	}
	
	public CommunicationProtocolSelectorForGui(UdpCommunicationProtcol udpCom){
		this.udpCom=udpCom;
	}
	
	public CommunicationProtocolSelectorForGui(RmiInterface rmiInterface){
		try{
			this.rmiInterface=rmiInterface;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public boolean comIsRMI(){
		if(rmiInterface!=null){
			return true;
		}else{
			return false;
		}
	}
	
	public void sendLUANObect(LUANObject luan){
		if(tcpComIO!=null){
			tcpComIO.sendLUANObject(luan);
		}else if(tcpComNIO!=null){
			tcpComNIO.sendLUANObject(luan);
		}else if(udpCom!=null){
			udpCom.sendDatagramLUANObject(luan);
		}
	}
	
	public LUANObject reciveLUANObect(){
		if(tcpComIO!=null){
			return tcpComIO.reciveLUANObject();
		}else if(tcpComNIO!=null){
			return tcpComNIO.receiveLUANObject();
		}else if(udpCom!=null){
			return udpCom.reciveDatagramLUANObject();
		}
		return null;
	}
	
	public int getSumOfListRMI(int[] intList){
		try {
			return rmiInterface.getSumOfList(intList);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	
	public int getMaxOfListRMI(int[] intList){
		try{
			return rmiInterface.getMaxOfList(intList);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0;
	}
	
	public double celsiusToFahrenheit(double celsius){
		try{
			return rmiInterface.celsiusToFahrenheit(celsius);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0;
	}
	
	public double fahrenheitToCelsius(double fahrenheit){
		try{
			return rmiInterface.fahrenheitToCelsius(fahrenheit);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0;
	}
	
	public String getCurrentDate(){
		try{
			return rmiInterface.getCurrentDate();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "error";
	}
	
	public String getCurrentTime(){
		try{
			return rmiInterface.getCurrentTime();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "error";
	}
	
	public void downloadFile(String fileName){
		try{
			StartMilliSecondsTimerRMI();
			byte[] byteArr= rmiInterface.downloadFile(fileName);
			panelForDownload.setDownloadingTime(endMilliSecondsTimerRMI());
			saveDialog(byteArr, getFileType(fileName), fileName);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private String getFileType(String fileName){
		int length=0;
		for(int i=fileName.length()-1;i>=0;i--){
			if(fileName.charAt(i)!='.'){
				length++;
			}else{
				length++;
				break;
			}
		}
		char[] string = new char[length];  
		int index=(length-1);
		for(int i=(fileName.length()-1);i>=0 && index>=0;i--){
				string[(index)]=fileName.charAt(i);
				index--;
		}
		return  new String(string);
	}
	
	private void saveDialog(byte[] byteFile,String fileType,String filename){
		try{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Save the file: "+filename);
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			
				File _file_ = fileChooser.getSelectedFile();
				String fileName=fileChooser.getCurrentDirectory()+"\\"+fileChooser.getName(_file_)+fileType;
				FileOutputStream fos = new FileOutputStream(fileName);
			    fos.write(byteFile);
			    fos.close();
			  //  removeAllItemsFromSegemntedByteArrayes();
			  //  luanInfo=null;
			  //  downloadDone=false;
			    //erase grapihcs
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void StartMilliSecondsTimerRMI(){
		milliSecondsTimer=0;
		milliSecondsTimer=System.currentTimeMillis();
	}
	
	private long endMilliSecondsTimerRMI(){
		return milliSecondsTimer = (System.currentTimeMillis()-milliSecondsTimer);
	}
	
	public void setDownloadPanel(PanelForDownload panelForDownload){
		this.panelForDownload=panelForDownload;
	}
	
	public void getAllAvalibleFilesFromServerBlob(){
		try{
				LUANObject luan = new LUANObject();
				luan.put("headCmd", "Start-up Avalible Files");
				sendLUANObect(luan);
				System.out.println("Start-up Avalible Files - Completed");
		}catch(Exception ex){
			ex.printStackTrace();
		}	
	}
	
	public String[] getAllAvalibleFilesFromRmiServerBlob(){
		try {
			String[] files= rmiInterface.getAllFilesFromBlob();
			System.out.println("Start-up Avalible Files - Completed");
			return files;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public void closeConnection(){
		if(tcpComIO!=null){
			closeTcoIoConnection();
		}else if(tcpComNIO!=null){
			closeTcoNioConnection();
		}else if(udpCom!=null){
			closeUdpIoConnection();
		}else if(rmiInterface!=null){
			closeRmiConnection();
		}
	}
	
	private void closeTcoIoConnection(){
		try{
			tcpComIO.closeConnection();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void closeTcoNioConnection(){
		try{
			tcpComNIO.closeConnection();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void closeUdpIoConnection(){
		try{
			udpCom.closeConnection();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void closeRmiConnection(){
		try{
		//	UnicastRemoteObject.unexportObject(rmiInterface, true);
		}catch(Exception ex){
			ex.printStackTrace();
		}		
	}
	
	
}
