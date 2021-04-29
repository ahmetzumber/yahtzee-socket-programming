
package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    
    public Socket socket;
    public ObjectInputStream sInput;
    public ObjectOutputStream sOutput;
    
    public Client(String ip, int port){
        try {
            this.socket = new Socket(ip,port);
            this.sInput = new ObjectInputStream(socket.getInputStream());
            this.sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
