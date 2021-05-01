package Message;

public class Message implements java.io.Serializable {

    //mesaj tipleri enum 
    public static enum Message_Type {
        None, Name, Disconnect, RivalRequest, RivalConnected, ROLL, Start, GameControl, Dice, PNTSELECT
    }

    //mesajın tipi
    public Message_Type type;

    //mesajın içeriği obje tipinde ki istenilen tip içerik yüklenebilsin
    public Object content;

    public Message(Message_Type t) {
        this.type = t;
    }

}
