

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {
		
		try {
			InetAddress addr = InetAddress.getByName("127.0.0.1");
			Socket sock = new Socket(addr, Server.PORT);

            BufferedReader srvin = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            BufferedReader cmdin = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter srvout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())),true);

			while(true) {
                String command = cmdin.readLine();
                if (command.length() == 0) {
                    srvout.println("QUIT");
                    break;
                }
				srvout.println(command);
				String response = srvin.readLine();
				System.out.println("[Server]:" + response);
			}

            cmdin.close();
            srvin.close();
			srvout.close();
			sock.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
