import javax.swing.JOptionPane;

import view.MainFrame;

Done Update:
	
	[Note: Bugs/Revisions Needed To Be Solved After Sir Michael used My Payroll System. Payroll Date: June 15,2018]


		[December 3, 2018]
			* Change all 'Comments' in UI to 'Note'.
			* Edit UI: Display PDF/Excel dialog box in PayrollViewPanel.
			* Edit UI: With ATM or without ATM dialog box in PayrollViewPanel.
		
		[December 5, 2018]
			* Edit UI: Edit Signature Info in PayrollViewPanel.	
			* Add the edit digital signature on Edit Signature Info dialog box.
 		
 		[December 7, 2018]
	 		* Add DateLeft on database
			* Add DateLeft on Add Employee dialog
			* Dont include the employees who left when adding payroll date.
			* Check the update employee info feature and make sure no bug
		
		[December 9, 2018]
			* Debug all code related to DateLeft mechanism.
			* Adjust the name and signature on payslip dialog.
			* Set the default value of Note/Comments from 0.0 to - .
		
		[December 12, 2018]
			* Ask for signature ni maam fam.
----------------------------------------------------------------------------------------------------------------		
Next Planned Addition Update:
	[UPDATE on DATABASE]
	

	
	[Note: Bugs/Revisions Needed To Be Solved After Sir Michael used My Payroll System. Payroll Date: June 29,2018]
			
		ALWAYS REMEMBER: Make your program faster than the excel. If it fails, useless ang imung program.
		
		
		
		Priority Task Total: 21
		
		* Create BPS and BPSX exe file for new update
		* Set look and feel on windows xp if possible, the payroll system look
			and feel is the same with windows 7 look and feel when running the 
			program on windows xp.		
		* Add a column of DateLeft to make sure employees that left wont be
 			included when creating payroll data
		* Add credits on menu where I will put my name there, for insurannce
			purposes.
		* Think of ways on how to not delete the data of a contractual when 
 			he/she becomes a regular employee.
		* In contractual, since there are times that contractual employees is 
			promoted to regular employes. When this happened, do not delete the 
			employee, instead just put a LeftDate column name on employee
			table. The data shlould not be erased despite that the emplpyee is
			promoted or left the COOP. The changes happens when adding payroll
			data, those that are promoted to regular and left the coop
			should not be included.
		
		
		
		
		
	* Deleting Payroll Date in regular will also delete on contractual. thimnk of something
	* Update Regular Employee List kay c Jay naa nas regular. Update ang salary.
	* Include NHA employees.. Wa pa cla maapil man
	* Add Leave credits on employee table.	
	* Add Logs Database
	* Add Signature Info Database
	* OVERTIME, NS-DIFF, L-W/O PAY, they are related sa Biometrics. 
		Create an implementation of these features. Create TABLE for these three.
	* OVERTIME should have a different table. Naa xay three modes, 25, 35 45 percent.
		For instance, when regular day, holiday has different computations
	* Use sha1 to encrypt password.
	* Modify Connect to server settings. Modify Connectivity Parameters
	* Locked mechanism on already done payroll, para di xa maedit, dapat ang past payroll data are for viewing purposes only. 
	* Create an instructions based on program screenshots
	* Only ADMIN can add/manage/edit user accounts. Put it on HomeView panel option panel.
	* Only ADMIN can do this. Add mechansim where you can set the IP Address within the program.
		Add admin password. where only authorize person is allowed to use this feature.
	* Add a print preview mechanism before printing para macheck kung sakto ba xa pagprint.
	* May problema pa kung SABAY MAG EDIT. Kay once magsave ang usa, since isave niya tanan
		sa row, then iya masave sad ang mga wa maedit. SABAY EDIT PROBLEM
	* FInd simple PDF Viwer, for the meantime use adobe reader as viewer.
	* Set the TotalFullScreenTable selection highlight by cnt in deduction/earning since tooell.
	* Add Loading label sa lower right of view panel.	
	* Remove departme much space
	* Limit heap space of the program to avoid too much use of memory especially when clicking 
		display payslip, see task manager, it uses too much memory. Debug this.
	* What to put on department column in employee table is the 
		department name, NOT the department ID.
	* Debug date in JTable, wont be readable in some computers
		[not sure if OS issue][NEED MORE INVESTIGATING!]
	* Edit that when typing date in search bar, convert to READABLE.	
	* Add leave credits[complicated,Maam Cath explanation]
	* Mangau ug overtime formula sa regular para ma.automate
	* Add Log file, just file not database. will only reset when 1 year.
	* Add a REFRESH button on all view panels.
	* Add control+z or undo option when editing on table.
	* Change implementation of generation of employee ID.
	* Dont delete employee, just put status inactive or active.
	