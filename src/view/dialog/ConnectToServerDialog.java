package view.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ConnectToServerDialog extends JDialog {
	
	private static ConnectToServerDialog instance;
	private JPanel contentPanel;
	private JLabel lblHostName, lblPortNumber;
	
	
	private void l____________________________________________l(){}
	
	public JButton okButton,cancelButton;
	private void l______________________________________l(){}
	
	public JTextField hostNameTextfield, portNumberTextfield, databaseTextfield;

	private void l______________________________________________l(){}
	/**
	 * Create the dialog.
	 */
	public ConnectToServerDialog() {
		
		
		
		init();
		set();
	}
	
	private void init(){
		getContentPane().setLayout(new BorderLayout());
		
		
		initContentPanel();
		initButtonPanel();
		
		
		
		
	}
	
	private void set(){
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setUndecorated(true);
		setLocationRelativeTo(null);
	}
	
	private void initButtonPanel(){
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPane.setBackground(Color.GRAY);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
	

		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		
	}
	
	
	
	private void initContentPanel(){
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblHostName = new JLabel("Host Name:");
		lblHostName.setBounds(40, 51, 100, 14);
		contentPanel.add(lblHostName);
		
		lblPortNumber = new JLabel("Port Number: ");
		lblPortNumber.setBounds(40, 76, 100, 14);
		contentPanel.add(lblPortNumber);
		
		hostNameTextfield = new JTextField("localhost");
		hostNameTextfield.setBounds(150, 48, 165, 20);
		contentPanel.add(hostNameTextfield);
		hostNameTextfield.setColumns(10);
		
		portNumberTextfield = new JTextField("3306");
		portNumberTextfield.setColumns(10);
		portNumberTextfield.setBounds(150, 73, 165, 20);
		contentPanel.add(portNumberTextfield);
		
		JLabel lblDatabase = new JLabel("Database Name: ");
		lblDatabase.setBounds(39, 101, 101, 14);
		contentPanel.add(lblDatabase);
		
		databaseTextfield = new JTextField("bileco_db");
		databaseTextfield.setBounds(150, 98, 165, 20);
		contentPanel.add(databaseTextfield);
		databaseTextfield.setColumns(10);
		
		
		
		
	}
	private void l___________________l(){}
	
	public static ConnectToServerDialog getInstance(){
		if(instance==null)
			instance=new ConnectToServerDialog();
		return instance;
	}
	public static void setInstanceToNull(){
		instance =null;
	}
}
