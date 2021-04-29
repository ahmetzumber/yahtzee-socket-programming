package Client;

import Message.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListenThread extends Thread {
    
    Client client;
    
    public ListenThread(Client client){
        this.client = client;
    }
    
    @Override
    public void run() {
        while(this.client.socket.isConnected()){
            try {
                Message msg = (Message)this.client.sInput.readObject();
                switch (msg.type) {
                    case Name:
                        break;
                    case RivalConnected:
                        break;
                    case Disconnect:
                        break;
                    case Text:           
                        break;
                    case Selected:
                        break;
                    case Bitis:
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
   
}
