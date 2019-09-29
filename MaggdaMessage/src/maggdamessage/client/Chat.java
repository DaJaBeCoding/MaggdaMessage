/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdamessage.client;

import java.io.DataOutputStream;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import maggdamessage.Command;
import maggdamessage.Command.CommandType;

/**
 *
 * @author DavidPrivat
 */
public class Chat extends VBox {

    private final static Font nameHeadingFont = Font.font(35);
    private final static Font textInputFont = Font.font(20);
    private final static Font backBtnFont = Font.font(20);

    private Button backBtn, sendButton;
    private ScrollPane scrollPane;
    private VBox scrollPaneContent;
    private TextField textInput;
    private Label partnerNameLabel;

    private String partnerName;

    private DataOutputStream output;

    public Chat(String name, DataOutputStream outputStream) {
        setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        super.setFillWidth(true);
        super.setPadding(new Insets(20));
        partnerName = name;
        output = outputStream;

        backBtn = new Button("Zurück");
        backBtn.setAlignment(Pos.CENTER_LEFT);
        backBtn.setFont(nameHeadingFont);
        backBtn.setFont(backBtnFont);
        backBtn.setOnAction((ActionEvent e) -> {
            Client.showPreviousScreen();
        });

        partnerNameLabel = new Label(name);
        partnerNameLabel.setAlignment(Pos.CENTER_LEFT);
        partnerNameLabel.setFont(nameHeadingFont);
        HBox headingBox = new HBox(backBtn, partnerNameLabel);
        headingBox.setSpacing(20);

        scrollPaneContent = new VBox();
        scrollPaneContent.setFillWidth(true);
        scrollPaneContent.setSpacing(3);

        scrollPane = new ScrollPane(scrollPaneContent);
        scrollPane.setFitToWidth(true);
        setVgrow(scrollPane, Priority.ALWAYS);
        scrollPane.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY), new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        scrollPaneContent.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane.setVvalue(1.0);
            }
        });

        textInput = new TextField();
        textInput.setPromptText("Deine Nachricht...");
        textInput.setFont(textInputFont);
        sendButton = new Button("Senden");
        sendButton.setFont(textInputFont);

        HBox sendingBox = new HBox(textInput, sendButton);
        sendButton.setAlignment(Pos.CENTER_RIGHT);
        sendingBox.setHgrow(textInput, Priority.ALWAYS);

        getChildren().addAll(headingBox, scrollPane, sendingBox);

        textInput.setOnAction((ActionEvent e) -> {
            sendMessage(textInput.getText());
            textInput.setText("");
        });

        sendButton.setOnAction((ActionEvent e) -> {
            sendMessage(textInput.getText());
            textInput.setText("");

        });
    }

    public void addMessage(String text, boolean isOwn) {
        Message addMessage = new Message(partnerName, text, isOwn);
        scrollPaneContent.getChildren().add(addMessage);
    }

    public void setPartnerName(String name) {
        this.partnerName = name;
        partnerNameLabel.setText(name);
    }

    public void sendMessage(String message) {
        if (message != null && !message.equals("")) {
            try {
                output.writeUTF(new Command(CommandType.MESSAGE, new String[]{message}).toString());
                addMessage(message, true);

            } catch (IOException e) {
                addMessage("error", true);
            }
        }
    }

}
