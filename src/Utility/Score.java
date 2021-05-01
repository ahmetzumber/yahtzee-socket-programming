package Utility;

import Message.ScoreMessage.Scores;
import javax.swing.JButton;
/**
 *
 * @author ahmet.zumberoglu
 */
public class Score {
    
    Scores score_type;
    JButton button;
    public boolean isButtonChoosen = false;
    
    public Score(Scores score_type, JButton button){
        this.score_type = score_type;
        this.button = button; 
    }
        
    public Scores getScore_type() {
        return score_type;
    }

    public void setScore_type(Scores score_type) {
        this.score_type = score_type;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }
    
    
}
