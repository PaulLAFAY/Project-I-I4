package server;

import java.util.ArrayList;

public class ConnectionChecker extends Thread {

    private ArrayList<GoClientHandler> clientHandlers;

    public ConnectionChecker(ArrayList<GoClientHandler> clientHandlers) {
        this.clientHandlers = clientHandlers;
    }

    @Override
    public void run() {
        for (GoClientHandler clientHandler : clientHandlers) {
            if (clientHandler.isClosed())
                clientHandlers.remove(clientHandler);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

// TODO refaire ça pour que ça marche

// TODO Conseil d'Eddie : envoyer un alive et le client renvoie un flag. Si pas
// de flag recu pendant 3 sec, kill le thread