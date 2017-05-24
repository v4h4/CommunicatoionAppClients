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

public class PanelForSumOfList {
	private JPanel sumPanel=null;
	private CommunicationProtocolSelectorForGui com = null;
	private JLabel sumLabel = null;
	private JButton sumButton = null;
	private JButton randomButton = null;
	private JComboBox<String> sumComboBox = null;
	private int[] currentComboBoxList=null;
	private ValidationDialogGUI val=null;
	public PanelForSumOfList(CommunicationProtocolSelectorForGui com,ValidationDialogGUI val){
		this.com=com;
		this.val=val;
		createMaxPanel();
		addSumLabelToPanel();
		addEqualLabel();
		addSumComboBoxToPanel();
		addSumButtonToPanel();
		addRandomButtonToPanel();
	}
	
	private void createMaxPanel(){
		this.sumPanel = new JPanel();
		this.sumPanel.setBorder(BorderFactory.createTitledBorder("Sum of List"));
		this.sumPanel.setVisible(true);
		this.sumPanel.setLayout(null);
	}
	
	private void addSumLabelToPanel(){
		this.sumLabel = new JLabel();
		Font font = new Font("Consolas", Font.BOLD, 15);
		sumLabel.setFont(font);
		sumLabel.setText("1345");
		sumLabel.setHorizontalAlignment(JLabel.CENTER);
		sumLabel.setBorder(BorderFactory.createTitledBorder("Sum"));
		sumPanel.add(sumLabel).setBounds(380, 25, 90, 50);
		
	}
	
	private void addEqualLabel(){
		JLabel equals = new JLabel();
		Font font = new Font("Consolas", Font.BOLD, 20);
		equals.setFont(font);
		equals.setText(" = ");
		sumPanel.add(equals).setBounds(352, 42, 60, 20);
		
	}
	
	private void addSumComboBoxToPanel(){
		this.sumComboBox = new JComboBox<String>(convertToStringArr(randomizeArray()));
		Font font = new Font("Consolas", Font.BOLD, 20);
		sumComboBox.setFont(font);
		sumPanel.add(sumComboBox).setBounds(290, 30, 65, 45);
		sumComboBox.updateUI();
		
	}
	
	private void addSumButtonToPanel(){
		this.sumButton= new JButton("Get Sum of the Integer List");
		this.sumButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	try{
	        		if(com.comIsRMI()==false){
	        			LUANObject luan = new LUANObject();
		        		luan.put("headCmd", "Sum of List");
		        		luan.put("Sum of List", currentComboBoxList);
		        		com.sendLUANObect(luan);
	        		}else{
	        			setSumIntegerOfList(com.getSumOfListRMI(currentComboBoxList));
	        		}
	        	}catch(Exception ex){
	        		val.dynamicErrorDialogWindow("Error Manager - "+ex.toString(), ex.getMessage());
	        	 }
	         }
	    });
		sumPanel.add(sumButton).setBounds(10, 20, 260, 30);
	}
	
	private void addRandomButtonToPanel(){
		this.randomButton = new JButton("Randomise Integer List");
		this.randomButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	try{
	        		System.out.println("Sum Random Button");
	        		removeAllItemsFromMaxComboBox();
	        		addItemsToSumComboBox(randomizeArray());
	        	}catch(Exception ex){
	        		 ex.printStackTrace();
	        	 }
	         }
	    });
		sumPanel.add(randomButton).setBounds(10, 55, 260, 30);
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
		int size=sumComboBox.getItemCount();
		for(int i=0;i<size;i++){
			sumComboBox.removeItemAt(size-1);
			size--;
		}
	}
	
	private void addItemsToSumComboBox(int[] intArr){
		String[] items=convertToStringArr(intArr);
		for(int i=0;i<items.length;i++){
			sumComboBox.addItem(items[i]);
		}
		sumComboBox.updateUI();
		sumPanel.updateUI();
	}
	
	public void setSumIntegerOfList(int sum){
		sumLabel.setText(""+sum);

	}
	
	public JPanel getSumPanel(){
		return this.sumPanel;
	}
}
