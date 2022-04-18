package assign3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class MetropolisView extends JFrame {
	private MyTableModel model;
	private JTable table;
	private JPanel panel;
	private JTextField metroTextField;
	private JTextField contTextField;
	private JTextField popTextField;
	private JButton addButton;
	private JButton searchButton;
	private JPanel horizbox;
	private JPanel vertbox;
	private JComboBox<String> searchpop;
	private JComboBox<String> searchMatch;
	private JScrollPane spane;
	
	
	
	
	
	/* adds three textFields in the north part opf the panel */
	private void initializeTextFields(){
		horizbox=new JPanel();
		horizbox.setLayout(new FlowLayout());
		JLabel metro=new JLabel("Metropolis");
		JLabel cont=new JLabel("Continent");
		JLabel pop=new JLabel("Population");
		metroTextField=new JTextField(10);
		contTextField=new JTextField(10);
		popTextField=new JTextField(10);
		horizbox.add(metro);
		horizbox.add(metroTextField);
		horizbox.add(cont);
		horizbox.add(contTextField);
		horizbox.add(pop);
		horizbox.add(popTextField);
	}
	
	
	
	/* adds buttons for search and add functions and adds listeners on them */
	private void initializeButtons(){
		vertbox=new JPanel();
		vertbox.setLayout(new BoxLayout(vertbox, BoxLayout.Y_AXIS));
		addButton=new JButton("Add");
		searchButton=new JButton("Search");
		vertbox.add(addButton);
		vertbox.add(searchButton);
		addListenerToAdd();
		addListenerToSearch();
	}
	
	
	/* adds listener to the add button */
	private void addListenerToAdd(){
		addButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				model.add(metroTextField.getText(), contTextField.getText(), popTextField.getText());
			}
			
		});
	}
	
	/* adds listener to search button */
	private void addListenerToSearch(){
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean exactMatch=false;
				boolean greaterThan=false;
				if(searchMatch.getSelectedItem().equals("Exact Match")){
					exactMatch=true;
				}
				if(searchpop.getSelectedItem().equals("Population larger than")){
					greaterThan=true;
				}
				model.search(metroTextField.getText(), contTextField.getText(), popTextField.getText(), exactMatch, greaterThan);
			}
		});
	}
	
	
	/* adds two combo boxes, one for searching by population number and the second for exact or partial matching */
	private void initializeComboBoxes(){
		searchpop=new JComboBox<String>();
		searchpop.addItem("Population larger than");
		searchpop.addItem("Population smaller than equal");
		searchMatch=new JComboBox<String>();
		searchMatch.addItem("Exact Match");
		searchMatch.addItem("Partial Match");
		JPanel searchoptions=new JPanel();
		searchoptions.setLayout(new BoxLayout(searchoptions, BoxLayout.Y_AXIS));
		searchoptions.add(searchpop);
		searchoptions.add(searchMatch);
		searchoptions.setBorder(new TitledBorder("Search Options"));
		vertbox.add(searchoptions);
	}
	
	
	
	/* initializes all the components of the panel */
	private void initializePanel(){
		panel=new JPanel();
		panel.setLayout(new BorderLayout(4, 4));
		initializeTextFields();
		initializeButtons();
		initializeComboBoxes();
		panel.add(horizbox, BorderLayout.NORTH);
		panel.add(vertbox, BorderLayout.EAST);
		add(panel);
		pack();
	}
	
	
	
	/*constructor for MetropolisView class */
	public MetropolisView(MyTableModel m){
		model=m;
		table=new JTable(m);
		spane=new JScrollPane(table);
		initializePanel();
		panel.add(spane, BorderLayout.CENTER);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
}
