package model.statics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import model.Filter;
import view.MainFrame;



public class FileBrowser{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private static FileBrowser instance;
	private String filePath;
	
	private void l_______________________________________________________l(){}
	
	public JFileChooser fc;
	
	private void l___________________________________________________l(){}
	
	public FileBrowser(){
		fc = new JFileChooser();
	}
	
		
	private void l_______________________________________________l(){}
	
	/**
	 * Save as pdf file where a file chooser will pop out.	
	 */
	public void savePDFFile(){
		Filter pf = new Filter("pdf");
		File f;
		
		fc.setAcceptAllFileFilterUsed(false);        //
        fc.setFileFilter(pf);

        String timeStamp = new SimpleDateFormat("MMddyy_HHmmss").format(Calendar.getInstance().getTime());
        
//        if(CPUModel.getInstance().isCPU())
        	 f = new File(timeStamp+ "_CPU");
       
        
        
        fc.setSelectedFile(f);
        
        //--> Show the save dialog. 
        int returnVal = fc.showSaveDialog(MainFrame.getInstance());
        
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
             File file = fc.getSelectedFile();
             System.out.println("\tFile: "+ file.toString()+CLASS_NAME);
             if(file.getName().lastIndexOf('.') == -1 || pf.getExtension(file) == null)
            	 file = new File(file.toString().concat(".pdf")); 
            
             if (file.exists()) {
                // prompt for overwrite
                int n = JOptionPane.showConfirmDialog(MainFrame.getInstance(), "File already exists, overwrite?",
                        "File exists",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                // Abort if they say no
                if (n == JOptionPane.NO_OPTION) return;
                
                try {
//					PDFCreator.getInstance().createPdf(file.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            else{
				try {
//					PDFCreator.getInstance().createPdf(file.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            	
        }

	}
	
	private void l_____________________________________________l(){}
	public static FileBrowser getInstance(){
		if(instance == null)
			instance = new FileBrowser();
		
		return instance;
	}
	
	public static void setInstanceToNull(){
		instance =null;
	}
}
