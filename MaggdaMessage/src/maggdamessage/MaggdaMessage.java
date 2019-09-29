/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdamessage;

import java.io.IOException;
import java.net.BindException;
import maggdamessage.client.Client;
import maggdamessage.server.Server;

/**
 *
 * @author DavidPrivat
 * 
 * update test 1
 */
public class MaggdaMessage {

    private static Server server;
    private static Client client;
    public final static int port = 42666;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            server = new Server();
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
                

        client = null;
        Client.main(null);

    }

    public static void setClient(Client c) {
        client = c;
        server.setClient(c);
        System.out.println("[STATIC] Set Client.");
    }

    public static Server getServer() {
        return server;
    }

    public static Client getClient() {
        return client;
    }
    
    public static void clientClosing() {
        server.end();
    }

}
