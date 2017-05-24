package TCPNioClient;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import org.luan.LUANObject;

public class FileDownloadingProtocol implements Runnable{
	private static ArrayList <byte[]> byteArrList = new ArrayList <byte[]>();
	private static LUANObject startLuan=null;
	private static LUANObject endLuan=null;
	public void run(){
		while(true){
			try{
				rutineProtocol();
				Thread.sleep(200);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	
	private void rutineProtocol(){
		try {
			if(endLuan!=null && startLuan!=null){
				byte[] original=getOriginalByteArrayFromSegentedByteArrays(byteArrList,startLuan.getInteger("originalLength"));
				saveDialog(original, startLuan);
			    byteArrList = new ArrayList<byte[]>();
			    startLuan=null;
			    endLuan=null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void saveDialog(byte[] byteFile,LUANObject luan){
		try{
			System.out.println("\n\n\n\nfileName == "+luan.getString("fileName"));
			System.out.println("fileType == "+luan.getString("fileType")+"\n\n\n\n");
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Save the file: "+luan.getString("fileName"));
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			
				File file = fileChooser.getSelectedFile();
				String fileName=fileChooser.getCurrentDirectory()+"\\"+fileChooser.getName(file)+luan.getString("fileType");
				FileOutputStream fos = new FileOutputStream(fileName);
			    fos.write(byteFile);
			    fos.close();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private byte[] getOriginalByteArrayFromSegentedByteArrays(ArrayList <byte[]> byteList,int originalSize){
		byte[] newOriginalByteArray=null;
		try{
			newOriginalByteArray = new byte[originalSize];
			byte[] currentByteArray=null;
			int x=0;
			//int count=newOriginalByteArray.length;
			
			//System.out.println("luanList.size() == "+byteList.size());
			for(int i=0;i<byteList.size();i++){
				currentByteArray=byteList.get(i);
				//System.out.println("currentByteArray.length == "+currentByteArray.length);
				for(int q=0;q<currentByteArray.length && x<newOriginalByteArray.length;q++){
					newOriginalByteArray[x]=currentByteArray[q];
					x++;
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		return newOriginalByteArray;
	}
	
	
	/***************Outside Thread Operation**************************************/
	public void addStartLUANObject(LUANObject luan){
		startLuan=luan;
		//startTimer
	}
	
	public void addEndLUANObject(LUANObject luan){
		endLuan=luan;
		//endTimer
	}
	
	int counter=0;
	public void addByteArrayToByteArrList(byte[] byteArr){
		byteArrList.add(byteArr);	
	//	System.out.println("new byte[] has been added to the ByteArrList at index: "+counter);
		counter++;
	}
}
