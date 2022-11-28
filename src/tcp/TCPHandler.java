package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class TCPHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    protected TCPHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        out = new PrintWriter(this.socket.getOutputStream());
    }

    protected void close() throws IOException {
        socket.close();
    }

    public void send(String message) {
        out.println(message);
        out.flush();
    }

    public boolean isClosed() {
        return socket.isClosed();
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                String message = in.readLine();
                if (message != null)
                    newMessage(message);
            } catch (IOException e) {
                try {
                    close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    protected abstract void newMessage(String message);

}
