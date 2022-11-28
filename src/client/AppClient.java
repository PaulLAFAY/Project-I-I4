package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class AppClient {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String args[]) {

        final boolean debug;
        final String name;
        final String host;
        final String sPort;
        final int iPort;

        if (args.length > 1) {
            if (args[0].equals("true"))
                debug = true;
            else
                debug = false;
            name = args[1];
            host = args[2];
            iPort = Integer.parseInt(args[3]);
        } else {
            debug = false;
            System.out.print("Name : ");
            name = sc.nextLine();
            System.out.print("Host : ");
            host = sc.nextLine();
            System.out.print("Port : ");
            sPort = sc.nextLine();
            iPort = Integer.parseInt(sPort);
            System.out.println("Connexion...");
        }

        try {
            GoClient client = new GoClient(new Socket(host, iPort), name, debug);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
