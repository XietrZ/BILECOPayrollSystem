package model;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class Filter extends FileFilter{
	
	private String extension;
	
	public Filter(String ext){
		this.extension = ext;
	}
	
	 public boolean accept(File f) {
	        if (f.isDirectory()) {
	            return true;
	        }

	        String extension =getExtension(f);
	        if (extension != null) {
	            if (extension.equals(this.extension)) {
	                    return true;
	            } else
	                return false;
	            
	        }

	        return false;
	    }
	    
	        /*
	     * Get the extension of a file.
	     */
	    public String getExtension(File f) {
	        String ext = null;
	        String s = f.getName();
	        int i = s.lastIndexOf('.');

	        if (i > 0 &&  i < s.length() - 1) {
	            ext = s.substring(i+1).toLowerCase();
	        }
	        return ext;
	    }

	    //The description of this filter
	    public String getDescription() {
	        return extension.toUpperCase()+" files";
	    }
	    
}
