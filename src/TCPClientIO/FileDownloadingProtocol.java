package TCPClientIO;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JFileChooser;

import org.luan.LUANObject;

public class FileDownloadingProtocol implements Runnable{
	private static ConcurrentHashMap <String, ArrayList <LUANObject>> luanListMap = new ConcurrentHashMap<String, ArrayList <LUANObject>>();
	
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
			for (String key : luanListMap.keySet()) {
				ArrayList <LUANObject> luanList=luanListMap.get(key);
				LUANObject luan=luanList.get(0);
				if(luan.getInteger("totaltAmountOfPackages")==luanList.size()){
					luanList=sortLuanList(luanList);
					byte[] original=getOriginalByteArrayFromSegentedByteArrays(luanList);
					saveDialog(original, luan,key);
				    luanListMap.remove(key);
				}
			
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private ArrayList <LUANObject> sortLuanList(ArrayList <LUANObject> filePackages){
		try{
			for(int i=0;i<filePackages.size();i++){
				for(int q=0;q<filePackages.size();q++){
					if(filePackages.get(q).getInteger("packageNbr")>filePackages.get(i).getInteger("packageNbr")){
						Collections.swap(filePackages, i, q);
					}
				}
			}
			/*for(int i=0;i<filePackages.size();i++){
				System.out.println("ints.get("+i+").getPackageNbr() == "+filePackages.get(i).getPackageNbr());
			}
			System.out.println("\n\n\n\n");*/
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return filePackages;
	}
	
	
	private void saveDialog(byte[] byteFile,LUANObject luan,String key){
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
			  //  removeAllItemsFromSegemntedByteArrayes();
			  //  luanInfo=null;
			  //  downloadDone=false;
			    //erase grapihcs
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/*private void saveDialog(byte[] byteFile,LUANObject luan){
		try{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Save the file: "+luan.getString("fileName"));
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			
				File file = fileChooser.getSelectedFile();
				String fileName=fileChooser.getCurrentDirectory()+"\\"+fileChooser.getName(file)+luan.getString("fileType");
				FileOutputStream fos = new FileOutputStream(fileName);
			    fos.write(byteFile);
			    fos.close();
			    luanListMap.remove(luan.getString("operationID"));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}*/
	
	private byte[] getOriginalByteArrayFromSegentedByteArrays(ArrayList <LUANObject> luanList){
		System.out.println("getOriginalByteArrayFromSegentedByteArrays() ENTERED");
		byte[] newOriginalByteArray=null;
		try{
			System.out.println("luanList.get(0).getInteger(\"originalLength\") == "+luanList.get(0).getInteger("originalLength"));
			newOriginalByteArray = new byte[luanList.get(0).getInteger("originalLength")];
			byte[] currentByteArray=null;
			int x=0;
			int count=newOriginalByteArray.length;
			
			System.out.println("luanList.size() == "+luanList.size());
			for(int i=0;i<luanList.size();i++){
				currentByteArray=luanList.get(i).getByteArray("byteArray");
				for(int q=0;q<currentByteArray.length && x<newOriginalByteArray.length;q++){
					newOriginalByteArray[x]=currentByteArray[q];
					x++;
				}
				count=(count-currentByteArray.length);
				System.out.println("This currentByte array length is: "+currentByteArray.length);
				System.out.println(i+" segments integrated: "+count+" to go!!\n\n");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		return newOriginalByteArray;
	}
	
	
	/***************Outside Thread Operation**************************************/
	public void addLUANObjects(LUANObject luan){
		try{
			if(luanListMap.containsKey(luan.getString("operationID"))){
				luanListMap.get(luan.getString("operationID")).add(luan);
			}else{
				ArrayList <LUANObject>luanList = new ArrayList<LUANObject>();
				luanList.add(luan);
				luanListMap.put(luan.getString("operationID"), luanList);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
