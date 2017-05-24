package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.luan.LUANObject;

public class PanelForDownload {
	private JPanel downloadPanel=null;
	private CommunicationProtocolSelectorForGui com = null;
	private JLabel downloadLabel = null;
	private JButton downloadButton = null;
	private JComboBox<String> downloadComboBox = null;
	private ValidationDialogGUI val=null;
	public PanelForDownload(CommunicationProtocolSelectorForGui com,ValidationDialogGUI val){
		this.com=com;
		this.val=val;
		createDatePanel();
		addDownloadLabelToPanel();
		addDownloadButtonToPanel();
		addDownloadComboBoxToPanel();
		com.setDownloadPanel(this);
	}
	
	private void createDatePanel(){
		this.downloadPanel = new JPanel();
		this.downloadPanel.setBorder(BorderFactory.createTitledBorder("File Downloading"));
		this.downloadPanel.setVisible(true);
		this.downloadPanel.setLayout(null);
	}
	
	private void addDownloadLabelToPanel(){
		this.downloadLabel = new JLabel();
		downloadLabel.setText("0.0");
		Font font = new Font("Consolas", Font.BOLD, 15);
		downloadLabel.setFont(font);
		downloadLabel.setHorizontalAlignment(JLabel.CENTER);
		downloadLabel.setBorder(BorderFactory.createTitledBorder("Downloading Time"));
		downloadPanel.add(downloadLabel).setBounds(300, 25, 170, 50);
	}
	
	private void addDownloadButtonToPanel(){
		this.downloadButton= new JButton("Download Selected file");
		downloadButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	try{
	        		if(com.comIsRMI()==false){
	        			LUANObject luan = new LUANObject();
		        		luan.put("headCmd", "File Download");
		        		luan.put("fileName", downloadComboBox.getSelectedItem().toString());
		        		com.sendLUANObect(luan);
	        		}else{
	        			com.downloadFile(downloadComboBox.getSelectedItem().toString());
	        		}
	        		
	        	}catch(Exception ex){
	        		val.dynamicErrorDialogWindow("Error Manager - "+ex.toString(), ex.getMessage());
	        	 }
	         }
	    });
		downloadPanel.add(downloadButton).setBounds(10, 20, 260, 30);
	}
	
	private void addDownloadComboBoxToPanel(){
		//String[] blob ={"plane.jpg","Number Train - 1-20.mp4","Homeland - S02E08.avi","exam notes.pdf","[MS-SRTP].pdf"};
		this.downloadComboBox = new JComboBox<String>();
		Font font = new Font("Consolas", Font.BOLD, 20);
		downloadComboBox.setFont(font);
		downloadPanel.add(downloadComboBox).setBounds(10, 55, 260, 30);
		downloadComboBox.updateUI();
	}
	
	private void removeAllItemsFromDownladComboBox(){
		int size=downloadComboBox.getItemCount();
		for(int i=size-1;i>0;i--){
			downloadComboBox.removeItemAt(i);
		}
	}
	
	private void addItemsToDownloadComboBox(String[] files){
		System.out.println("Avalibl Files are:");
		for(int i=0;i<files.length;i++){
			System.out.println("files["+i+"] == "+files[i]);
		}
		for(int i=0;i<files.length;i++){
			downloadComboBox.addItem(files[i]);
		}
	}
	
	public void setDownloadingTime(long timer){
		downloadLabel.setText(timer+"");
	}
	
	public JPanel getDownloadPanel(){
		return this.downloadPanel;
	}
	
	public void updateDownloadProcentageProcess(int procentage){
		downloadLabel.setBorder(BorderFactory.createTitledBorder("Downloading Time:   "+procentage+" %"));
	}
	
	public void clearDownloadProcentageProcess(){
		downloadLabel.setBorder(BorderFactory.createTitledBorder("Downloading Time"));
	}
	
	public void setStartUpAlvalibleBlobFiles(String[] files){
		removeAllItemsFromDownladComboBox();
		addItemsToDownloadComboBox(files);
	}
}
