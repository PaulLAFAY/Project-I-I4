package server;

public class AppServer {

    public static void main(String args[]) {

        final int maxClientsCount = 2;
        final int port;
        if (args.length < 1)
            port = 2000;
        else
            port = Integer.valueOf(args[0]).intValue();

        GoServer server = new GoServer(port, maxClientsCount);
        server.start();

    }

}
