package ClientSelectionGUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GUI.ValidationDialogGUI;
import TCPClientIO.ClientTcpIO;

public class DetailPanelForTcpIO {
	private JPanel panelTcpIO = null;
	private JButton startServer = null;
	private JButton backToMenu = null;
	private ValidationDialogGUI val=null;
	private JTextField portTextFiled=null;
	private JTextField ipTextFiled=null;
	private MainFrameClientSelection gui;
	public DetailPanelForTcpIO(ValidationDialogGUI val,MainFrameClientSelection gui){
		this.val=val;
		this.gui=gui;
		createTcpIOPanel();
		addIpLabelToPanel();
		addPortLabelToPanel();
		addIpTextFieldToPanel();
		addPortTextFieldToPanel();
		addBackToMenuButtonToPanel();
		addStartServerButtonToPanel();
	}
	
	private void createTcpIOPanel(){
		this.panelTcpIO = new JPanel();
		this.panelTcpIO.setBorder(BorderFactory.createTitledBorder("Initilaztion of TCP I/O Client"));
		this.panelTcpIO.setVisible(false);
		this.panelTcpIO.setLayout(null);
	}
	
	private void addIpLabelToPanel(){
		JLabel ipLabel = new JLabel();
		ipLabel.setText(" Enter IP:");
		Font font = new Font("Consolas", Font.BOLD, 15);
		ipLabel.setFont(font);
		ipLabel.setHorizontalAlignment(JLabel.LEFT);
		ipLabel.setBorder(BorderFactory.createTitledBorder("Enter IP number"));
		panelTcpIO.add(ipLabel).setBounds(10, 15, 280, 60);
	}
	
	private void addPortLabelToPanel(){
		JLabel portLabel = new JLabel();
		portLabel.setText(" Enter Port:");
		Font font = new Font("Consolas", Font.BOLD, 15);
		portLabel.setFont(font);
		portLabel.setHorizontalAlignment(JLabel.LEFT);
		portLabel.setBorder(BorderFactory.createTitledBorder("Enter Port number"));
		panelTcpIO.add(portLabel).setBounds(10, 75, 280, 60);
	}
	
	private void addIpTextFieldToPanel(){
		ipTextFiled = new JTextField();
		panelTcpIO.add(ipTextFiled).setBounds(100, 35, 182, 30);
	}
	
	private void addPortTextFieldToPanel(){
		this.portTextFiled = new JTextField();
		panelTcpIO.add(portTextFiled).setBounds(120, 95, 162, 30);
	}
	
	private void addStartServerButtonToPanel(){
		this.startServer= new JButton("Start Client");
		startServer.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	try{
	        		int port=Integer.parseInt(portTextFiled.getText());
	        		if(val.textComponetIsIpNumber(ipTextFiled.getText())!=true){
	        			val.dynamicErrorDialogWindow("ERRROR - Wrong IP Structure", "Ip number is not correct, correct IP is from 0.0.0.0 to 255.255.255.255");
	        		}else{
	        			ClientTcpIO tcpIO = new ClientTcpIO(gui, ipTextFiled.getText(), port);
	        			new Thread(tcpIO,"TCP I/O Connection Listener").start();
		        		hideDetailPanelForTcpIO();
		        		gui.showServerSelectionPanel();
		        		gui.hideMainFrame();
	        		}
	        	}catch(Exception ex){
	        		val.dynamicErrorDialogWindow("ERRROR - Wrong port Structure", "Port number must consit of 1 to 5 digit number");
	        	 }
	         }
	    });
		panelTcpIO.add(startServer).setBounds(10, 140, 135, 30);
	}
	
	private void addBackToMenuButtonToPanel(){
		this.backToMenu= new JButton("Back To Menu");
		backToMenu.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	try{
	        		hideDetailPanelForTcpIO();
	        		gui.showServerSelectionPanel();
	        	}catch(Exception ex){
	        		val.dynamicErrorDialogWindow("Error Manager - "+ex.toString(), ex.getMessage());
	        	 }
	         }
	    });
		panelTcpIO.add(backToMenu).setBounds(155, 140, 135, 30);
	}
	
	public void showDetailPanelForTcpIO(){
		this.panelTcpIO.setVisible(true);
		ipTextFiled.setText("");
		portTextFiled.setText("");
	}
	
	public void hideDetailPanelForTcpIO(){
		this.panelTcpIO.setVisible(false);
	}
	
	public JPanel getJPanel(){
		return this.panelTcpIO;
	}
}
