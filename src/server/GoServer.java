package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import tcp.TCPCommand;

public class GoServer {

    private ServerSocket serverSocket;
    private final int port;
    private Socket clientSocket;
    private final int maxClientsCount;
    private ArrayList<GoClientHandler> clientHandlers;

    public GoServer(int port, int maxClientsCount) {
        this.port = port;
        this.maxClientsCount = maxClientsCount;
    }

    public void start() {
        clientHandlers = new ArrayList<GoClientHandler>();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening to port " + port + ".");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                if (clientHandlers.size() < maxClientsCount) {
                    clientHandlers.add(new GoClientHandler(clientSocket, clientHandlers));
                    System.out.println("GoServer : new user | " + clientHandlers.size() + " users connected.");
                    clientHandlers.get(clientHandlers.size() - 1).start();
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
                    out.println("Server is full. Try later.");
                    out.flush();
                    out.println(TCPCommand.exit());
                    out.flush();
                    out.close();
                    clientSocket.close();
                    System.out.println("Someone tried to log in but server is full.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
