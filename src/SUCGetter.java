import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class SUCGetter {
	
	/**
	 * Diese Methoden ziehen die Informationen momentan aus einem .txt file
	 * Das fertige Tool sollte sich durch Methoden der AML-Engine aus dem jeweiligen .aml-file bedienen.
	 * */

	public static String[] getNames () throws IOException
	  {
		String strLine;
	    FileReader fr = new FileReader("SUC.txt");
	    BufferedReader br = new BufferedReader(fr);
	    String [] allSUCNames = new String[100];
	    
	    int count=0;
	    
	    while ((strLine = br.readLine()) != null)   {
	    	   
	    	  String arr[]=strLine.split(":",2);
	    	  allSUCNames[count]=arr[0];
	    	  System.out.println(allSUCNames[count]);
	    	  count++;
	    	}
	    
	    br.close();
	    
	    return allSUCNames;
	  }
	
	
	public static String[][] getAttributes () throws IOException
	  {
		String strLine;
	    FileReader fr = new FileReader("SUC.txt");
	    BufferedReader br = new BufferedReader(fr);
	    String [][] allSUCAttributes = new String[100][100];
	    
	    int count=0;
	    
	    while ((strLine = br.readLine()) != null) {

	    		String arr[]=strLine.split(":");
	    for(int i =0; i<arr.length;i++) {
  	  			allSUCAttributes[count][i]=arr [i];
	    }
  	  			System.out.println(Arrays.toString(allSUCAttributes[count]));
  	  		count++;   	  
	    	}
	    br.close();
	    
	    return allSUCAttributes;
	  }

}
