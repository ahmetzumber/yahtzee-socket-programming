
package Message;

/**
 *
 * @author ahmet.zumberoglu
 */
public class ScoreMessage implements java.io.Serializable {
    
    public Scores score_type;
    public int content;
    
    // score tipleri enum 
    public static enum Scores {
        ONES, TWOS, THREES, FOURS, FIVES, SIXES, THREEKIND, FOURKIND, FULLHOUSE, SMALLSTR, LARGESTR, CHANCE, YAHTZEE
    }
     
    public ScoreMessage(Scores score){
        this.score_type = score;
    }
    
    
}
