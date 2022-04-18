
public class Account {
	
	private int idNumber;
	private int currentBalance;
	private int numOfTransactions;
	private final int initialBalance=1000;
	
	
	
	/* main constructor for Account Object */
	public Account(int idNumber){
		currentBalance=initialBalance;
		numOfTransactions=0;
		this.idNumber=idNumber;
	}
	
	
	
	/* synchronized method for increasing balance of the account */
	public synchronized void increaseBalance(int amount){
		currentBalance+=amount;
		numOfTransactions++;
	}
	
	
	/* synchronized method for decreasing balance of the account */
	public synchronized void decreaseBalance(int amount){
		currentBalance-=amount;
		numOfTransactions++;
	}
	
	/* method which returns the current balance of the account */
	public int getbalance(){
		return currentBalance;
	}
	
	/* returns number of transactions commited on this account */
	public int getTransactions(){
		return numOfTransactions;
	}
	
	/* Overriden toString method which returns index of the account, balance and number of transactions */
	@Override
	public String toString(){
		return "acct:"+idNumber+" "+"bal:"+getbalance()+" trans:"+getTransactions();
	}
	
	
}
