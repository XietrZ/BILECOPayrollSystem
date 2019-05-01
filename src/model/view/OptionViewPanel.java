package model.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.statics.Utilities;

public class OptionViewPanel extends JPanel {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private int x,y,width,height;
	private ImageIcon bgImg;
	private Utilities util;
	private ImageIcon[] imageList,imageHoverList;
	private JPanel buttonPanel;
	private void l_____________________________l(){}
	
	public HashMap<String,JButton>buttonList;
	public String[]buttonNames;
	public JLabel bg;
	
	private void l____________________l(){}
	/**
	 * Create the panel.
	 */
	public OptionViewPanel(String[]btnNames,ImageIcon bgImg,
			ImageIcon[] imageList,ImageIcon[] imageHoverList,
			int x, int y,int width,int height) {
		init(btnNames, bgImg, imageList, imageHoverList, x, y, width, height);
		set();
	}
	
	private void init(String[]btnNames,ImageIcon bgImg,
			ImageIcon[] imageList,ImageIcon[] imageHoverList,
			int x, int y,int width,int height){
		
		buttonList=new HashMap<String,JButton>();
		util=Utilities.getInstance();
		
		
		this.bgImg=bgImg;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		
		this.buttonNames=btnNames;
		this.imageList=imageList;
		this.imageHoverList=imageHoverList;
	}
	private void set(){
		setThisPanel();
		setButtonPanel();
		
		

	}
	
	
	private void l________________________________l(){}
	
	
	private void setThisPanel(){
		setBounds(x,y,width,height);
		setBackground(Color.CYAN);
		setVisible(false);
		
		if(bgImg!=null){
			setLayout(null);
			setOpaque(false);
		}
		
		//--> Set background
		bg=new JLabel(bgImg);
		bg.setBounds(0,0,this.getWidth(),this.getHeight());
		
	}
	
	/**
	 * Set button panel components
	 */
	private void setButtonPanel(){
		buttonPanel=new JPanel();
		buttonPanel.setLayout(new GridLayout((buttonNames!=null)?buttonNames.length:1,0));
		buttonPanel.setOpaque(false);
		
		addOptionButtons();
		
	}
	
	private void l___________________________________l(){}
	/**
	 * Add the option buttons
	 */
	public void addOptionButtons(){
		buttonPanel.setLayout(new GridLayout((buttonNames!=null)?buttonNames.length:1,0));
		
		int index=0,totalHeight=0,width=-1;
		
		//--> To make sure that list values are not null to avoid error.
		if(buttonNames!=null){
			for(String name:buttonNames){
				buttonList.put(
					name,
					util.initializeNewButton(
						-1,-1,-1,-1, imageList[index], imageHoverList[index])
				);
				
				JButton btn=buttonList.get(name);
				buttonPanel.add(btn);
				index++;
				totalHeight+=btn.getPreferredSize().getHeight();
				width=(width<(int) btn.getPreferredSize().getWidth())?(int) btn.getPreferredSize().getWidth():width;
				
				//--> For debugging puroses//
//				System.out.println("ASQWS Name:  "+name
//						+"\t"+btn.getMaximumSize()
//						+"\tTotal Height: "+totalHeight
//						+"\tWidth: "+width+CLASS_NAME);
	
			}
			
			//--> Set the size and location of button panel based from the 
			//		button width and total height.
			buttonPanel.setBounds(3,20,width,totalHeight);
			add(buttonPanel);
			
		}
		
		//-->Add the background image.
		remove(bg);
		repaint();
		revalidate();
		add(bg);
	}
	
	/**
	 * Used this method to put values to the list when they are initially empty or null.
	 * @param btnNames
	 * @param imageList
	 * @param imageHoverList
	 */
	public void initNewListValues(String[]btnNames,
			ImageIcon[] imageList,ImageIcon[] imageHoverList){
		this.buttonNames=btnNames;
		this.imageList=imageList;
		this.imageHoverList=imageHoverList;
	}
	

}
