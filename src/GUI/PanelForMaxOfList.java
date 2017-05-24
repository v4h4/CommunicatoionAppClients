package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.luan.LUANObject;

public class PanelForMaxOfList {
	private JPanel maxPanel=null;
	private CommunicationProtocolSelectorForGui com = null;
	private JLabel maxLabel = null;
	private JButton maxButton = null;
	private JButton randomButton = null;
	private JComboBox<String> maxComboBox = null;
	private int[] currentComboBoxList=null;
	private ValidationDialogGUI val=null;
	public PanelForMaxOfList(CommunicationProtocolSelectorForGui com,ValidationDialogGUI val){
		this.com=com;
		this.val=val;
		createMaxPanel();
		addMaxLabelToPanel();
		addEqualLabel();
		addMaxComboBoxToPanel();
		addMaxButtonToPanel();
		addRandomButtonToPanel();
	}
	
	private void createMaxPanel(){	
		this.maxPanel = new JPanel();
		this.maxPanel.setBorder(BorderFactory.createTitledBorder("Maxium of List"));
		this.maxPanel.setVisible(true);
		this.maxPanel.setLayout(null);
	}
	
	private void addMaxLabelToPanel(){
		this.maxLabel = new JLabel();
		Font font = new Font("Consolas", Font.BOLD, 15);
		maxLabel.setFont(font);
		maxLabel.setText("100");
		maxLabel.setHorizontalAlignment(JLabel.CENTER);
		maxLabel.setBorder(BorderFactory.createTitledBorder("Maximum"));
		maxPanel.add(maxLabel).setBounds(380, 25, 90, 50);
		
	}
	
	private void addEqualLabel(){
		JLabel equals = new JLabel();
		Font font = new Font("Consolas", Font.BOLD, 20);
		equals.setFont(font);
		equals.setText(" = ");
		maxPanel.add(equals).setBounds(352, 42, 60, 20);
		
	}
	
	private void addMaxComboBoxToPanel(){
		this.maxComboBox = new JComboBox<String>(convertToStringArr(randomizeArray()));
		Font font = new Font("Consolas", Font.BOLD, 20);
		maxComboBox.setFont(font);
		maxPanel.add(maxComboBox).setBounds(290, 30, 65, 45);
	//	maxComboBox.updateUI();
	}
	
	private void addMaxButtonToPanel(){
		this.maxButton= new JButton("Get Maximum Integer of the Integer List");
		this.maxButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	try{
	        		if(com.comIsRMI()==false){
		        		LUANObject luan = new LUANObject();
		        		luan.put("headCmd", "Max of List");
		        		luan.put("Max of List", currentComboBoxList);
		        		com.sendLUANObect(luan);
	        		}else{
	        			setMaximumIntegerOfList(com.getMaxOfListRMI(currentComboBoxList));
	        		}
	        	}catch(Exception ex){
	        		 val.dynamicErrorDialogWindow("Error Manager - "+ex.toString(), ex.getMessage()); 
	        	 }
	         }
	    });
		maxPanel.add(maxButton).setBounds(10, 20, 260, 30);
	}
	
	private void addRandomButtonToPanel(){
		this.randomButton = new JButton("Randomise Integer List");
		this.randomButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	try{
	        		System.out.println("Max Random Button");
	        		removeAllItemsFromMaxComboBox();
	        		addItemsToMaxComboBox(randomizeArray());
	        	}catch(Exception ex){
	        		 ex.printStackTrace();
	        	 }
	         }
	    });
		maxPanel.add(randomButton).setBounds(10, 55, 260, 30);
	}
	
	private int[] randomizeArray(){
		Random rand = new Random();
		int [] randomIntArray = new int[rand.nextInt(100)+25];
		for(int i=0;i<randomIntArray.length;i++){
			randomIntArray[i]=rand.nextInt(101);
		}
		this.currentComboBoxList=randomIntArray;
		return randomIntArray;
	}
	
	private String[] convertToStringArr(int[] intArr){
		String [] randomIntArray = new String[intArr.length];
		for(int i=0;i<randomIntArray.length;i++){
			randomIntArray[i]=String.valueOf(intArr[i]);
		}
		return randomIntArray;
	}
	
	private void removeAllItemsFromMaxComboBox(){
		int size=maxComboBox.getItemCount();
		for(int i=0;i<size;i++){
			maxComboBox.removeItemAt(size-1);
			size--;
		}
	}
	
	private void addItemsToMaxComboBox(int[] intArr){
		String[] items=convertToStringArr(intArr);
		for(int i=0;i<items.length;i++){
			maxComboBox.addItem(items[i]);
		}
		//maxComboBox.updateUI();
		//maxPanel.updateUI();
	}
	
	public void setMaximumIntegerOfList(int maxInteger){
		maxLabel.setText(maxInteger+"");
	}
	
	public JPanel getMaxPanel(){
		return this.maxPanel;
	}
}
