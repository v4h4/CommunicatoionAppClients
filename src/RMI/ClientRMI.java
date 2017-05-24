package RMI;
import java.rmi.Naming;

import ClientSelectionGUI.MainFrameClientSelection;
import GUI.CommunicationProtocolSelectorForGui;
import GUI.MainFrameGUI;

public class ClientRMI/* implements Runnable*/{ 
	private RmiInterface rmi=null;
	
	public ClientRMI(MainFrameClientSelection mainFrameServerSelection,String ipNbr){
		 try{
			 this.rmi = (RmiInterface)Naming.lookup("//"+ipNbr+"/RMILab1_Services");
			 new MainFrameGUI(mainFrameServerSelection,new CommunicationProtocolSelectorForGui(rmi),"RMI - Client");
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }		 
	 }
}  