package tetris;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JBrainTetris extends JTetris {
	
	private JCheckBox brainMode;
	private DefaultBrain brain;
	private JPanel little;
	private JSlider adversary;
	private JLabel statusLabel;
	private Brain.Move result;
	private int curCount;
	private JCheckBox animatedFalling;
	
	public JBrainTetris(int a){
		super(a);
		brain=new DefaultBrain();
		curCount=0;
	}
	
	
	
	
	//adds a new panel, consisting of BrainMode play and adversary slider 
	private void addPanelForBrainMode(JPanel panel){
		
		String brainLabel="Brain:";
		panel.add(new JLabel(brainLabel));
		String brainModeActiveLabel="Brain active";
		brainMode = new JCheckBox(brainModeActiveLabel);
		panel.add(brainMode);
		animatedFalling=new JCheckBox("Animate Falling");
		animatedFalling.setSelected(true);
		panel.add(animatedFalling);
		little = new JPanel();
		little.add(new JLabel("Adversary:"));
		adversary = new JSlider(0, 100, 0); 
		adversary.setPreferredSize(new Dimension(100,15));
		little.add(adversary);
		statusLabel=new JLabel("");
		little.add(statusLabel);
		panel.add(little);
	}
	
	
	
	//overridden method of JTetris createControlPanel, which adds Brain mode menu on the panel //
	@Override
	public JComponent createControlPanel() {
		JPanel p=(JPanel)super.createControlPanel();
		
		//Brain mode panel //
		addPanelForBrainMode(p);
		return p;
	}
	
	
	
	//overridden method tick, which is called when the brain mode is on, chooses an optimal coordinates to drop the current piece //
	@Override
	public void tick(int verb) {
		if(brainMode.isSelected()){
			if(verb==DOWN){
				if(curCount!=count){
					board.undo();
					curCount=count;
					result=brain.bestMove(board, currentPiece, HEIGHT, result);
				}
				if(result!= null){
					int xDir=result.x;
					int yDir=result.y;
					Piece resultPiece=result.piece;
					if(xDir>currentX){
						super.tick(RIGHT);
					}
					if(!resultPiece.equals(currentPiece)){
						super.tick(ROTATE);
					}
					if(xDir<currentX){
						super.tick(LEFT);
					}
					if(currentY>yDir && currentX==xDir && currentPiece.equals(resultPiece) && !animatedFalling.isSelected()){
						super.tick(DROP);
					}
				}
			}
		}
		super.tick(verb);
	}
	
	
	
	
	// iterates over the pieces array and returns the worst piece //
	private Piece returnWorstPiece(){
		int pieceNum=0;
		Piece resultPiece=null;
		int largestScore=-2;
		for(int i=0; i<pieces.length; i++){
			Piece curPiece=pieces[i];
			Brain.Move bm=new Brain.Move();
			bm=brain.bestMove(board, curPiece, HEIGHT, bm);
			if(bm==null) {
				pieceNum=(int)(pieces.length * random.nextDouble());
				return pieces[pieceNum];
			}
			int curScore=(int) bm.score;
			if(curScore>largestScore){
				largestScore=curScore;
				resultPiece=curPiece;
			}
			statusLabel.setText("*ok*");
		}
		return resultPiece;
	}
	
	
	
	// returns a piece randomly, based on an adversary value//
	@Override
	public Piece pickNextPiece() {
		int pieceNum=0;
		Random r=new Random();
		int number=r.nextInt(99)+1;
		int adversaryValue=adversary.getValue();
		if(number>=adversaryValue){
			pieceNum = (int) (pieces.length * random.nextDouble());
			statusLabel.setText("ok");
			return pieces[pieceNum];
		}
		else{
			Piece resultPiece=returnWorstPiece();
			return resultPiece;
		}
		
	}
	

	
	// main method which initializes JBrainTetris object //
	public static void main(String[] args) {
		JBrainTetris tetris = new JBrainTetris(16);
		JFrame frame = JBrainTetris.createFrame(tetris);
		frame.setVisible(true);
			
	}

}
