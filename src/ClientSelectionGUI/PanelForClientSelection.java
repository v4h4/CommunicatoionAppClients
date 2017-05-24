package ClientSelectionGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelForClientSelection {
	private JPanel panelForServerSelection = null;
	private DetailPanelForRMI rmi =null;
	private DetailPanelForUDP udp=null;
	private DetailPanelForTcpIO tcpIO=null;
	private DetailPanelForTcpNIO tcpNIO=null;
	public PanelForClientSelection(DetailPanelForRMI rmi,DetailPanelForUDP udp,DetailPanelForTcpIO tcpIO,DetailPanelForTcpNIO tcpNIO){
		this.rmi=rmi;
		this.udp=udp;
		this.tcpIO=tcpIO;
		this.tcpNIO=tcpNIO;
		createServerSelectionPanel();
		udpIOServerButton();
		tcpIOServerButton();
		tcpNIOServerButton();
		rmiServerButton();
	}
	private void createServerSelectionPanel(){
		this.panelForServerSelection = new JPanel();
		this.panelForServerSelection.setBorder(BorderFactory.createTitledBorder("Client Selection"));
		this.panelForServerSelection.setVisible(true);
		this.panelForServerSelection.setLayout(null);
	}
	
	private void udpIOServerButton() {
		JButton udpServerIO = new JButton("Start UDP Client with I/O");
		udpServerIO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelForServerSelection.setVisible(false);
				udp.showDetailPanelForUDP();
				//selectIpAndPort.ipAndPortGUI("Start UDP Server with I/O");
			}
		});
		panelForServerSelection.add(udpServerIO).setBounds(10, 20, 280, 30);
	}

	private void tcpNIOServerButton() {
		JButton tcpServerIO = new JButton("Start TCP Client with I/O");
		tcpServerIO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelForServerSelection.setVisible(false);
				tcpIO.showDetailPanelForTcpIO();
			}
		});
		panelForServerSelection.add(tcpServerIO).setBounds(10, 60, 280, 30);
	}

	private void tcpIOServerButton() {
		JButton tcpServerNIO = new JButton("Start TCP Client with NEW I/O");
		tcpServerNIO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelForServerSelection.setVisible(false);
				tcpNIO.showDetailPanelForTcpNIO();
				//selectIpAndPort.ipAndPortGUI("Start TCP Server with NEW I/O");
			}
		});
		panelForServerSelection.add(tcpServerNIO).setBounds(10, 100, 280, 30);
	}

	private void rmiServerButton() {
		JButton rmiServer = new JButton("Start RMI Client");
		rmiServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelForServerSelection.setVisible(false);
				rmi.showDetailPanelForRMI();
				//selectIpAndPort.ipAndPortGUI("Start RMI Server");
			}
		});	
		panelForServerSelection.add(rmiServer).setBounds(10, 140, 280, 30);
	}
	
	public JPanel getJPanel(){
		return this.panelForServerSelection;
	}
	
}
