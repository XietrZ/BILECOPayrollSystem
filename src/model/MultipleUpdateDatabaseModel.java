package model;

import java.util.HashMap;

/**
 * This model is used to store the data for updating multiple data in the database.
 * @author XietrZ
 *
 */
public class MultipleUpdateDatabaseModel {
	//--> String[key] is the column Name, String is the value that changed and needed to be stored in the database for update
	public HashMap<String, Object>changesToBeUpdated;
	
	//--> Primary key used.
	public String primarykey;
	

	
	public MultipleUpdateDatabaseModel() {
		// TODO Auto-generated constructor stub
		changesToBeUpdated=new HashMap<String,Object>();
	}
	

}
