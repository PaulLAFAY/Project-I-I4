package client;

import java.io.IOException;
import java.net.Socket;

import commons.Board;
import commons.Coordonates;
import commons.Stone;
import commons.User;
import tcp.*;

public class GoClient extends TCPHandler {

    private final User user;
    private Board board;

    private final boolean debug;

    public GoClient(Socket socket, String name, final boolean debug) throws IOException {
        super(socket);
        this.debug = debug;
        user = new User(name);
        send(TCPCommand.isConnected());
        board = new Board(this.debug);
    }

    @Override
    protected void newMessage(String message) {
        if (message.startsWith("/"))
            newCommandMessage(message);
        else
            System.out.println(message);
    }

    private void newCommandMessage(String command) {
        if (debug)
            System.out.println("(debug) Server : /sendBoard [...] " + command);
        if (command.equals(TCPCommand.exit()))
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        else if (command.startsWith(TCPCommand.sendBoard())) {
            board.updateBoard(command);
            board.print();
        } else if (command.equals(TCPCommand.yourTurn())) {
            System.out.println("It's your turn !");
            String column;
            String line;
            Coordonates coord;
            do {
                do {
                    System.out.print("Select a column : ");
                    column = AppClient.sc.nextLine();
                    if ((((int) column.charAt(0)) - 64) < 1 || (((int) column.charAt(0)) - 64) > 19)
                        System.out.println("Error : write a letter include between A and S.");
                } while ((((int) column.charAt(0)) - 64) < 1 || (((int) column.charAt(0)) - 64) > 19);
                do {
                    System.out.print("Select a line : ");
                    line = AppClient.sc.nextLine();
                    if (Integer.parseInt(line) < 1 || Integer.parseInt(line) > 19)
                        System.out.println("Error : write a value include between 1 and 19.");
                } while (Integer.parseInt(line) < 1 || Integer.parseInt(line) > 19);
                coord = new Coordonates(column, line);
                if (board.getStone(coord).getColor() != Stone.free)
                    System.out.println("Error : can't play on a stone already on the board.");
            } while (board.getStone(coord).getColor() != Stone.free);
            send(TCPCommand.sendAction(coord));
        } else if (command.startsWith(TCPCommand.asw())) {
            String[] args = TCPCommand.parseCommand(command);
            if (args[1].equals(TCPCommand.isConnected()) && args[2].equals("true")) {
                send(TCPCommand.setUserInfo(user));
                send(TCPCommand.getNbClient());
            } else if (args[1].equals(TCPCommand.getNbClient()) && args[2].equals("2")) {
                send(TCPCommand.startParty());
            } else if (args[1].equals(TCPCommand.startParty()) && args[2].equals("true")) {
                System.out.println("Party is starting");
            }
        }
    }

}
