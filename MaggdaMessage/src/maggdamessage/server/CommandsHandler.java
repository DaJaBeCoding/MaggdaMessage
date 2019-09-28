/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdamessage.server;

import java.io.BufferedInputStream;
import maggdamessage.Command;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author DavidPrivat
 */
public class CommandsHandler {

    private DataInputStream input;
    private DataOutputStream output;
    private String connectedClientIp;
    private boolean running;

    public CommandsHandler(Socket socket) throws IOException {
        output = new DataOutputStream(socket.getOutputStream());
        input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        connectedClientIp = socket.getInetAddress().getHostAddress();
        System.out.println("[Server] Connected ip: " + connectedClientIp);
        running = true;
        System.out.println("[Server] CommandsHandler generated.");
    }

    public void run() throws IOException {
        System.out.println("[Server] CommandHandler started.");
        String line = "";
        Command currCommand;
        System.out.println("[Server] CommandHandler now waiting...");
        while (((line = input.readUTF()) != null) && (running)) {
            System.out.println("[Server] Command received: " + line);
            try {
                currCommand = new Command(line);
                switch (currCommand.getCommandType()) {
                    case PING:
                        handlePING(currCommand);
                        break;
                }

            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
        System.out.println("[Server] CommandHandler closed.");

    }

    private void handlePING(Command pingCommand) throws IOException {
        sendCommand(new Command(Command.CommandType.PONG, new String[]{System.getProperty("user.name")}));
        if (Server.isLocalClient()) {
            maggdamessage.MaggdaMessage.getClient().updateConnectionName(connectedClientIp, pingCommand.getArgs()[0]);
        }
    }

    public void sendCommand(Command c) throws IOException {
        String commandAsString = c.toString();
        output.writeUTF(commandAsString);
        System.out.println("[Server] Command sent: " + commandAsString);

    }
}
