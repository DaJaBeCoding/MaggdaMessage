/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdamessage.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author DavidPrivat
 */
public class ClientSocket implements Runnable {

    private DataOutputStream output;
    private DataInputStream input;
    private CommandsHandler commandsHandler;
    private Socket socket;

    public ClientSocket(Socket s) throws IOException {
        System.out.println("[Server] Connection detected!");
        socket = s;

        commandsHandler = new CommandsHandler(socket); //       
       
    }
   

    @Override
    public void run() {
        try {
            commandsHandler.run();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
