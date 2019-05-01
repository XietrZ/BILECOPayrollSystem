package model;

import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import model.statics.Utilities;


public class DateFormatRenderer extends DefaultTableCellRenderer {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	 public Component getTableCellRendererComponent(
    		JTable table, Object value, boolean isSelected,
    		boolean hasFocus, int row, int column) {

    	Utilities util= Utilities.getInstance();
    	//--> Debugging Purposes
//    	System.out.println("\t\t Date Renderer VALUE: "+value.toString()
//    			+"\t Column Name: "+table.getModel().getColumnName(column+1)
//				+"\t STRING NULL: "+Constant.STRING_DATE_LEFT_NULL
//				+"value.toString().equals(Constant.STRING_DATE_LEFT_NULL): "+value.toString().equals(Constant.STRING_DATE_LEFT_NULL)+CLASS_NAME);
    	
    	value=(value.toString().equals(Constant.STRING_DATE_LEFT_NULL))? // When the date is empty or null or 1900-01-01
    			"":
    			util.convertDateYyyyMmDdToReadableDate(value.toString());

    	
    	 // And pass it on to parent class
    	 Component c =super.getTableCellRendererComponent(
    	          table, value, isSelected, hasFocus, row, column );
      
    	 return c;
    }

  
    
 }