/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdamessage.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import maggdamessage.MaggdaMessage;

/**
 *
 * @author DavidPrivat
 */
public class Client extends Application {
    
    private ClientScene scene;
    @Override
    public void start(Stage primaryStage) {
        MaggdaMessage.setClient(this);
        System.out.println("[Client] Client starting...");

        
        scene = new ClientScene();
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        System.out.println("[Client] Client started.");
        primaryStage.setOnCloseRequest((WindowEvent e)->{
            scene.getConnectionPanes().forEach((ConnectionsOverviewPane pane)->{
                pane.getConnector().closeConnections();
            });
            MaggdaMessage.clientClosing();
        });
        
    }
    
    public void updateConnectionName (String ip, String newName) {
        scene.getConnectionPanes().forEach((ConnectionsOverviewPane pane)->{
            pane.getConnector().getConnections().forEach((Connection connection)->{
                if(connection.getIpAdress().equals(ip)) {
                    connection.setConnectionName(newName);
                }
            });
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
