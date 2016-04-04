//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.Socket;
//
//public class ServerThread extends Thread {
//
//	private Socket client;
//	private BufferedReader in;
//	private PrintWriter out;
//
//	public ServerThread(Socket sock){
//		this.client = sock;
//
//		 try {
//			//inicijalizacija ulaznog sistema
//			in = new BufferedReader(
//				        new InputStreamReader(
//				          client.getInputStream()));
//
//			//inicijalizacija izlaznog sistema
//		    out = new PrintWriter(
//		    	        new BufferedWriter(
//		    	          new OutputStreamWriter(
//		    	            client.getOutputStream())), true);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public void run(){
//		try {
//			String komanda = in.readLine();
//			String response = "";
//
//			response = napraviOdogovor(komanda);
//
//			//ovaj deo nam sluzi samo da bismo ispisali na konzoli servera ceo HTTP zahtev
//			System.out.println("HTTP ZAHTEV KLIJENTA:\n");
//			do{
//				System.out.println(komanda);
//				komanda = in.readLine();
//			} while(!komanda.trim().equals(""));
//
//
//
//
//			//treba odgovoriti browser-u po http protokolu:
//			out.println(response);
//
//			in.close();
//			out.close();
//			client.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private String napraviOdogovor(String komanda){
//		String retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
//		String vrednost = komanda.substring(komanda.indexOf("poljeForme=")+11, komanda.indexOf("HTTP")-1);
//
//		retVal += "<html><head><title>Odgovor servera</title></head>\n";
//		retVal += "<body><h1>Uneta vrednost: "+vrednost+"</h1>\n";
//		retVal += "</body></html>";
//
//		System.out.println("HTTP odgovor:");
//		System.out.println(retVal);
//
//		return retVal;
//	}
//
//}



import java.io.*;
import java.net.Socket;
import java.util.Vector;
import java.util.stream.Collectors;

public class ServerThread extends Thread {

    private Socket sock;
    private BufferedReader in;
    private PrintWriter out;

    public ServerThread(Socket sock, int value) {

        this.sock = sock;

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
        try {
            //citanje zahteva
            String request = in.readLine();

            System.out.println(request);

            if(request == null){
                //
            }else if (request.equalsIgnoreCase("GET /favicon.ico HTTP/1.1")) {
                out.println("");
            }else if (request.equalsIgnoreCase("GET /dodaj.html HTTP/1.1")) {
                serveFile("dodaj.html");
            }else if (request.equalsIgnoreCase("GET /trazi.html HTTP/1.1")) {
                serveFile("trazi.html");
            }else if (request.equalsIgnoreCase("GET /listaj HTTP/1.1")) {
                out.println(napraviOdogovor(Server.users.toString()));
            }else if (request.contains("dodajKorisnika=")) {
                String korisnik = request.substring(request.indexOf("dodajKorisnika=") + 15, request.indexOf("HTTP") - 1);

                if (korisnik.length() > 0) {
                    Server.users.add(korisnik);
                    out.println(napraviOdogovor("Added " + korisnik));
                } else {
                    out.println(napraviOdogovor("Malformed command"));
                }

            } else if (request.contains("traziKorisnika=")) {
                String query = request.substring(request.indexOf("traziKorisnika=") + 15, request.indexOf("HTTP") - 1);

                if (query.length() > 0) {
                    Vector<String> v = Server.users.stream().filter(
                            korisnik -> korisnik.contains(query)
                    ).collect(Collectors.toCollection(Vector::new));

                    out.println(napraviOdogovor("Matching users: " + v.toString()));
                } else {
                    out.println(napraviOdogovor("Malformed command"));
                }

            } else {
                out.println("404 Not found");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            in.close();
            out.close();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String napraviOdogovor(String content){
        String retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";


        retVal += "<html><head><title>Odgovor servera</title></head>\n";

        retVal += "<body>\n";

        retVal += "<ul>\n";
        retVal += "<li><a href='dodaj.html'>Dodaj korisnika</a></li>\n";
        retVal += "<li><a href='trazi.html'>Trazi korisnika</a></li>\n";
        retVal += "<li><a href='listaj'>Lista korisnika</a></li>\n";
        retVal += "</ul>\n";

        retVal += "<h1>"+content+"</h1>\n";


        retVal += "</body></html>";

        System.out.println("HTTP odgovor:");
        System.out.println(retVal);

        return retVal;
    }
    private void serveFile(String filename){
        String retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";

        out.println(retVal);

        File file = new File(filename);

        System.out.println(file.getAbsolutePath());

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text;

            while ((text = reader.readLine()) != null) {
                out.println(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("fajl: " + filename);

    }
}
