package UDPClientIO;

import org.luan.LUANObject;

import ClientSelectionGUI.MainFrameClientSelection;
import GUI.CommunicationProtocolSelectorForGui;
import GUI.MainFrameGUI;

public class UdpClientIO implements Runnable{
	private UdpCommunicationProtcol com;
	private MainFrameGUI gui = null;
	private FileDownloadingProtocol fdp=null;
	private long milliSecondsTimer=0;
	private int packageCounter=0;
	private int totalPackages=0;
    public UdpClientIO(MainFrameClientSelection mainFrameServerSelection,String ipNbr, int port){
		this.com=new UdpCommunicationProtcol(port, ipNbr);
		this.gui = new MainFrameGUI(mainFrameServerSelection,new CommunicationProtocolSelectorForGui(com),"UDP IO - Client");
		this.fdp = new FileDownloadingProtocol();
		new Thread(fdp,"FileDownload Protocol").start();
    }
	
    public void run(){
    	com.sendDatagramLUANObject(getBlobContentp());
    	while(true){
    		reciviedMessageHandler(com.reciveDatagramLUANObject());	
    	}
    }
    
    public void reciviedMessageHandler(LUANObject cmd){
    	try{
    		if(cmd.getString("headCmd").equals("c2f")){
    			gui.setTemperatureC2F(cmd.getDouble("temperature"));
    		}
    		else if(cmd.getString("headCmd").equals("f2c")){
    			gui.setTemperatureF2C(cmd.getDouble("temperature"));
    		}
    		else if(cmd.getString("headCmd").equals("Date")){
    			gui.setCurrentDate(cmd.getString("Date"));
    		}
    		else if(cmd.getString("headCmd").equals("Time")){
    			gui.setCurrentTime(cmd.getString("Time"));
    		}
    		else if(cmd.getString("headCmd").equals("Sum of List")){
    			gui.setSumOfList(cmd.getInteger("Sum of List"));
    		}
    		else if(cmd.getString("headCmd").equals("Max of List")){
    			gui.setMaximumIntegerOfList(cmd.getInteger("Max of List"));
    		}
    		else if(cmd.getString("headCmd").contains("File Download")){
    			if(cmd.getString("headCmd").equals("File Download START")){
    				StartMilliSecondsTimer();
    				fdp.addLUANObjects(cmd);
    				totalPackages=cmd.getInteger("totaltAmountOfPackages");
    				packageCounter++;
    				gui.updateDownloadProcentageProcess(packageCounter/totalPackages);
    			}
    			if(cmd.getString("headCmd").equals("File Download")){
    				fdp.addLUANObjects(cmd);
    				packageCounter++;
    				gui.updateDownloadProcentageProcess(packageCounter/totalPackages);
    			}
    			if(cmd.getString("headCmd").equals("File Download END")){
    				fdp.addLUANObjects(cmd);
    				endMilliSecondsTimer();
    				gui.setDownloadingTime(milliSecondsTimer);
    				gui.clearDownloadProcentageProcess();
    			}
    		}
    		else if(cmd.getString("headCmd").equals("Start-up Avalible Files")){
    			gui.setStartUpAlvalibleBlobFiles(cmd.getStringArray("Avalible Files"));
    		}
    		else if(cmd.getString("headCmd").equals("Quit")){
    			
    		}
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    private void StartMilliSecondsTimer(){
		milliSecondsTimer=0;
		milliSecondsTimer=System.currentTimeMillis();
	}
	
	private void endMilliSecondsTimer(){
		milliSecondsTimer = System.currentTimeMillis()-milliSecondsTimer;
	}
	
	private LUANObject getBlobContentp(){
    	LUANObject luan=null;
    	try{
    		luan = new LUANObject();
        	luan.put("headCmd", "Start-up Avalible Files");
        
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	return luan;
    }
}
