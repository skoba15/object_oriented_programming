
public class Transaction {
	
	private final int source;
	private final int destination;
	private final int amount;
	
	
	/* main constructor for Transaction object */
	public Transaction(int source, int destination, int amount){
		this.source=source;
		this.destination=destination;
		this.amount=amount;
	}
	
	/* returns the index of the account from where we withdraw the money */
	public int getSource(){
		return source;
	}
	
	
	/* returns the index of the account where we wish to transfer money */
	public int getDestination(){
		return destination;
	}
	
	
	/* returns the amount of transfer from one to another account */
	public int getAmount(){
		return amount;
	}
	

}
