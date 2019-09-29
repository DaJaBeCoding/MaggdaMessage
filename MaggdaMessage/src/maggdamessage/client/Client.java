/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdamessage.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
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

    private static ClientScene scene;
    private static Parent previousParent;

    @Override
    public void start(Stage primaryStage) {
        previousParent = null;
        MaggdaMessage.setClient(this);
        System.out.println("[Client] Client starting...");

        scene = new ClientScene();

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        System.out.println("[Client] Client started.");
        primaryStage.setOnCloseRequest((WindowEvent e) -> {
            end();
        });

    }

    public void end() {
        System.out.println("[Client] Close request!");
        MaggdaMessage.clientClosing();
        scene.getConnectionPanes().forEach((ConnectionsOverviewPane pane) -> {
            pane.getConnector().closeConnections();
        });

    }

    public void updateConnectionName(String ip, String newName) {
        findConnection(ip).forEach((Connection connection) -> {
            connection.setConnectionName(newName);
        });
    }

    public void removeConnection(String ip) {
        findConnection(ip).forEach((Connection connection) -> {
            connection.deactivate();
        });
    }

    public void addMessage(String ip, String message) {
        Platform.runLater(() -> {
            findConnection(ip).forEach((Connection connection) -> {
                connection.getChat().addMessage(message, false);
            });
        });
    }

    private ObservableList<Connection> findConnection(String ip) {
        ObservableList<Connection> retConnections = FXCollections.observableArrayList();
        scene.getConnectionPanes().forEach((ConnectionsOverviewPane pane) -> {
            pane.getConnector().getConnections().forEach((Connection connection) -> {
                if (connection.getIpAdress().equals(ip)) {
                    retConnections.add(connection);
                }
            });
        });
        return retConnections;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static void setNewRoot(Parent parent) {
        previousParent = scene.getRoot();
        scene.setRoot(parent);
    }

    public static void showPreviousScreen() {
        scene.setRoot(previousParent);
    }

}
