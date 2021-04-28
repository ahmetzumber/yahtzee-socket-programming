package Server;

import Message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SClient {
    
    Socket socket;
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;
    
    // dinleme threadi
    SCListenThread scListenThread;
    
    //rakip client
    SClient rival;
    
    //eşleşme durumu
    public boolean paired = false;
    
    public SClient(Socket socket){

        try {
            this.socket = socket;
            this.scListenThread = new SCListenThread(this);
            this.sOutput = new ObjectOutputStream(this.socket.getOutputStream());
            this.sInput = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     
    public void listen(){
        this.scListenThread.start();
    }
    
    //client mesaj gönderme
    public void Send(Message message) {
        try {
            this.sOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
