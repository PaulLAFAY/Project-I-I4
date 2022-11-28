package tcp;

import commons.Board;
import commons.Coordonates;
import commons.User;

public class TCPCommand {

    public static String[] parseCommand(String command) {
        String[] parsedString = command.split(" ");
        return parsedString;
    }

    public static String asw() {
        return "/asw";
    }

    public static String asw(String str) {
        return "/asw " + str;
    }

    public static String exit() {
        return "/exit";
    }

    public static String isConnected() {
        return "/isConnected";
    }

    public static String isConnected(boolean bool) {
        return "/isConnected " + bool;
    }

    public static String setUserInfo() {
        return "/setUserInfo";
    }

    public static String setUserInfo(User user) {
        return "/setUserInfo " + user.getName();
    }

    public static String getNbClient() {
        return "/getNbClient";
    }

    public static String getNbClient(int nbClient) {
        return "/getNbClient " + nbClient;
    }

    public static String startParty() {
        return "/startParty";
    }

    public static String startParty(boolean bool) {
        return "/startParty " + bool;
    }

    public static String sendBoard() {
        return "/sendBoard";
    }

    public static String sendBoard(Board board) {
        return "/sendBoard" + Board.SEPARATOR_2 + board;
    }

    public static String yourTurn() {
        return "/yourTurn";
    }

    public static String sendAction() {
        return "/sendAction";
    }

    public static String sendAction(Coordonates coord) {
        return "/sendAction" + Coordonates.COORD_SEPARATOR + coord;
    }

}
