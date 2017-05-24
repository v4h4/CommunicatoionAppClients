package TCPNioClient;

/* java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.luan.*;

import GuiClientSelection.SelectClientGUI;
*/

public class StartTcpNioClientGUI implements Runnable{
	public void run(){
		
	}
	
	/*JFrame clientMainMenuTCP_NIO = new JFrame();
    private CommunicationProtocolTcpNIO com=null;
    private SelectClientGUI selectClient=null;
    private OperationManagerNIO op;
    private JLabel c2fLabel;
	private JLabel f2cLabel;
	private String [] fileComboBoxContent ={"This is Standard Array","exam notes.pdf", "Homeland - S02E08.avi", "Number Train - 1-20.mp4", "[MS-SRTP].pdf"};
	private String [] intMaxBox = {"76","42","56","74","2","5","7","8","9","5","4","32","3","34","6","33","2","5","6","32","87"};
	private String [] intSumBox = {"76","42","56","74","2","5","7","8","9","5","4","32","3","34","6","33","2","5","6","32","87"};
	private JComboBox<String> sumOfListBox;
    private JComboBox<String> maxOfListBox;
    private JLabel downloadingMillisecondsTimer = new JLabel();
    private Thread realTimeListener=null;
    JTextField sumField;
    JTextField maxField;
	JLabel timeLabel;
	JLabel dateLabel;

    public StartTcpNioClientGUI(String ipNbr, int port,SelectClientGUI selectClient){;
		this.selectClient=selectClient;
		this.com = new CommunicationProtocolTcpNIO(ipNbr,port);
		this.op = new OperationManagerNIO(this,com);
		this.realTimeListener = new Thread(op,"RealTimeLustener");
		this.realTimeListener.start();
    }
	
    public void run(){
    	startTcpNioClientGUI();
    }
	
	private void startTcpNioClientGUI(){
		mainMenuGui();
		gui_SumOfList();
		gui_maxOfList();
		gui_C2F_converter();
		gui_F2C_converter();
		guiDate();
		guiTime();
		guiDownloadFile();
		guiQuitClient();
	}
	
    private void mainMenuGui(){
		clientMainMenuTCP_NIO.setTitle("Multithreaded TCP Client with NEW I/O ");
		clientMainMenuTCP_NIO.setVisible(true);
		clientMainMenuTCP_NIO.setSize(515, 430);
		clientMainMenuTCP_NIO.setLayout(null);
		clientMainMenuTCP_NIO.setLocationRelativeTo(null);
		clientMainMenuTCP_NIO.setResizable(false);
		clientMainMenuTCP_NIO.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
    public void startWithgettingAllFilesFromBlobIntoFileComboBoxContent(String[] files){
 		fileComboBoxContent=files;
 	}
     
    
	private void gui_maxOfList(){
		JButton maxOfList_button = new JButton("Randomize Max List");
		JButton max_button = new JButton("Get max of the List of Integers");
		JLabel equalsMax = new JLabel(" = ");
		maxOfListBox = new JComboBox<String>(intMaxBox);
		maxField = new JTextField();
		maxOfListBox.setEditable(false);
		maxField.setEditable(false);
		maxField.setText("123456");
		clientMainMenuTCP_NIO.add(maxOfList_button).setBounds(10, 85, 250, 30);
		clientMainMenuTCP_NIO.add(max_button).setBounds(10, 125, 250, 30);
		clientMainMenuTCP_NIO.add(maxField).setBounds(300, 105, 80, 30);
		clientMainMenuTCP_NIO.add(equalsMax).setBounds(390, 105, 20, 30);
		clientMainMenuTCP_NIO.add(maxOfListBox).setBounds(420, 105, 80, 30);
		
		maxOfList_button.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	clientMainMenuTCP_NIO.remove(maxOfListBox);
	        	intMaxBox= randomizeArray();
	        	maxOfListBox = new JComboBox<String>(intMaxBox);
	     		clientMainMenuTCP_NIO.add(maxOfListBox).setBounds(420, 105, 80, 30);
	        }
	    });
		
		max_button.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 try{
	        		 LUANObject luan = new LUANObject();
		        	 luan.putString("headCmd", "Maximum of List");
		        	 luan.putStringArray("intMaxBox",intMaxBox);
		        	 com.sendLUANObject(luan);
		        	// task.reciviedMessageHandler(com.receiveLUANObject());
	        	 }catch(Exception ex){
	        		 
	        	 }
	        }
	    });	
	}
	
	private void gui_SumOfList(){
		JButton sumOfList_button = new JButton("Randomize Sum List");
		JButton sum_button = new JButton("Get sum of the List of Integers");
		
		JLabel equalsSum = new JLabel(" = ");
		sumOfListBox = new JComboBox<String>(intSumBox);
		sumField = new JTextField();
		sumOfListBox.setEditable(false);
		sumField.setEditable(false);
		sumField.setText("123456");
		clientMainMenuTCP_NIO.add(sumOfList_button).setBounds(10, 5, 250, 30);
		clientMainMenuTCP_NIO.add(sum_button).setBounds(10, 45, 250, 30);
		clientMainMenuTCP_NIO.add(sumField).setBounds(300, 28, 80, 30);
		clientMainMenuTCP_NIO.add(equalsSum).setBounds(390, 28, 20, 30);
		clientMainMenuTCP_NIO.add(sumOfListBox).setBounds(420, 28, 80, 30);
		sumOfList_button.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 clientMainMenuTCP_NIO.remove(sumOfListBox);
	        	 intSumBox= randomizeArray();
	        	 sumOfListBox = new JComboBox<String>(intSumBox);
	        	 clientMainMenuTCP_NIO.add(sumOfListBox).setBounds(420, 28, 80, 30);
	         }
	    });
		
		sum_button.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 try{
	        		 LUANObject luan = new LUANObject();
		        	 luan.putString("headCmd","Sum of List");
		        	 luan.putStringArray("intSumBox",intSumBox);
		        	 com.sendLUANObject(luan);
		        	// task.reciviedMessageHandler(com.receiveLUANObject());
	        	 }catch(Exception ex){
	        		 ex.printStackTrace();
	        	 }
	         }
	    });	
	}
	
	private void gui_C2F_converter(){
		final JTextField c2fField = new JTextField();
		c2fLabel = new JLabel();
		c2fLabel.setText("0 °f");
		JButton convert_button_c2f = new JButton("Convert from Celsius to Fahrenheit");
		clientMainMenuTCP_NIO.add(convert_button_c2f).setBounds(10, 165, 290, 30);
		clientMainMenuTCP_NIO.add(c2fField).setBounds(310, 165, 50, 30);
		clientMainMenuTCP_NIO.add(c2fLabel).setBounds(370, 165, 180, 30);
		convert_button_c2f.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	LUANObject luan = new LUANObject();
	        	try{
	        		 luan.putString("headCmd", "C2F");
	        		 luan.putDouble("temperature", Double.parseDouble(c2fField.getText()));
		        	 com.sendLUANObject(luan);
	        		// task.reciviedMessageHandler(com.receiveLUANObject());
	        	}catch(Exception ex){
	        		 ex.printStackTrace();
	        	 }
	         }
	    });
	}
	
	private void gui_F2C_converter(){
		
		final JTextField f2cField = new JTextField();
		f2cLabel = new JLabel();
		f2cLabel.setText("0 °c");
		JButton convert_button_f2c = new JButton("Convert from Fahrenheit to Celsius");
		clientMainMenuTCP_NIO.add(convert_button_f2c).setBounds(10, 205, 290, 30);
		clientMainMenuTCP_NIO.add(f2cField).setBounds(310, 205, 50, 30);
		clientMainMenuTCP_NIO.add(f2cLabel).setBounds(370, 205, 180, 30);
		convert_button_f2c.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 LUANObject luan = new LUANObject();
	        	 try{
	        		 luan.putString("headCmd", "F2C");
	        		 luan.putDouble("temperature", Double.parseDouble(f2cField.getText()));
	        		 com.sendLUANObject(luan);
	        		// task.reciviedMessageHandler(com.receiveLUANObject());
	        	 }catch(Exception ex){
	        		 ex.printStackTrace();
	        	 }
	         }
	    });
	}
	
	private void guiDate(){
		dateLabel = new JLabel();
		dateLabel.setText(" Date ? ");
		JButton getDate_button = new JButton("Get Current Date");
		clientMainMenuTCP_NIO.add(getDate_button).setBounds(10, 245, 290, 30);
		clientMainMenuTCP_NIO.add(dateLabel).setBounds(310, 245, 100, 30);
		getDate_button.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 LUANObject luan = new LUANObject();
	        	 try{
	        		 luan.putString("headCmd", "Date");
		        	 com.sendLUANObject(luan);
		        //	 task.reciviedMessageHandler(com.receiveLUANObject());
	        	 }catch(Exception ex){
	        		 ex.printStackTrace();
	        	 }
	         }
	    });
	}
	
	private void guiTime(){
		timeLabel = new JLabel();
		timeLabel.setText(" Time ? ");
		JButton getTime_button = new JButton("Get Current Time");
		clientMainMenuTCP_NIO.add(getTime_button).setBounds(10, 285, 290, 30);
		clientMainMenuTCP_NIO.add(timeLabel).setBounds(310, 285, 100, 30);
		getTime_button.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 LUANObject luan = new LUANObject();
	        	 try{
	        		 luan.putString("headCmd", "Time");
	        		 com.sendLUANObject(luan);
		        	// task.reciviedMessageHandler(com.receiveLUANObject());
	        	 }catch(Exception ex){
	        		 ex.printStackTrace();
	        	 }
	        }
	    });
	}
	
	private void guiDownloadFile(){
		JButton downloadFile_button = new JButton("Download selected file");
		final JComboBox<String> fileSelectionBox = new JComboBox<String>(fileComboBoxContent);
		clientMainMenuTCP_NIO.add(downloadFile_button).setBounds(10, 325, 250, 30);
		clientMainMenuTCP_NIO.add(fileSelectionBox).setBounds(280, 325, 200, 30);
		downloadingMillisecondsTimer.setText("MS downloading timer: 0");
		clientMainMenuTCP_NIO.add(downloadingMillisecondsTimer).setBounds(280, 365, 200, 30);
		downloadFile_button.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	try{
	        		LUANObject luan = new LUANObject();
	        		luan.putString("headCmd", "Download File");
	     			luan.putString("fileName", fileSelectionBox.getSelectedItem().toString());
	     			com.sendLUANObject(luan);
	     			
	        	}catch(Exception ex){
	        		ex.printStackTrace();
	        	}
	       }
	    });
	}
	
	private void guiQuitClient(){
		JButton quitClient = new JButton("Quit TCP Server with I/O");
		clientMainMenuTCP_NIO.add(quitClient).setBounds(10, 365, 250, 30);
		quitClient.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	// closeConnection();
	        	 selectClient.clientMainMenu.setVisible(true);
		         clientMainMenuTCP_NIO.dispose();
	         }
	    });
	}
	
	public void setC2F_Label(String fahrenheit){
		c2fLabel.setText(fahrenheit+" °f");
	}
	
	public void setF2C_Label(String celcius){
		f2cLabel.setText(celcius+" °c");
	}
	
	private String[] randomizeArray(){
		Random rand = new Random();
		String [] randomIntArray = new String[rand.nextInt(100)+25];
		for(int i=0;i<randomIntArray.length;i++){
			randomIntArray[i]=String.valueOf(rand.nextInt(101));
		}
		return randomIntArray;
	}

	
		
	public void updateTimerGUI(long milliSecondsTimer){
		downloadingMillisecondsTimer.setText("MS downloading timer: "+milliSecondsTimer);
	}*/
}
