package assign3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class MyTableModel extends AbstractTableModel {
	
	private List<MData> data;
	private String columnNames[];
	private MetropolisDB mdb;
	
	
	
	/* initializes columnNames based on the given one */
	private void initializeColumnNames(String columns[]){
		columnNames=new String[columns.length];
		for(int i=0; i<columns.length; i++){
			columnNames[i]=new String(columns[i]);
		}
	}
	
	/**
	 * main constructor for MyTableModel class.
	 * @param columns - column names for the table
	 * @param db - database which this table interacts with
	 */
	public MyTableModel(String columns[], MetropolisDB db){
		data=new ArrayList<MData>();
		initializeColumnNames(columns);
		mdb=db;
	}
	
	
	/**
	 * searches for entries in the database which meet the required conditions and updates table content.
	 * @param Metropolis - metropolis name
	 * @param Continent -  continent name
	 * @param population -  population amount
	 * @param exactMatch - boolean describing what kind of searching the user prefers for continents and metropolises
	 * @param greaterThan - boolean describing what kind of searching the user prefers for population number
	 */
	public void search(String Metropolis, String Continent, String population, boolean exactMatch, boolean greaterThan){
		ResultSet rs=mdb.search(Metropolis, Continent, population, exactMatch, greaterThan);
		data.clear();
		if(rs!=null)updateTable(rs);
		fireTableDataChanged();
	}
	
	
	/* updates the table contents based on resultset */
	private void updateTable(ResultSet rs){
		try {
			while(rs.next()){
				MData m=new MData(rs.getString(1), rs.getString(2), rs.getString(3));
				data.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * adds a new row in the database and updates the table, showing only this entry
	 * @param Metropolis - metropolis name
	 * @param Continent - continent name
	 * @param population - population amount
	 */
	public void add(String Metropolis, String Continent, String population){
		int res=mdb.add(Metropolis, Continent, population);
		if(res!=0){
			MData m=new MData(Metropolis, Continent, population);
			data.clear();
			data.add(m);
			fireTableDataChanged();
		}
	}
	
	/**
	 * Returns the number of rows in the model. A JTable uses this method to determine how many rows it should display. 
	 * This method should be quick, as it is called frequently during rendering.
	 * @return the number of rows in the model
	 */
	@Override
	public int getRowCount() {
		return data.size();
	}

	
	
	/**
	 * Returns the number of columns in the model. 
	 * A JTable uses this method to determine how many columns it should create and display by default.
	 * @return the number of columns in the model
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	
	/**
	 * Returns the value for the cell at columnIndex and rowIndex. 
	 * @param rowIndex - the row whose value is to be queried, columnIndex - the column whose value is to be queried
	 * @return the value Object at the specified cell
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object result=data.get(rowIndex).get(columnIndex);
		return result;
	}
	
	
	/**
	 * Returns the name of the column at columnIndex. This is used to initialize the table's column header name. Note: this name does not need to be unique;
	 *  two columns in a table can have the same name.
	 *  @param columnIndex - the index of the column
	 *  @return the name of the column
	 */ 
			
	@Override
	public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
	
	
	
	public static void main(String[] args) {
		List<MData> l=new ArrayList<MData>();
		MetropolisDB mdb=new MetropolisDB();
		String[] columnNames={"Metropolis", "Continent", "Population"};
		MyTableModel m=new MyTableModel(columnNames, mdb);
		MetropolisView v=new MetropolisView(m);	
	}
}
