/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdamessage.client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author DavidPrivat
 */
public class Message extends TextFlow {

    public static final Font textFont = Font.font(20);
    public static final Font timeFont = Font.font(11);
    public static final Font writerFont = Font.font(11);
    private Text writer, time, text;
    private static final double youOtherInsetsDifference = 100;

    public Message(String partner, String text, boolean isOwn) {
        this.text = new Text(text);
        this.text.setFont(textFont);
        
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        time = new Text(sdf.format(cal.getTime()));
        time.setFont(timeFont);
        
        
        writer = new Text();
        writer.setFont(writerFont);
        
        getChildren().addAll(writer, new Text(System.lineSeparator()), this.text, new Text(System.lineSeparator()), time);
        if(isOwn) {
            writer.setText("Du:");
            setPadding(new Insets(10, 10, 10, 10));
            setBackground(new Background(new BackgroundFill(Color.BISQUE, new CornerRadii(20), Insets.EMPTY), new BackgroundFill(Color.ANTIQUEWHITE, new CornerRadii(20), new Insets(2))));
        } else {
            writer.setText(partner + ":");
            setPadding(new Insets(10, 10, 10, 10 + youOtherInsetsDifference));
            setBackground(new Background(new BackgroundFill(Color.BISQUE, new CornerRadii(20), new Insets(0,0,0,youOtherInsetsDifference)), new BackgroundFill(Color.CORNSILK, new CornerRadii(20), new Insets(2,2,2,2+youOtherInsetsDifference))));
        }
        
    }

}
