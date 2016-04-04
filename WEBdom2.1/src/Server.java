

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class Server {

	public static final int PORT = 8988;
	public static Vector<String> users = new Vector<>();

	public static void main(String[] args) {
		try {
			int clientNumber = 0;
			ServerSocket ss = new ServerSocket(PORT);
			System.out.println("Server is running...");

			while(true){
				Socket client = ss.accept();
				System.out.println("Accepted client number "+(++clientNumber));
				ServerThread st = new ServerThread(client, clientNumber);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
