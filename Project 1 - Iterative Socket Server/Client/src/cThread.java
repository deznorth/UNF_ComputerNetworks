import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class cThread extends Thread{
	
	private String HOST;
	private int PORT;
	private int command;
	private String line;
	private PrintWriter print;
	
	//Constructor sets up the socket
	public cThread(String HOST, int PORT, int command) {
		
		this.HOST = HOST;
		this.PORT = PORT;
		this.command = command;
	}
	
	
	public void start() {
		
		//socket is created
		try (
		Socket socket = new Socket(HOST, PORT);
		PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	) {

		if (input.ready())
		while((line = input.readLine()) != null);
			System.out.println(line);
		}
		catch(UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch(IOException ex) {
			System.out.println("I/O error: " + ex.getMessage());
		}
		
		//long time = System.currentTimeMillis();
		
		}
	
	}
	




