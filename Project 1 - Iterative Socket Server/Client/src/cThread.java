import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;

import entities.RequestType;

public class cThread extends Thread{
	
	private String HOST;
	private int PORT; 
	private int command; //sends command to server
	private String line; //displays info from server
	private PrintWriter print; //used to print to the server
	private Socket clientS; //client socket
	private BufferedReader input; //scanner used to read from server
	
	//Constructor sets up the socket
	public cThread(String HOST, int PORT, int command) {
		
		this.HOST = HOST;
		this.PORT = PORT;
		this.command = command;
		
		try {
			//socket, print and output references are created
			clientS = new Socket(HOST,PORT);
			print = new PrintWriter(clientS.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(clientS.getInputStream()));
		}
		catch(UnknownHostException e) {
			System.out.println("Server not found: " + e.getMessage());
		}
		catch(IOException e) {
			System.out.println("I/O error: " + e.getMessage());
		}
		
	}
	
	
	public void start() {
		
		switch (command) {
	
		case 1 : print.println(RequestType.DateTime.name());
			break;
		case 2 : print.println(RequestType.Uptime.name());
			break;
		case 3 : print.println(RequestType.Memory.name());
			break;
		case 4 : print.println(RequestType.Netstat.name());
			break;
		case 5 : print.println(RequestType.CurrentUsers.name());
			break;
		case 6 : print.println(RequestType.RunningProcesses.name());
			break;
		case 7 :
			print.println(RequestType.Quit.name());
			break;
		default:
			System.out.println("Wrong command entered. Please try again.\n");
			break;
			
		}
		
//		if (input.ready())
//			while((line = input.readLine()) != null) {
//				System.out.println(line);
//			}
		
		
		//long time = System.currentTimeMillis();
		
		}
	
}
	




