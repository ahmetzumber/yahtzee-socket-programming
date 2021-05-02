package Message;

public class Message implements java.io.Serializable {

    //mesaj tipleri enum 
    public static enum Message_Type {
        Name, RivalConnected, CHANGE, GameControl, FINISH
    }

    //mesajın tipi
    public Message_Type type;

    //mesajın içeriği obje tipinde ki istenilen tip içerik yüklenebilsin
    public Object content;

    public Message(Message_Type t) {
        this.type = t;
    }

}
