
package Server;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SPairingThread extends Thread {
    
    SClient sclient;
    
    public SPairingThread(SClient sclient){
        this.sclient = sclient;
    }

    @Override
    public void run() {
        while(this.sclient.paired == false && this.sclient.socket.isConnected()){
            try {
                Server.pairingSemp.acquire(1);
                SClient selectedPair = null;
                while(selectedPair == null && this.sclient.socket.isConnected()){
                    for (SClient i : Server.sclients) {
                        if (!i.equals(this.sclient) && i.paired == false) {
                            selectedPair = i;
                            this.sclient.rival = selectedPair;
                            selectedPair.rival = this.sclient;
                            selectedPair.paired = true;
                            this.sclient.paired = true;
                            break;
                        }
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(SPairingThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
