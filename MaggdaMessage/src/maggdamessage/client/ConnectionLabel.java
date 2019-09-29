/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdamessage.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 *
 * @author DavidPrivat
 */
public class ConnectionLabel extends Label{
    public final static double size = 80;
    public final static Font defaultFont = Font.font("courier new", FontWeight.NORMAL, FontPosture.ITALIC, 10);
    public final static Font successFont = Font.font("courier new",  FontWeight.BOLD, FontPosture.REGULAR, 10);
    
    public ConnectionLabel(String name) {
        super(name);
        setHover(true);
        setMinSize(size, size);
        setFont(defaultFont);
       
        setAlignment(Pos.CENTER);
        setWrapText(true);
        
        setSuccessfull(false);
    }
    
    public void setSuccessfull (boolean isSuccess) {
        if(isSuccess) {
            setFont(successFont);
            setTextFill(Color.GREEN);
            setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, new Insets(2))));
            
            setOnMousePressed((MouseEvent e)->{
                setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, new Insets(-4, -4, -4, -4))));
            });
            
            setOnMouseReleased((MouseEvent e)->{
                setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, new Insets(-4, -4, -4, -4)), new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, new Insets(-2, -2, -2, -2))));
            });
            
            setOnMouseEntered((MouseEvent e)->{
                toFront();
                setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, new Insets(-4, -4, -4, -4)), new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, new Insets(-2, -2, -2, -2))));
            });
            setOnMouseExited((MouseEvent e)->{
                setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, new Insets(2))));
            });
           
            
        } else {
            setFont(defaultFont);
            setTextFill(Color.GRAY);
            setBackground(new Background(new BackgroundFill(Color.SILVER, CornerRadii.EMPTY, new Insets(0)), new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, new Insets(2))));
            
            setOnMousePressed(null);
            setOnMouseReleased(null);
            setOnMouseEntered(null);
            setOnMouseExited(null);
            setOnMouseClicked(null);
        }
    }
    
}
