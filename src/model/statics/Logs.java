package model.statics;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import model.FileNameGenerator;



/**
 * The class that prints what you outputted at console, to a text file titled,
 * 		 "Logs.txt".
 * The only problem is this overwrite the log file. It does NOT APPEND. 
 * @author XietrZ
 *
 */
public class Logs {
	private static Logs instance;
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	private final FileNameGenerator fng;
	private PrintStream pstr;
	
	public Logs(){
		fng = new FileNameGenerator("Logs",".java");
		
		//initialize output streams
		String fname = fng.prefix+".java";
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(fname);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Writing to file "+ fname);
		pstr =new PrintStream(fos);
	}
	
	/**
	 * Print ln logs
	 * @param msg
	 */
	public void printlnLogs(String msg){
		System.out.println(msg);
		pstr.println(msg);
	}
	
	/**
	 * Print only logs
	 * @param msg
	 */
	public void printLogs(String msg){
		System.out.print(msg);
		pstr.println(msg);
	}
	
	

	public static Logs getInstance(){
		if(instance == null){
			instance = new Logs();
		}
		return instance;
	}
	
	public static void setInstanceToNull(){
		instance = null;
	}
	
}
