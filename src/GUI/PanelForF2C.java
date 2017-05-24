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

public class PanelForF2C {
	private JPanel f2cPanel=null;
	private JLabel f2cLabel = null;
	private JButton f2cButton = null;
	private JTextField f2cTextFiled=null;
	private CommunicationProtocolSelectorForGui com=null;
	private ValidationDialogGUI val=null;
	public PanelForF2C(CommunicationProtocolSelectorForGui com,ValidationDialogGUI val){
		this.com=com;
		this.val=val;
		create_c2fPanel();
		addC2fLabelToPanel();
		addC2fTextFieldToPanel();
		addC2fButtonToPanel();
	}
	
	private void create_c2fPanel(){
		this.f2cPanel = new JPanel();
		this.f2cPanel.setBorder(BorderFactory.createTitledBorder("Fahrenheit to Celsius Converter"));
		this.f2cPanel.setVisible(true);
		this.f2cPanel.setLayout(null);
	}
	
	private void addC2fLabelToPanel(){
		this.f2cLabel = new JLabel();
		f2cLabel.setText("0 °c");
		Font font = new Font("Consolas", Font.BOLD, 15);
		f2cLabel.setFont(font);
		f2cLabel.setHorizontalAlignment(JLabel.CENTER);
		f2cLabel.setBorder(BorderFactory.createTitledBorder("Temprature"));
		f2cPanel.add(f2cLabel).setBounds(380, 12, 90, 40);
		
	}
	
	private void addC2fTextFieldToPanel(){
		this.f2cTextFiled = new JTextField();
		f2cTextFiled.setText("0.0");
		f2cPanel.add(f2cTextFiled).setBounds(300, 23, 50, 25);
		
	}
	
	private void addC2fButtonToPanel(){
		this.f2cButton= new JButton("Convert to Celcius");
		f2cButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	try{
	        		if(com.comIsRMI()==false){
		        		LUANObject luan = new LUANObject();
		        		luan.put("headCmd", "f2c");
		        		luan.put("temperature", Double.parseDouble(f2cTextFiled.getText()));
		        		com.sendLUANObect(luan);
	        		}else{
	        			setTemperatureF2C(com.fahrenheitToCelsius(Double.parseDouble(f2cTextFiled.getText())));
	        		}
	        	}catch(Exception ex){
	        		 val.dynamicErrorDialogWindow("Input Error", "Your input is incorrect, please follow the instructions!");
	        	 }
	         }
	    });
		f2cPanel.add(f2cButton).setBounds(10, 20, 260, 30);
		
	}
	
	
	public void setTemperatureF2C(double temperature){
		DecimalFormat sec = new DecimalFormat("#.##");
		f2cLabel.setText(sec.format(temperature)+" °c");
	}
	
	public JPanel getC2fPanel(){
		return this.f2cPanel;
	}
}
