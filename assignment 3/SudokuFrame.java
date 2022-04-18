package assign3;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.text.Document;

import java.awt.*;
import java.awt.event.*;




 public class SudokuFrame extends JFrame {
	
	private JPanel panel;
	private JPanel panelBox; 
	private JButton check;
	private JCheckBox autoCheck;
	private JTextArea source;
	private JTextArea result;
	private Document document;
	private DocumentListener ds;
	
	
	
	
	/* adds main components on the frame , like textareas, sets borderlayout */
	public void initializeMainPanel(){
		panel=new JPanel();
		panel.setLayout(new BorderLayout(4, 4));
		source=new JTextArea(15, 20);
		source.setBorder(new TitledBorder("Puzzle"));
		panel.add(source, BorderLayout.CENTER);
		result=new JTextArea(15, 20);
		result.setBorder(new TitledBorder("Solution"));
		panel.add(result, BorderLayout.EAST);
	}
	
	
	
	/* solves the current sudoku table, showing the result on the solutions textarea */
	private void getSudokuResult(){
		try{
			Sudoku s=new Sudoku(source.getText());
			int solutions=s.solve();
			String solution=s.getSolutionText();
			long elapsedTime=s.getElapsed();
			result.setText(solution+"\n"+"solutions: "+solutions+"\n"+"elapsed: "+elapsedTime+"ms");
		}
		catch(Exception ee){
			String exception="Parsing Problem";
			result.setText(exception);
		}
	}
	
	
	/* adds document listener to the document varable */
	private void setDocumentListener(){
		document.addDocumentListener(ds);
	}
	
	
	
	/* initializes doclistener for the source textarea */
	private void initializeDocListener(){
		document=source.getDocument();
		ds=new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				getSudokuResult();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				getSudokuResult();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				getSudokuResult();
			}
		};
	}
	
	
	
	/* sets autocheck on, letting us check whether the current table is a valid Sudoku table or not */
	private void initializeAutoCheck(){
		autoCheck=new JCheckBox("Auto Check");
		autoCheck.setSelected(true);
		setDocumentListener();
		autoCheck.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(autoCheck.isSelected()){
					setDocumentListener();
				}
				else{
					document.removeDocumentListener(ds);
				}
			}
			
		});
		panelBox.add(autoCheck);
	}
	
	
	
	/* initializes check button in the south part and adding actionlistener to it */
	private void initializeCheckButton(){
		check=new JButton("Check");
		check.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				getSudokuResult();
			}
		});
		panelBox.add(check);
	}
	
	
	/* initializes the south part of borderlayout */
	public void initializeSouthPanel(){
		panelBox=new JPanel();
		panelBox.setLayout(new BoxLayout(panelBox, BoxLayout.X_AXIS));
		initializeDocListener();
		initializeCheckButton();
		initializeAutoCheck();
		
	}
	
	
	public SudokuFrame() {
		super("Sudoku solver");
		 setLocationByPlatform(true);
		initializeMainPanel();
		initializeSouthPanel();
		panel.add(panelBox, BorderLayout.SOUTH);
		add(panel);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}

}
