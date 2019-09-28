/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdamessage;

/**
 *
 * @author DavidPrivat
 */
public class Command {

    public final static String keyWord = "MAGGDA";
    public final static String firstLayerSeparator = "\u2A59";
    public final static String secondLayerSeparator = "\u2750";

    public enum CommandType {

        PING, // args: 1) username
        PONG;   // args: 1) username
    }

    private String[] args;
    private CommandType commandType;

    public Command(CommandType type, String[] args) {
        this.args = args;
        this.commandType = type;
    }

    public Command(String s) throws IllegalArgumentException, WrongKeyWordException {
        String[] content = s.split(firstLayerSeparator);
        if (content[0].equals(keyWord)) {
            commandType = CommandType.valueOf(content[1]);
            String arguments;
            if (content.length >= 3) {
                arguments = content[2];
            } else {
                arguments = "";
            }
            if (arguments.contains(secondLayerSeparator)) {
                args = arguments.split(secondLayerSeparator);
            } else {
                args = new String[]{arguments};
            }

        } else {
            throw new WrongKeyWordException(s);
        }
    }

    @Override
    public String toString() {
        String returnString = "";
        returnString += keyWord;
        returnString += firstLayerSeparator;
        returnString += commandType.name();
        returnString += firstLayerSeparator;
        for (String s : args) {
            returnString += s;
            returnString += secondLayerSeparator;
        }
        return returnString;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public class WrongKeyWordException extends Exception {

        public WrongKeyWordException(String s) {
            super("Message with wrong keyWord received: " + s);
        }
    };

    public String[] getArgs() {
        return args;
    }
}
