package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ClientSelectionGUI.MainFrameClientSelection;

public class PanelForQuitingClientGUI {
	private JPanel quitPanel=null;
	private JButton quitButton = null;
	private String title="+ ......title..... +";
	private MainFrameClientSelection mainFrameServerSelection=null;
	private JFrame mainFrame=null;
	private CommunicationProtocolSelectorForGui com=null;
	public PanelForQuitingClientGUI(MainFrameClientSelection mainFrameServerSelection,JFrame mainFrame,CommunicationProtocolSelectorForGui com,String title){
		this.mainFrameServerSelection=mainFrameServerSelection;
		this.com=com;
		this.mainFrame=mainFrame;
		this.title=title;
		createQuitPanel();
		addQuitButtonToPanel();
		
	}
	
	private void createQuitPanel(){
		this.quitPanel = new JPanel();
		this.quitPanel.setBorder(BorderFactory.createTitledBorder("Get Current GMT Date"));
		this.quitPanel.setVisible(true);
		this.quitPanel.setLayout(null);
	}
	
	private void addQuitButtonToPanel(){
		this.quitButton= new JButton("Quit "+title+" Client");
		quitButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	try{
	        		com.closeConnection();
	        		mainFrameServerSelection.showMainFrame();
					mainFrame.dispose();
	        	}catch(Exception ex){
	        		 ex.printStackTrace();
	        	 }
	         }
	    });
		quitPanel.add(quitButton).setBounds(10, 20, 460, 30);
	}

	public JPanel getQuitPanel(){
		return this.quitPanel;
	}
}
