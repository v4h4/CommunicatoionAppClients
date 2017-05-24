package GUI;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.luan.LUANObject;

import ClientSelectionGUI.MainFrameClientSelection;

public class MainFrameGUI {
	private JFrame mainFrame = null;
	private PanelForC2F panelC2F= null;
	private PanelForF2C panelF2C= null;
	private PanelForMaxOfList panelMax = null;
	private PanelForSumOfList panelSum = null;
	private PanelForDate panelDate = null;
	private PanelForTime panelTime = null;
	private PanelForDownload panelDownload =null;
	private PanelForQuitingClientGUI panelQuit=null;
	private ValidationDialogGUI val=null;
	private MainFrameClientSelection mainFrameServerSelection=null;
	private CommunicationProtocolSelectorForGui com=null;
	
	
	/*
	 //DONI LOOK HERE!!!! 
	  
	//If you uncomment this, and press this class and then the run button(looks like play)
	//this GUI class will only start and the TCP connection will not work!!
	public static void main(String[] args){
	 
		MainFrameGUI main = new MainFrameGUI(null);
	}
	*/
	
	public MainFrameGUI(MainFrameClientSelection mainFrameServerSelection,CommunicationProtocolSelectorForGui com,String title){
		this.mainFrameServerSelection=mainFrameServerSelection;
		this.com=com;
		startClientWindowGUI(title);
		constructorProtcol(title);	
		addPanelsToFrame();
		startupUpdateOfAvalibleFiles();
	}
	
	public void startClientWindowGUI(String title){
		try{
			this.mainFrame = new JFrame();
			mainFrame.setTitle(title);
			mainFrame.setVisible(true);
			mainFrame.setSize(505, 690);
			mainFrame.setLayout(null);
			mainFrame.setLocationRelativeTo(null);
			mainFrame.setResizable(false);
			mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			mainFrame.setLocation(800, 10);
			mainFrameCloseListener();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void constructorProtcol(String title){
		try{
			this.val=new ValidationDialogGUI(mainFrame);
			this.panelC2F= new PanelForC2F(com,val);
			this.panelF2C = new PanelForF2C(com,val);
			this.panelMax = new PanelForMaxOfList(com,val);
			this.panelSum = new PanelForSumOfList(com,val);
			this.panelDate = new PanelForDate(com,val);
			this.panelTime = new PanelForTime(com,val);
			this.panelDownload = new PanelForDownload(com,val);
			this.panelQuit = new PanelForQuitingClientGUI(mainFrameServerSelection,mainFrame,com,title);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void addPanelsToFrame(){
		try{
			mainFrame.add(panelC2F.getC2fPanel()).setBounds(10, 5, 480, 65);
			mainFrame.add(panelF2C.getC2fPanel()).setBounds(10, 70, 480, 65);
			mainFrame.add(panelMax.getMaxPanel()).setBounds(10, 136, 480, 100);
			mainFrame.add(panelSum.getSumPanel()).setBounds(10, 239, 480, 100);
			mainFrame.add(panelDate.getDatePanel()).setBounds(10, 344, 480, 65);
			mainFrame.add(panelTime.getTimePanel()).setBounds(10, 414, 480, 65);
			mainFrame.add(panelDownload.getDownloadPanel()).setBounds(10, 484, 480, 100);
			mainFrame.add(panelQuit.getQuitPanel()).setBounds(10, 588, 480, 65);	
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void mainFrameCloseListener(){
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				boolean close=val.dynamicConfirmationDialog("Closing down Admin Bank Chatt", "Are you sure that you want to close this Admin Bank Chatt?");
				if(close == true){
					mainFrame.dispose();
					mainFrameServerSelection.showMainFrame();
				}	
			}
		});
	}
	
	private void startupUpdateOfAvalibleFiles(){
		try{
			if(com.comIsRMI()==true){
				setStartUpAlvalibleBlobFiles(com.getAllAvalibleFilesFromRmiServerBlob());
			}else{
				LUANObject luan = new LUANObject();
				luan.put("headCmd", "Start-up Avalible Files");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void enableFrame(boolean toggle){
		mainFrame.setEnabled(toggle);
	}
	
	public void setTemperatureC2F(double temperature){
		panelC2F.setTemperatureC2F(temperature);
	}
	
	public void setTemperatureF2C(double temperature){
		panelF2C.setTemperatureF2C(temperature);
	}

	public void setSumOfList(int sum){
		panelSum.setSumIntegerOfList(sum);
	}
	
	public void setMaximumIntegerOfList(int max){
		panelMax.setMaximumIntegerOfList(max);
	}
	
	public void setCurrentDate(String date){
		panelDate.setCurrentDate(date);
	}
	
	public void setCurrentTime(String time){
		panelTime.setCurrentTime(time);
	}
	
	public void clearDownloadProcentageProcess(){
		panelDownload.clearDownloadProcentageProcess();
	}
	
	public void updateDownloadProcentageProcess(int procentage){
		panelDownload.updateDownloadProcentageProcess(procentage);
	}
	
	public void setDownloadingTime(long timer){
		panelDownload.setDownloadingTime(timer);
	}
	
	public void setStartUpAlvalibleBlobFiles(String[] files){
		System.out.println("String[] files == "+files);
		panelDownload.setStartUpAlvalibleBlobFiles(files);
	}
}
