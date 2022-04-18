package assign3;



/* a class which describes the row of a tablemodel consisting of 3 columns*/
public class MData {
	private String[] datas;
	
	/* constructor for the mdata */
	public MData(String m, String c, String p){
		datas=new String[3];
		datas[0]=m;
		datas[1]=c;
		datas[2]=p;
	}
	
	
	public String get(int column){
		return datas[column];
	}
}
