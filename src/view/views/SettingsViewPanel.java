package view.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableRowSorter;



import database.Database;
import model.Constant;
import model.MultipleUpdateDatabaseModel;
import model.PayrollTableModel;
import model.PayslipComponentInfo;
import model.PayslipDataStorageInfo;
import model.statics.Images;
import model.statics.Utilities;
import model.view.DisplayOptionsDialog;
import model.view.Edit15th30thTotalDialog;
import model.view.OptionViewPanel;
import model.view.PayrollSlipLayout;
import model.view.ReusableTable;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

public class SettingsViewPanel extends JPanel {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	
	private static SettingsViewPanel instance;
	private Utilities util;
	private Images img;
	private JLabel settingsViewPanelBg;
	private JPanel viewPanel,topPanel,centerPanel;
	private void l__________________________l(){}
	public ReusableTable dynamicTable,fixedTable,fullScreenTable;
	public JButton settingsOptionButton,
	 	saveButton,cancelButton,
	 	addButton, editButton,deleteButton;
	public JScrollPane dynamicTableScrollPane,fixedTableScrollPane,fullScreenTableScrollPane;
	public OptionViewPanel optionPanel;
	public int mode=-1;
	public ArrayList<MultipleUpdateDatabaseModel>multipleUpdateList;
	public JLabel rowCountLabel,optionSettingTitleLabel;
	public Edit15th30thTotalDialog edit15th30thTotalDialog;
	
	
	private void l________________________________l(){}
	
	/**
	 * Create the panel.
	 */
	public SettingsViewPanel() {
		init();
		set();
	}
	private void init(){
		img=Images.getInstance();
		util=Utilities.getInstance();
		multipleUpdateList= new ArrayList<MultipleUpdateDatabaseModel>();
		edit15th30thTotalDialog= new Edit15th30thTotalDialog();
	}
	private void set(){
		setThisPanelComponents();
		setOptionPanelComponents();
		setViewPanel();
		setTopPanelComponents();
		setButtonPanelComponents();
		setCenterPanelComponents();
		setTableComponents();
		setBottomPanelComponents();
		
		//--> Add background
//		add(payrollViewPanelBg);
		
		repaint();
	}
private void l____________________________l(){}
	
	/**
	 * Set this panel components.
	 */
	private void setThisPanelComponents(){
		setBounds(0, Constant.VIEW_Y, Constant.VIEW_WIDTH, Constant.VIEW_HEIGHT);
		setBackground(Color.GREEN);
		setLayout(null);
		setVisible(false);
		setOpaque(false);	
	}
	
	private void setOptionPanelComponents(){
		String[] buttonKeyList=null;
		ImageIcon[] imageList=null;
		ImageIcon[] imageHoverList=null;
			
		optionPanel=new OptionViewPanel(
				buttonKeyList, null, 
				imageList, imageHoverList, 
				163, 44, 194, 249
		);
		
		add(optionPanel);
	}
	
	
	private void setNecessaryOptionPanelComponentsWhenExtended(ImageIcon bgImg,int width,int height,
			String[]buttonKeyList, ImageIcon[]imageList, ImageIcon[] imageHoverList){
		
		//--> Set Calculation Panel
		optionPanel.setLayout(null);
		optionPanel.setOpaque(false);
		optionPanel.bg.setIcon(bgImg);
		optionPanel.setSize(width, height);
		optionPanel.bg.setBounds(0,0,optionPanel.getWidth(),optionPanel.getHeight());
		
		
		optionPanel.initNewListValues(buttonKeyList, imageList, imageHoverList);
		optionPanel.addOptionButtons();
		repaint();
		
	}
	
	
	
	
	
	/**
	 * Set view panel where top panel and center panel is added.
	 */
	private void setViewPanel(){
		//--> Set View Panel:
		viewPanel=new JPanel();
		viewPanel.setBounds(0,0,this.getWidth(),this.getHeight());
		viewPanel.setBackground(Color.BLUE);
		viewPanel.setOpaque(false);
		viewPanel.setLayout(new BorderLayout());
		add(viewPanel);
		
		//--> Add background image label. Always add this at the last!
		settingsViewPanelBg = new JLabel(img.payrollViewPanelBgImg);
		settingsViewPanelBg.setBounds(0, 0, this.getWidth(), this.getHeight());
	}
	
