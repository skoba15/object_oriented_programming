import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

public class JCount extends JPanel {
	
	private JLabel label;
	private JTextField field;
	private JButton startButton;
	private JButton stopButton;
	private WorkerThread worker;
	private final int DefaultNumber=100000000;
	private final int IntervalTochange=10000;
	private final int sleepTime=100;
	private static final int tableWidth=200;
	private static final int tableHeight=400;
	
	
	/* adds components on the GUI */
	private void addComponents(){
		label=new JLabel("0");
		field=new JTextField(""+DefaultNumber, 10);
		startButton=new JButton("Start");
		stopButton=new JButton("Stop");
		this.add(field);
		this.add(label);
		this.add(startButton);
		this.add(stopButton);
	}
	
	
	
	
	/* starts the worker thread */
	private void startThread(){
		worker=new WorkerThread();
		worker.start();
	}
	
	
	/* adds listeners to start and stop buttons */
	private void addListeners(){
		startButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(worker==null){
					startThread();
				}
				else{
					worker.interrupt();
					startThread();
				}
			}
	
		});
		;
		stopButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(worker!=null && worker.isAlive()){
					worker.interrupt();
				}
			}
			
		});
	}
	
	
	/*main constructor for JCount */
	public JCount(){
		addComponents();
		addListeners();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
	}
	
	
	
	/* gets the integer from the field as a string */
	public String getTextFromTextField(){
		return field.getText();
	}
	
	
	/* creates the GUI components, four panels */
	private static void createAndShowGUI(JCount[] counts) {
		JFrame frame=new JFrame();
		
		counts=new JCount[4];
		for(int i=0; i<4; i++){
			counts[i]=new JCount();
		}
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		for(int i=0; i<4; i++){
			frame.add(counts[i]);
			frame.add(Box.createRigidArea(new Dimension(0, 40)));
		}	
		frame.pack();
	}
	
	
	
	/* worker thread which runs for each panel */
	public class WorkerThread extends Thread{
		private int counter;
		private int destinationNumber;
		
		
		/*constructor of WorkerThread */
		public WorkerThread(){
			destinationNumber=Integer.parseInt(JCount.this.getTextFromTextField());
			counter=0;
		}
		
		
		/*updates the label */
		private void updateLabel(){
			final int cnt=counter;
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					label.setText(""+cnt);
				}
				
			});
		}
		
		
		/* run method for the given class */
		public void run() {
			while(true){
				counter++;
				if(counter==destinationNumber){
					if(counter%IntervalTochange==0){
						updateLabel();
					}
					break;
				}
				if(isInterrupted()){
					break;
				}
				
				if(counter%IntervalTochange==0){
					try {
						Thread.sleep(sleepTime);
						updateLabel();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						break;
					}
				}
			}
		}
		
	}
	
	
	
	
	/*main method, creating the  */
	public static void main(String[] args) {
		final JCount[] counts=new JCount[4];
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI(counts);
			}
		});
	}
}
