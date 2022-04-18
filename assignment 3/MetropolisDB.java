package assign3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MetropolisDB {
	
	
	private String selectStatement="SELECT metropolis, continent, population FROM metropolises where ";
	private String addStatement="INSERT INTO metropolises VALUES";
	private Connection con;
	
	
	
	/*loads the given database */
	private void loadDatabase(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			con=DriverManager.getConnection("jdbc:mysql://"+MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME, MyDBInfo.MYSQL_PASSWORD);
			Statement stmt=con.createStatement();
			stmt.executeQuery("use "+MyDBInfo.MYSQL_DATABASE_NAME);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* constructor for MetropolisDB class */
	public MetropolisDB(){
		loadDatabase();
	}
	
	
	/* adds a new row based on metropolis, continent and population */
	public int add(String Metropolis, String Continent, String population){
		Statement st=null;
		String q=addStatement+"("+"\""+Metropolis+"\""+", "+"\""+Continent+"\""+", "+population+")";
		if(!checkNumberFormat(population))return 0;
		try {
			st = con.createStatement();
			st.executeUpdate(q);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 1;
	}
	
	
	/* checks whether the population string is really a positive integer */
	private boolean checkNumberFormat(String population){
		try{
			long l=Long.parseLong(population);
			if(l<0){
				System.out.println("Invalid number input");
				return false;
			}
		}catch(NumberFormatException e){
			System.out.println("Invalid number input");
			return false;
		}
		return true;
	}
	
	
	
	/* searches the rows in the database based on the given parameteers */
	public ResultSet search(String Metropolis, String Continent, String population, boolean exactMatch, boolean greaterThan){
		ResultSet rs=null;
		if(!population.equals("") && !checkNumberFormat(population))return rs;
		if(Metropolis.equals("") && Continent.equals("") && population.equals("")){
			return AllFieldsEmpty();
		}
		String q="";
		q+=takeInformationBasedOnMetropolis(Metropolis, q, exactMatch);
		q+=takeInformationBasedOnContinent(Continent, q, exactMatch);
		q+=takeInformationBasedOnPopulation(population, q, greaterThan);
		q=selectStatement+q;
		rs=executeQuery(q);
		return rs;
	}
	
	
	/* returns resultset containing all the rows in the database */
	private ResultSet AllFieldsEmpty(){
		Statement st;
		ResultSet rs=null;
			try {
				st = con.createStatement();
				 rs=st.executeQuery("SELECT * FROM metropolises");
				 return rs;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return rs;
	}
	
	
	
	/* executes the given query */
	private ResultSet executeQuery(String query){
		Statement st;
		ResultSet rs=null;
		try {
			st = con.createStatement();
			rs = null;
			 rs=st.executeQuery(query);
			 return rs;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return rs;
	}
	
	
	
	/* checks whether the user wishes to search by metropolises and updates query based on it */
	private String takeInformationBasedOnMetropolis(String Metropolis, String query, boolean exactMatch){
		if(!Metropolis.equals("")){
			if(exactMatch){
				return "metropolis="+"'"+Metropolis+"'";
			}
			else{
				return "metropolis like '%"+Metropolis+"%'";
			}
		}
		return "";
	}
	
	/* checks whether the user wishes to search by continent and updates the query based on it */
	private String takeInformationBasedOnContinent(String Continent, String query, boolean exactMatch){
		if(!Continent.equals("")){
			String and="";
			if(!query.equals(""))and+=" and ";
			if(exactMatch){
				return and+"continent="+"'"+Continent+"'";
			}
			else{
				return and+"continent like "+"'%"+Continent+"%'";
			}
		}
		return "";
	}
	
	
	/* checks whether the user wishes to search by population and updates the query based on it */
	private String takeInformationBasedOnPopulation(String population, String query, boolean greaterThan){
		if(!population.equals("")){
			String and="";
			if(!query.equals(""))and+=" and ";
			if(greaterThan){
				return and+"population>"+population;
			}
			else{
				return and+"population<="+population;
			}
		}
		return "";
	}
	
	
	public void finish(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
