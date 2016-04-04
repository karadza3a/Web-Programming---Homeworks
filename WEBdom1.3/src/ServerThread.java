

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

    private Socket sock;
    private BufferedReader in;
    private PrintWriter out;
    private int value;

    public ServerThread(Socket sock, int value) {

        this.sock = sock;
        this.value = value;

        try {
            //inicijalizacija ulazno/izlaznog sistema
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                //citanje zahteva
                String request = in.readLine();
                if(request == null)
                    break;
                //odgovor
                if (request.charAt(0) == 'A') {
                    synchronized (Server.users) {
                        String[] tokens = request.split(" ");
                        if (tokens.length == 2) {
                            Server.users.add(tokens[1]);
                            out.println("Added " + tokens[1]);
                        } else {
                            out.println("Malformed command");
                        }

                    }
                } else if (request.charAt(0) == 'L') {
                    synchronized (Server.users) {
                        out.println(Server.users.toString());
                    }
                } else if (request.charAt(0) == 'Q') {
                    out.println("Quitting");
                    break;
                } else {
                    out.println("Malformed command");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            in.close();
            out.close();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
