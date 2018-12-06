import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;



public class SUCGetter {
	
	/**
	 * Diese Methoden ziehen die Informationen momentan aus einem .txt file
	 * Das fertige Tool sollte sich durch Methoden der AML-Engine aus dem jeweiligen .aml-file bedienen.
	 * @throws IOException 
	 * */
	
	static String path;
	
	
	public static void readFile () throws IOException
	{
		System.out.println("read file");
		
		final JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = fc.showOpenDialog(null);
		

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
			String title = getTitle(selectedFile.getAbsolutePath());
			String [] names = getNames(selectedFile.getAbsolutePath());
			ViewBuilder.addSucContainer(title, names);
		}
	}

	
	public static String getTitle (String filePath) throws IOException 
	{
		path = filePath;
		String strLine;
		FileReader fr = new FileReader(filePath);
	    BufferedReader br = new BufferedReader(fr);
	    
	    strLine = br.readLine();
	    String arr[]=strLine.split(":",2);
	    return arr[0];
	}
	
	public static String[] getNames (String filePath) throws IOException
	  {
		String strLine;
	    FileReader fr = new FileReader(filePath);
	    BufferedReader br = new BufferedReader(fr);
	    String [] allSUCNames = new String[100];
	    
	    int count=0;
	    
	    while ((strLine = br.readLine()) != null)   {
	    	   if (count>0) {
	    	  String arr[]=strLine.split(":",2);
	    	  allSUCNames[count]=arr[0];
	    	  System.out.println(allSUCNames[count]);
	    	   }
	    	  count++;
	    	   
	    	}
	    
	    br.close();
	    
	    List<String> list = new ArrayList<String>();

	    for(String s : allSUCNames) {
	       if(s != null && s.length() > 0) {
	          list.add(s);
	       }
	    }
	    allSUCNames = list.toArray(new String[list.size()]);
	    return allSUCNames;
	  }
	
	
	public static String[] getAttributes (String name) throws IOException
	  {
		FileReader fr = new FileReader(path);
	    BufferedReader br = new BufferedReader(fr);
	    
	    //hard gecodet, hier sollten die Attribute und die zugehörigen Werte des IE aus der aml-Datei in den String Array gelesen werden.
	    String [] allSUCAttributes = {"Name", name, "x-Koordinate","500","y-Koordinate", "300"};
	    
	    System.out.println("Name: "+name);
	    return allSUCAttributes;
	  }

}
