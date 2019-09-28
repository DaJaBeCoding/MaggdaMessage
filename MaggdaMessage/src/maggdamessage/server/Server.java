/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdamessage.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import maggdamessage.MaggdaMessage;
import maggdamessage.client.Client;

/**
 *
 * @author DavidPrivat
 */
public class Server extends Thread {

    private ServerSocket serverSocket;
    private HashMap<String, ClientSocket> clientSockets;
    private static int currClientSocketId = Integer.MIN_VALUE;
    private boolean run;
    
    private static boolean isLocalClient;
    private Client client;

    public Server() throws BindException, IOException {
        isLocalClient = false;
        System.out.println("[Server] Server starting...");
        run = true;

        serverSocket = new ServerSocket(MaggdaMessage.port);

        clientSockets = new HashMap<>();
        System.out.println("[Server] Server started.");

    }

    @Override
    public void run() {
        System.out.println("[Server] Server beginning loop...");
        while (run) {
            try {
                Socket socket = serverSocket.accept();
                ClientSocket clientSocket = new ClientSocket(socket);
                new Thread(clientSocket).start();
                String socketId = getNextClientSocketId();
                clientSockets.put(socketId, clientSocket);
                System.out.println("[Server] Added new Socket successfully! Port: " + MaggdaMessage.port + ", id: " + socketId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void setClient(Client c) {
        if(c == null) {
            isLocalClient = false;
        } else {
            isLocalClient = true;
        }
        this.client = c;
    }

    public static synchronized String getNextClientSocketId() {
        currClientSocketId++;
        return Integer.toString(currClientSocketId);
    }
    
    public static boolean isLocalClient() {
        return isLocalClient;
    }
}
