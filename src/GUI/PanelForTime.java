package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.luan.LUANObject;

public class PanelForTime {
	private JPanel timePanel=null;
	private CommunicationProtocolSelectorForGui com = null;
	private JLabel timeLabel = null;
	private JButton timeButton = null;
	private ValidationDialogGUI val=null;
	public PanelForTime(CommunicationProtocolSelectorForGui com,ValidationDialogGUI val){
		this.com=com;
		this.val=val;
		createTimePanel();
		addTimeLabelToPanel();
		addTimeButtonToPanel();
	}
	
	private void createTimePanel(){
		this.timePanel = new JPanel();
		this.timePanel.setBorder(BorderFactory.createTitledBorder("Get Current GMT Time"));
		this.timePanel.setVisible(true);
		this.timePanel.setLayout(null);
	}
	
	private void addTimeLabelToPanel(){
		this.timeLabel = new JLabel();
		timeLabel.setText("20:23:48");
		Font font = new Font("Consolas", Font.BOLD, 15);
		timeLabel.setFont(font);
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		timeLabel.setBorder(BorderFactory.createTitledBorder("Current Time"));
		timePanel.add(timeLabel).setBounds(300, 12, 170, 40);
	}
	
	private void addTimeButtonToPanel(){
		this.timeButton= new JButton("Get Current GMT Time");
		timeButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	try{
	        		if(com.comIsRMI()==false){
		        		LUANObject luan = new LUANObject();
		        		luan.put("headCmd", "Time");
		        		com.sendLUANObect(luan);
	        		}else{
	        			setCurrentTime(com.getCurrentTime());
	        		}
	        	}catch(Exception ex){
	        		val.dynamicErrorDialogWindow("Error Manager - "+ex.toString(), ex.getMessage());
	        	 }
	         }
	    });
		timePanel.add(timeButton).setBounds(10, 20, 260, 30);
	}
	
	
	public void setCurrentTime(String time){
		timeLabel.setText(time);
	}
	
	public JPanel getTimePanel(){
		return this.timePanel;
	}
}
