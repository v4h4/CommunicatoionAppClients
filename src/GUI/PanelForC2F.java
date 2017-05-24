package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.luan.LUANObject;
public class PanelForC2F {
	private JPanel c2fPanel=null;
	private JLabel c2fLabel = null;
	private JButton c2fButton = null;
	private JTextField c2fTextFiled=null;
	private ValidationDialogGUI val=null;
	private CommunicationProtocolSelectorForGui com=null;
	public PanelForC2F(CommunicationProtocolSelectorForGui com,ValidationDialogGUI val){
		this.com=com;
		this.val=val;
		create_c2fPanel();
		addC2fLabelToPanel();
		addC2fTextFieldToPanel();
		addC2fButtonToPanel();
	}
	
	private void create_c2fPanel(){
		this.c2fPanel = new JPanel();
		//Border border = BorderFactory.createLineBorder(Color.DARK_GRAY)
		this.c2fPanel.setBorder(BorderFactory.createTitledBorder("Celsius to Fahrenheit Converter"));
		this.c2fPanel.setVisible(true);
		this.c2fPanel.setLayout(null);
	}
	
	private void addC2fLabelToPanel(){
		this.c2fLabel = new JLabel();
		c2fLabel.setText("0 °f");
		Font font = new Font("Consolas", Font.BOLD, 15);
		c2fLabel.setFont(font);
		c2fLabel.setHorizontalAlignment(JLabel.CENTER);
		c2fLabel.setBorder(BorderFactory.createTitledBorder("Temprature"));
		c2fPanel.add(c2fLabel).setBounds(380, 12, 90, 40);
	}
	
	private void addC2fTextFieldToPanel(){
		this.c2fTextFiled = new JTextField();
		c2fTextFiled.setText("0.0");
		c2fPanel.add(c2fTextFiled).setBounds(300, 23, 50, 25);
	}
	
	private void addC2fButtonToPanel(){
		this.c2fButton= new JButton("Convert to Fahrenheit");
		c2fButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	try{
	        		if(com.comIsRMI()==false){
		        		LUANObject luan = new LUANObject();
		        		luan.put("headCmd", "c2f");
		        		luan.put("temperature", Double.parseDouble(c2fTextFiled.getText()));
		        		com.sendLUANObect(luan);
	        		}else{
	        			setTemperatureC2F(com.celsiusToFahrenheit(Double.parseDouble(c2fTextFiled.getText())));
	        		}
	        	}catch(Exception ex){
	        		 val.dynamicErrorDialogWindow("Input Error", "Your input is incorrect, please follow the instructions!");
	        	 }
	         }
	    });
		c2fPanel.add(c2fButton).setBounds(10, 20, 260, 30);
		
	}
	
	
	public void setTemperatureC2F(double temperature){
		DecimalFormat sec = new DecimalFormat("#.##");
		c2fLabel.setText(sec.format(temperature)+" °f");
	}
	
	public JPanel getC2fPanel(){
		return this.c2fPanel;
	}
}
