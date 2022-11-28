package server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import commons.Coordonates;
import commons.User;
import tcp.*;

public class GoClientHandler extends TCPHandler {

    private ArrayList<GoClientHandler> clientHandlers;
    private User user;
    private GoParty party;

    public GoClientHandler(Socket clientSocket, ArrayList<GoClientHandler> clientHandlers) throws IOException {
        super(clientSocket);
        this.clientHandlers = clientHandlers;
    }

    @Override
    protected void newMessage(String message) {
        if (message.startsWith("/"))
            newCommandMessage(message);
        else
            broadcastMessage(message);
    }

    private void newCommandMessage(String command) {
        if (user != null)
            System.out.println(user.getName() + "#" + user.getID() + " : " + command);
        else
            System.out.println("Thread#" + this.getId() + " : " + command);
        if (command.equals(TCPCommand.exit())) {
            try {
                close();
                clientHandlers.remove(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (command.equals(TCPCommand.isConnected())) {
            send(TCPCommand.asw(TCPCommand.isConnected(true)));
        } else if (command.startsWith(TCPCommand.setUserInfo())) {
            String[] args = TCPCommand.parseCommand(command);
            user = new User(args[1]);
            send("Server : Welcom " + user.getName() + " !");
            broadcastMessage("Hello !");
        } else if (command.equals(TCPCommand.getNbClient())) {
            send(TCPCommand.asw(TCPCommand.getNbClient(clientHandlers.size())));
        } else if (command.equals(TCPCommand.startParty())) {
            new GoParty(clientHandlers);
            broadcastCommand(TCPCommand.asw(TCPCommand.startParty(true)));
            System.out.println("Board broadcasted.");
            broadcastCommand(TCPCommand.sendBoard(party.getBoard()));
            System.out.println("Board : \n" + party.getBoard());
            party.getBoard().print();
            party.nextTurn(this);
        } else if (command.startsWith(TCPCommand.sendAction())) {
            newAction(command);
        }
    }

    public void broadcastMessage(String message) {
        for (GoClientHandler clientHandler : clientHandlers) {
            if (clientHandler != this)
                clientHandler.send(user.getName() + "#" + user.getID() + " : " + message);
        }
    }

    public void broadcastCommand(String command) {
        for (GoClientHandler clientHandler : clientHandlers)
            clientHandler.send(command);
    }

    public void broadcastCommand(String command, GoClientHandler ignoredClientHandler) {
        for (GoClientHandler clientHandler : clientHandlers)
            if (clientHandler != ignoredClientHandler)
                clientHandler.send(command);
    }

    public void setParty(GoParty party) {
        this.party = party;
        System.out.println(user.getName() + "#" + user.getID() + " join a party.");
    }

    public User getUser() {
        return user;
    }

    public void newAction(String command) {
        String[] arg = command.split(Coordonates.COORD_SEPARATOR);
        party.getBoard().setStone(new Coordonates(arg[1], arg[2]), party.getTurn());
        party.getBoard().parseBoard();
        System.out.println("Board broadcasted.");
        broadcastCommand(TCPCommand.sendBoard(party.getBoard()));
        System.out.println("Board : \n" + party.getBoard());
        party.getBoard().print();
        party.nextTurn(this);
    }

}