	/**
	 * Set components of the top panel.
	 */
	private void setTopPanelComponents(){
		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(0,100));
		topPanel.setBackground(Constant.BILECO_DEFAULT_COLOR_GREEN);
		topPanel.setOpaque(false);
		topPanel.setLayout(null);
		viewPanel.add(topPanel, BorderLayout.NORTH);
		
		//--> Add View TITLE Label.
		JLabel lblSettingsTitle = new JLabel(img.settingViewTitleImg);
		lblSettingsTitle.setBounds(13, 13, 144,26);
		lblSettingsTitle.setFont(Constant.VIEW_TITLE);
		topPanel.add(lblSettingsTitle);
		
		//--> Add Option button.
		settingsOptionButton = Utilities.getInstance().initializeNewButton(
				lblSettingsTitle.getX()+lblSettingsTitle.getWidth(), 5, 47, 47, img.optionBtnImg, img.optionHoverBtnImg
		);
		settingsOptionButton.setFocusable(false);
		topPanel.add(settingsOptionButton);
		
		//--> Add the TITLE option button/label
		int optionSettingTitleBtnWIDTH=316,optionSettingTitleBtnHEIGHT=36;
		optionSettingTitleLabel=new JLabel();
		optionSettingTitleLabel.setHorizontalAlignment(JLabel.CENTER);
		optionSettingTitleLabel.setVerticalAlignment(JLabel.CENTER);
		optionSettingTitleLabel.setBounds(
			Constant.VIEW_WIDTH/2-(optionSettingTitleBtnWIDTH/2),
			50,
			optionSettingTitleBtnWIDTH,
			optionSettingTitleBtnHEIGHT
		);
		topPanel.add(optionSettingTitleLabel);

	}
	
	private void setButtonPanelComponents(){
		int buttpaneDesiredWidth=240;
		JPanel buttonPane = new JPanel();
		buttonPane.setOpaque(false);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPane.setBounds(
				this.getWidth()-buttpaneDesiredWidth,5,
				buttpaneDesiredWidth,70
		);
		topPanel.add(buttonPane);
		
		addButton = util.initializeNewButton(-1, -1, -1, -1, img.edAddImg, img.edAddImgHover);
		buttonPane.add(addButton);
		
		editButton = util.initializeNewButton(-1, -1, -1, -1, img.edEditImg, img.edEditImgHover);
		buttonPane.add(editButton);
		
		deleteButton = util.initializeNewButton(-1, -1, -1, -1, img.edDeleteImg, img.edDeleteImgHover);
		buttonPane.add(deleteButton);
	
		
		saveButton= util.initializeNewButton(-1, -1, -1, -1, img.edSaveImg, img.edSaveImgHover);
		buttonPane.add(saveButton);
		saveButton.setVisible(false);
	
		cancelButton = util.initializeNewButton(-1, -1, -1, -1, img.edCancelImg, img.edCancelImgHover);
		buttonPane.add(cancelButton);
		cancelButton.setVisible(false);
		
		
	}
	
	private void setCenterPanelComponents(){
		centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(0,100));
		centerPanel.setBackground(Constant.BILECO_DEFAULT_COLOR_GREEN);
		centerPanel.setOpaque(false);
		centerPanel.setLayout(null);
		viewPanel.add(centerPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Set components of the table panel.
	 */
	private void setTableComponents(){
		int tablePanelDesiredWith=1200,tablePanelDesiredHeight=460;
		JPanel tablePanel = new JPanel();
		tablePanel.setBackground(Color.BLACK);
		tablePanel.setOpaque(false);
		tablePanel.setLayout(null);
		tablePanel=setBoundsAtCenterOfPanelDependingGivenWidthHeight(tablePanel, tablePanelDesiredWith, tablePanelDesiredHeight);
		centerPanel.add(tablePanel);
		
		//--------------------------------------------------------------------------------------

		//--> Set TABLE
		TableRowSorter<PayrollTableModel> sorter;
		
		//> Full Screen Table
		PayrollTableModel fullScreenTableModel=new PayrollTableModel();
		sorter= new TableRowSorter<PayrollTableModel>(fullScreenTableModel); 
		fullScreenTable=new ReusableTable(fullScreenTableModel, sorter, false);
//		fullScreenTable.setBackground(new Color(255, 255, 153));
		fullScreenTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		
		
		PayrollTableModel tableModelForFixedDynamicTable=new PayrollTableModel();
		sorter=new TableRowSorter<PayrollTableModel>(tableModelForFixedDynamicTable);
		
		//> Fixed Table		
		fixedTable=new ReusableTable(tableModelForFixedDynamicTable, sorter, false);
		fixedTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				
						
						
		//> Dynamic Table		
		dynamicTable=new ReusableTable(tableModelForFixedDynamicTable, sorter,false);
		dynamicTable.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		
		//--------------------------------------------------------------------------------------
		
		//--> Set SCROLLPANE
		 //--> Create the scroll pane and add the table to it.
		//--> Remember: To show the horizontal scroll bar, 
		//		the main panel layout must be null/absolute and autoresize of table id OFF.
		//		Since the horizontal scroll bar is not shown if the scrollpane is too long.
		//		Height of a horzontak scrollbar is 20;
		
		fullScreenTableScrollPane = new JScrollPane(fullScreenTable);
		fullScreenTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		fullScreenTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		fullScreenTableScrollPane.setOpaque(false);
		fullScreenTableScrollPane.setBounds(0,0,
				tablePanel.getWidth(),
				tablePanel.getHeight());
		tablePanel.add(fullScreenTableScrollPane);
//		fullScreenTableScrollPane.setVisible(false);
		
		
		fixedTableScrollPane = new JScrollPane(fixedTable);
		fixedTableScrollPane.setBounds(
				Constant.SCROLL_BAR_WIDTH, 									//x
				0,															//y		
				Constant.VIEW_WIDTH-620-Constant.SCROLL_BAR_WIDTH*2,		//width
				tablePanel.getHeight()-(Constant.SCROLL_BAR_WIDTH+14));	//height
		fixedTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		fixedTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tablePanel.add(fixedTableScrollPane);
		fixedTableScrollPane.setVisible(false);
		
		
		
		
		dynamicTableScrollPane = new JScrollPane(dynamicTable);
		dynamicTableScrollPane.setBounds(
				fixedTableScrollPane.getX()+fixedTableScrollPane.getWidth(), 						// x
				 0,																					// y						
				tablePanel.getWidth()-fixedTableScrollPane.getWidth()-Constant.SCROLL_BAR_WIDTH*2,	// width
				tablePanel.getHeight()-20);																// height
		dynamicTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		dynamicTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tablePanel.add(dynamicTableScrollPane);
		dynamicTableScrollPane.setVisible(false);
		
		
		
		//--> Set when you scroll down the dynamic table, the fixed table will scroll down too.
		fixedTableScrollPane.getVerticalScrollBar().setModel(dynamicTableScrollPane.getVerticalScrollBar().getModel());
	}
	
	
	private void setBottomPanelComponents(){
		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setBackground(Color.ORANGE);
		bottomPanel.setPreferredSize(new Dimension(0,20));
		bottomPanel.setLayout(null);
		viewPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		rowCountLabel = new JLabel("Row Count: 0");
		rowCountLabel.setBounds(10, 0, Constant.ROW_COUNT_LABEL_WIDTH, Constant.ROW_COUNT_LABEL_HEIGHT);
		bottomPanel.add(rowCountLabel);
		
	}
	
	private void l_________________________________l(){}
	/**
	 * This method sets the width and height of panel depending on the given width and height value
	 * 		and making sure that the panel is at the center of centerPanel.
	 * @param p
	 * @param width
	 * @param height
	 * @return
	 */
	private JPanel setBoundsAtCenterOfPanelDependingGivenWidthHeight(JPanel p,int width, int height){
		p.setBounds(
				(this.getWidth()/2)-(width/2),
				(this.getHeight()-(int)topPanel.getPreferredSize().getHeight())/2-(height/2),
				width,height);
		
		return p;
	}
	
	private void l___________________________________l(){}
	public void setAddEditDeleteBtnVisible(boolean isVisible){
		addButton.setVisible(isVisible);
		editButton.setVisible(isVisible);
		deleteButton.setVisible(isVisible);
	}

	public void setSaveCancelBtnVisible(boolean isVisible){
		saveButton.setVisible(isVisible);
		cancelButton.setVisible(isVisible);
	}
	
	
	public void setContractualOptionPanelComponents(){
		String[] buttonKeyList={
				Constant.DEPARTMENT_SETTING_BTN,
				Constant.PHIC_SALARY_RANGE_SETTINGS_BTN,
				Constant.PHIC_RATE_SETTING_BTN,
				Constant.PAGIBIG_SETTING_BTN,
				Constant.SSS_SETTING_BTN,
				Constant.DIVIDER_SETTING_BTN,
				Constant.RATE_PER_DAY_SETTING_BTN
			};
			ImageIcon[] imageList={
					img.departmentSettingImg,
					img.phicSalaryRangfeImg,
					img.phicRateImg,
					img.pagibigImg,
					img.sssImg,
					img.dividerOptionImg,
					img.contractualRatePerDayImg
			};
			ImageIcon[] imageHoverList={
					img.departmentSettingImgHover,
					img.phicSalaryRangfeImgHover,
					img.phicRateImgHover,
					img.pagibigImgHover,
					img.sssImgHover,
					img.dividerOptionImg,
					img.contractualRatePerDayImgHover
			};
			
			
			
			setNecessaryOptionPanelComponentsWhenExtended( 
				img.settingsContractualOptionPanelBgImg, 194, 153,
				buttonKeyList, imageList, imageHoverList
			);
	}
	
	/**
	 * Set component of option panel for Regular.
	 */
	public void setRegularOptionPanelComponents(){
		String[] buttonKeyList={
			Constant.ASEMCO_SETTING_BTN,
			Constant.DEPARTMENT_SETTING_BTN,
			Constant.PHIC_SALARY_RANGE_SETTINGS_BTN,
			Constant.PHIC_RATE_SETTING_BTN,
			Constant.PAGIBIG_SETTING_BTN,
			Constant.SSS_SETTING_BTN,
			Constant.ST_PETER_SETTING_BTN,
			Constant.UNION_DUES_SETTING_BTN,
			Constant.DIVIDER_SETTING_BTN,
			Constant.ECOLA_SETTING_BTN,
			Constant.LAUNDRY_ALLOWANCE_SETTING_BTN,
			Constant.LONGEVITY_SETTING_BTN,
			Constant.RICE_SETTING_BTN
		};
		ImageIcon[] imageList={
				img.asemcoOcciBCCDbpImg,
				img.departmentSettingImg,
				img.phicSalaryRangfeImg,
				img.phicRateImg,
				img.pagibigImg,
				img.sssImg,
				img.stPeterImg,
				img.unionDuesImg,
				img.dividerOptionImg,
				img.ecolaOptionSettingImg,
				img.laundryAllowanceOptionSettingImg,
				img.longevityOptionSettingImg,
				img.riceOptionSettingImg
		};
		ImageIcon[] imageHoverList={
				img.asemcoOcciBCCDbpImgHover,
				img.departmentSettingImgHover,
				img.phicSalaryRangfeImgHover,
				img.phicRateImgHover,
				img.pagibigImgHover,
				img.sssImgHover,		
				img.stPeterImgHover,
				img.unionDuesImgHover,
				img.dividerOptionImg,
				img.ecolaOptionSettingImgHover,
				img.laundryAllowanceOptionSettingImgHover,
				img.longevityOptionSettingImgHover,
				img.riceOptionSettingImgHover
		};
		
		
		
		setNecessaryOptionPanelComponentsWhenExtended( 
			img.settingsOptionPanelBgImg, 194, 249,
			buttonKeyList, imageList, imageHoverList
		);
	}
	private void l_________________________________________________l(){}
	
	
	public static SettingsViewPanel getInstance(){
		if(instance==null)
			instance=new SettingsViewPanel();
		return instance;
	}
	public static void setInstanceToNull(){
		instance =null;
	}

}

