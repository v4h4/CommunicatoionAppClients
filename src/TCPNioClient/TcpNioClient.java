package TCPNioClient;
import org.luan.LUANObject;

import ClientSelectionGUI.MainFrameClientSelection;
import GUI.CommunicationProtocolSelectorForGui;
import GUI.MainFrameGUI;

public class TcpNioClient implements Runnable{
	private CommunicationProtocolTcpNIO com=null;
	private FileDownloadingProtocol fdm=null;
    private long milliSecondsTimer;
	private MainFrameGUI gui = null;
	public TcpNioClient(String ipNbr, int port,MainFrameClientSelection mainFrameServerSelection){
		this.com=new CommunicationProtocolTcpNIO(ipNbr, port);
		this.gui = new MainFrameGUI(mainFrameServerSelection,new CommunicationProtocolSelectorForGui(com),"TCP NEW IO - Client");
		this.fdm=com.getFileDownloadingProtocol();
	}

	public void run(){
		com.sendLUANObject(getBlobContentp());
    	while(true){
    		try{
    			reciviedMessageHandler(com.receiveLUANObject());  	    		
    		}catch(Exception ex){
    			ex.printStackTrace();
    		}
    	}
    }
    
    public void reciviedMessageHandler(LUANObject cmd){
    	try{
    		if(cmd!=null && cmd.getString("headCmd")!=null){
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
        		}else if(cmd.getString("headCmd").contains("File Download")){
        			if(cmd.getString("headCmd").equals("File Download START")){
        				fdm.addStartLUANObject(cmd);
        				StartMilliSecondsTimer(cmd.getString("fileName")+cmd.getString("fileType"));
        				System.out.println("Download File START...");
        			}
        			else if(cmd.getString("headCmd").equals("File Download END")){
        				fdm.addEndLUANObject(cmd);
        				endMilliSecondsTimer(cmd.getString("fileName")+cmd.getString("fileType"));
        				gui.setDownloadingTime(milliSecondsTimer);
        				System.out.println("Download File END...");
        			}		
        		}
        		else if(cmd.getString("headCmd").equals("Start-up Avalible Files")){
        			gui.setStartUpAlvalibleBlobFiles(cmd.getStringArray("Avalible Files"));
        		}else if(cmd.getString("headCmd").equals("Quit")){
        			
        		}
        	}
       }catch(Exception ex){
    		ex.printStackTrace();
    		System.exit(0);
    	}
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
    
    private void StartMilliSecondsTimer(String name){
		milliSecondsTimer=0;
		System.out.println("START - "+name+".txt - time: "+milliSecondsTimer);
		milliSecondsTimer=System.currentTimeMillis();
	}
	
	private void endMilliSecondsTimer(String name){
		milliSecondsTimer = System.currentTimeMillis()-milliSecondsTimer;
		System.out.println("END - "+name+" - time: "+milliSecondsTimer);
	}
}