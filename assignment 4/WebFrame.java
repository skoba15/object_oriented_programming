import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.StringTokenizer;
import java.util.concurrent.Semaphore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WebFrame extends JFrame {
	
	
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scrollpane;
	private JPanel panel;
	private JButton singleThread;
	private JButton concurrentThread;
	private JTextField limit;
	private JLabel running;
	private JLabel completed;
	private JLabel elapsed;
	private int numOfRunning;
	private int completedThreads;
	private double elapsedTime; 
	private JProgressBar progressbar;
	private int barMinimum;
	private int barMaximum;
	private JButton stopButton;
	private int limitOfThreads;
	private Launcher launcher;
	private static WebFrame frame;
	private Semaphore stopLock;
	private boolean resetted=false;
	
	
	
	/* reads the file and updates JTable */
	private void readFile(String fileName){
		BufferedReader rd=null;
		try {
			rd=new BufferedReader(new FileReader(fileName));
			String curLine="";
			while(true){
				curLine=rd.readLine();
				if(curLine==null)break;
				model.addRow(new Object[]{curLine, ""});
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	/* adds listeners to the buttons of thread modes */
	private void addModeListeners(){

		singleThread.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				limitOfThreads=1;
				singleThread.setEnabled(false);
				concurrentThread.setEnabled(false);
				stopButton.setEnabled(true);
				launcher=new Launcher();
				launcher.start();
			}
			
		});
		
		concurrentThread.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				limitOfThreads=Integer.parseInt(frame.limit.getText());
				singleThread.setEnabled(false);
				concurrentThread.setEnabled(false);
				stopButton.setEnabled(true);
				launcher=new Launcher();
				launcher.start();
			}
			
		});
		
	}
	
	
	
	/* initializes buttons for Thread modes */
	private void initializeThreadModes(){
		singleThread=new JButton("Single Thread Fetch");
		concurrentThread=new JButton("Concurrent Thread Fetch");
		addModeListeners();
	}
	
	
	
	/* initializes progressbar */
	private void initializeProgressBar(){
		barMinimum=0;
		barMaximum=model.getRowCount();
		progressbar=new JProgressBar();
		progressbar.setMinimum(barMinimum);
		progressbar.setMaximum(barMaximum);
	}
	
	
	
	/* adds listener for stop button */
	private void addStopListener(){
		stopButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				lockStopButton();
				launcher.interrupt();
				launcher.InterruptAll();
				unlockStopButton();
				stopButton.setEnabled(false);
				singleThread.setEnabled(true);
				concurrentThread.setEnabled(true);
				elapsed.setText("elapsed: "+elapsedTime);
				
			}
			
		});
	}
	
	
	
	/* initializes stop button */
	private void initializeStop(){
		stopButton=new JButton("Stop");
		stopButton.setEnabled(false);
		addStopListener();
	}
	
	
	
	/* adds components showing numbe rof running threads, completed runs and elapsed time */
	private void initializeStatisticalInf(){
		numOfRunning=0;
		completedThreads=0;
		elapsedTime=0;
		running=new JLabel("Running: "+numOfRunning);
		completed=new JLabel("Completed: "+completedThreads);
		elapsed=new JLabel("Elapsed: "+elapsedTime);
	}
	
	
	
	/* initializes Jtable */
	private void initializeTable(){
		model=new DefaultTableModel(new String[]{"url", "status"}, 0);
		table=new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollpane=new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(600, 300));
	}
	
	
	/* initializes textfield for choosing Limit of available Threads running at the same time */
	private void initializeThreadNumChoice(){
		limit=new JTextField();
		limit.setMaximumSize(new Dimension(100, 40));
	}
	
	
	
	/* initializes panel, adding components to it */
	private void initializePanel(){
		panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(scrollpane);
		panel.add(singleThread);
		panel.add(concurrentThread);
		panel.add(limit);
		panel.add(running);
		panel.add(completed);
		panel.add(elapsed);
		panel.add(progressbar);
		panel.add(stopButton);
	}
	
	
	
	/* constructor for WebFrame object */
	public WebFrame(String filename){
		stopLock=new Semaphore(1);
		resetted=false;
		initializeTable();
		readFile(filename);
		initializeThreadModes();
		initializeThreadNumChoice();
		initializeStatisticalInf();
		initializeProgressBar();
		initializeStop();
		initializePanel();
		add(panel);
		setVisible(true);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	/* returns limit of thread numbers */
	public int limitOfThreads(){
		return limitOfThreads;
	}
	
	/* returns the number of rows of the JTable */
	public int tableRows(){
		return model.getRowCount();
	}
	
	/* changes the status in the table on the given row */
	public void change(int row, String s){
		model.setValueAt(s, row, 1);
	}
	
	/*decreases the number of running threads */
	public synchronized void  decreaseRunning(){
		numOfRunning--;
		SwingUtilities.invokeLater(new Runnable(){

			public void run() {
				running.setText("Running: "+numOfRunning);
			}
			
		});
	}
	
	/* increases the number of running threads, updating the label */
	public synchronized void increaseRunning(){
		numOfRunning++;
		SwingUtilities.invokeLater(new Runnable(){

			public void run() {
				running.setText("Running: "+numOfRunning);
			}
			
		});
	}
	
	/* increases the number of completed threads by one and updates the label */
	public synchronized void increaseCompleted(){
		completedThreads++;
		SwingUtilities.invokeLater(new Runnable(){

			public void run() {
				completed.setText("Completed: "+completedThreads);
			}
			
		});
	}
	
	/* returns the url written on the row number row in the table */
	public String getUrl(int row){
		return (String) model.getValueAt(row, 0);
	}
	
	/* increases the elapsed time */
	public synchronized void increaseElapsedTime(double elapsed){
		elapsedTime+=elapsed;
	}
	
	/* checks whether all threads ended running */
	public synchronized void checkend(){
		if(numOfRunning==0 && !resetted){
			resetted=true;
			reset();
		}
	}
	
	
	/* changes the progressbar, increases by one */
	public synchronized void changeProgressBar(){
		progressbar.setValue(progressbar.getValue()+1);
	}
	
	
	/* resets everything after running threads gets 0, it's in a ready state */
	public synchronized void reset(){
		resetted=false;
		elapsed.setText("elapsed :"+elapsedTime/1000);
		stopLock=new Semaphore(1);
		progressbar.setValue(0);
		stopButton.setEnabled(false);
		singleThread.setEnabled(true);
		concurrentThread.setEnabled(true);
	}
	
	
	/* creates the GUI */
	public static void createAndShowGUI(){
		frame=new WebFrame("/home/shota/workspace/assignment4/bin/links.txt");
	}
	
	
	/* locks the stopButton, in order not to create a thread when the button is clicked */
	public void lockStopButton(){
		try {
			stopLock.acquire();
		
		} catch (InterruptedException e) {
			
		}
	}
	
	
	private class Launcher extends Thread{
		private Semaphore limitNum;
		private WebWorker[] workers;
		private int numOfThreads;
		
		/* main constructor for Launcher */
		public Launcher(){
			limitNum=new Semaphore(frame.limitOfThreads());
			numOfThreads=frame.tableRows();
			workers=new WebWorker[numOfThreads];
			frame.increaseRunning();
		}
		
		/* run method */
		@Override
		public void run(){
			for(int i=0; i<numOfThreads; i++){
				try {
					if(isInterrupted()){
						break;
					}
					limitNum.acquire();
					frame.lockStopButton();
					workers[i]=new WebWorker(frame.getUrl(i), i, frame, limitNum);
					workers[i].start();
					frame.unlockStopButton();
				} catch (InterruptedException e) {
					break;
				}
				
			}
			frame.decreaseRunning();
			checkend();
		}
		
		/* interrupts all the threads */
		public void InterruptAll(){
			for(int i=0; i<workers.length; i++){
				if(workers[i]!=null && workers[i].isAlive()){
					workers[i].interrupt();
				}
			}
		}
	}
	
	
	
	/* unlocks the stop button */
	public void unlockStopButton(){
		stopLock.release();
	}
	
	/* main method */
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				createAndShowGUI();
			}
		});
	}
}