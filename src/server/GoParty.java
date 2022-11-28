package server;

import java.util.ArrayList;

import commons.Board;
import tcp.TCPCommand;

public class GoParty {

    private Board board;
    private int turn; // 0 = blanc 1 = noir

    public GoParty(ArrayList<GoClientHandler> clientHandlers) {
        for (GoClientHandler clientHandler : clientHandlers) {
            clientHandler.setParty(this);
        }
        board = new Board(true);
        turn = 0;
    }

    public Board getBoard() {
        return board;
    }

    public void nextTurn(GoClientHandler clientHandler) {
        turn++;
        clientHandler.broadcastCommand(TCPCommand.yourTurn(), clientHandler);
        clientHandler.send("Waiting for other player's action.");
    }

    public int getTurn() {
        return turn;
    }

}
