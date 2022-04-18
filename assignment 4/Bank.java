import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Bank {
	
	private BlockingQueue transactions;
	private ArrayList<Account> accounts;
	private int numOfThreads;
	private final int numOfAccounts=20;
	private Thread[] Workers;
	private final int sizeOfQueue=10;
	private CountDownLatch ct;
	private final Transaction nulltrans=new Transaction(-1, 0, 0);
	
	
	
	/* initializes all the accounts indexed from 0 to 19 */
	private void initializeAccounts(){
		accounts=new ArrayList<Account>();
		for(int i=0; i<numOfAccounts; i++){
			Account acc=new Account(i);
			accounts.add(acc);
		}
	}
	
	
	/* main constructor for Bank object, initializing accounts, workers and doing transactions */
	public Bank(String fileName, int numOfThreads){
		transactions=new ArrayBlockingQueue(sizeOfQueue);
		this.numOfThreads=numOfThreads;
		ct=new CountDownLatch(numOfThreads);
		initializeAccounts();
		initializeWorkers(numOfThreads);
		processTransactions(fileName);
		try {
			ct.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printAccountInf();
	}
	
	
	
	
	/* prints information of the account, including transaction number, balance, account index */
	private void printAccountInf(){
		for(int i=0; i<numOfAccounts; i++){
			System.out.println(accounts.get(i).toString());
		}
	}
	
	
	
	/* creating and starting up all the worker threads */
	private void initializeWorkers(int numOfWorkers){
		Workers=new Thread[numOfWorkers];
		for(int i=0; i<numOfWorkers; i++){
			Worker w=new Worker();
			Workers[i]=new Thread(w);
		}
		startWorkers();
	}
	
	
	/* starting all the worker threads */
	private void startWorkers(){
		for(int i=0; i<numOfThreads; i++){
			Workers[i].start();
		}
	}
	
	
	
	/* takes and puts the transaction */
	private void putTransaction(String line){
		StringTokenizer st=new StringTokenizer(line);
		int source=Integer.parseInt(st.nextToken());
		int destination=Integer.parseInt(st.nextToken());
		int amount=Integer.parseInt(st.nextToken());
		try {
			transactions.put(new Transaction(source, destination, amount));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/* puts null transactions in the queue */
	private void putNullTrans(){
		for(int i=0; i<numOfThreads; i++){
			try {
				transactions.put(nulltrans);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/* reads transactions file, putting new transactions in the queue */
	private void processTransactions(String fileName){
		BufferedReader rd=null;
		try {
			rd=new BufferedReader(new FileReader(fileName));
			String curLine="";
			while(true){
				curLine=rd.readLine();
				if(curLine==null)break;
				putTransaction(curLine);
			}
			putNullTrans();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	
	
	
	
	
	/* Worker class, which is responsible to take and commit transactions */
	private class Worker implements Runnable{
		
		/* run method for Worker thread */
		public void run() {
			
			while(true){
				try {
					Transaction tr=(Transaction) transactions.take();
					if(isNullTransaction(tr)){
						ct.countDown();
					}
					else{
						accounts.get(tr.getSource()).decreaseBalance(tr.getAmount());
						accounts.get(tr.getDestination()).increaseBalance(tr.getAmount());
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch blocktr.
					e.printStackTrace();
				}
			}
			
		}
		
		/* checks whether a transaction is  a null tranaction */
		private  boolean isNullTransaction(Transaction tr){
			return tr.getSource()==-1;
		}
	}
	
	
	
	/* main method */
	public static void main(String[] args) {
		String fileName=args[0];
		int threadNum=Integer.parseInt(args[1]);
		Bank b=new Bank(fileName, threadNum);
		System.exit(1);
	}

}
