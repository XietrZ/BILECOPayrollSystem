package model;

import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import model.statics.Utilities;


public class DecimalFormatRenderer extends DefaultTableCellRenderer {
	 public Component getTableCellRendererComponent(
    		JTable table, Object value, boolean isSelected,
    		boolean hasFocus, int row, int column) {

    	//--> Without this code, the alignment of numbers is default: to the left.
    	setHorizontalAlignment(SwingConstants.RIGHT);
    	
    	//--> Add comma in double numbers more than 1000 or above.
    	 value=Utilities.getInstance().insertComma(value);
    	 
    	
    	 // And pass it on to parent class
    	 Component c =super.getTableCellRendererComponent(
    	          table, value, isSelected, hasFocus, row, column );
      
    	 return c;
    }

  
    
 }