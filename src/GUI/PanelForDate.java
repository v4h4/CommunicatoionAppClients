package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.luan.LUANObject;

public class PanelForDate {
	private JPanel datePanel=null;
	private CommunicationProtocolSelectorForGui com = null;
	private JLabel dateLabel = null;
	private JButton DateButton = null;
	private ValidationDialogGUI val=null;
	public PanelForDate(CommunicationProtocolSelectorForGui com,ValidationDialogGUI val){
		this.com=com;
		this.val=val;
		createDatePanel();
		addDateLabelToPanel();
		addDateButtonToPanel();
	}
	
	private void createDatePanel(){
		this.datePanel = new JPanel();
		this.datePanel.setBorder(BorderFactory.createTitledBorder("Get Current GMT Date"));
		this.datePanel.setVisible(true);
		this.datePanel.setLayout(null);
	}
	
	private void addDateLabelToPanel(){
		this.dateLabel = new JLabel();
		dateLabel.setText("1912-04-10");
		Font font = new Font("Consolas", Font.BOLD, 15);
		dateLabel.setFont(font);
		dateLabel.setHorizontalAlignment(JLabel.CENTER);
		dateLabel.setBorder(BorderFactory.createTitledBorder("Current Date"));
		datePanel.add(dateLabel).setBounds(300, 12, 170, 40);
	}
	
	private void addDateButtonToPanel(){
		this.DateButton= new JButton("Get Current GMT Date");
		DateButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	try{
	        		if(com.comIsRMI()==false){
		        		LUANObject luan = new LUANObject();
		        		luan.put("headCmd", "Date");
		        		com.sendLUANObect(luan);
	        		}else{
	        			setCurrentDate(com.getCurrentDate());
	        		}
	        	}catch(Exception ex){
	        		val.dynamicErrorDialogWindow("Error Manager - "+ex.toString(), ex.getMessage());
	        	 }
	         }
	    });
		datePanel.add(DateButton).setBounds(10, 20, 260, 30);
	}
	
	
	public void setCurrentDate(String date){
		dateLabel.setText(date);
	}
	
	public JPanel getDatePanel(){
		return this.datePanel;
	}
}
