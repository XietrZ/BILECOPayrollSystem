package model;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * This class is use to geerate the UI components of payslip in a more efficient way.
 * @author XietrZ
 *
 */
public class PayslipComponentInfo {
	public JLabel label;
	public JTextField textField;
	
	public PayslipComponentInfo(JLabel label, JTextField textField){
		this.label=label;
		this.textField=textField;
	}
}
