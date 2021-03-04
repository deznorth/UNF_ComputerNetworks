package entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.ArrayList;

public class cThread extends Thread{
	// IO references
	private Socket clientS; //client socket
	private PrintWriter print; //used to print to the server
	private BufferedReader input; //scanner used to read from server
	private String line; //displays info from server

	// Variables
	private long totalTime; //total time spent
	private RequestType request; // sends request to server

	// Static Variables
	public static ArrayList<Long> times = new ArrayList<Long>(); // Static array of all request total times.

	//Constructor sets up the socket
	public cThread(String HOST, int PORT, int clientIndex, RequestType request) {
		try {
			// Setup IO
			this.clientS = new Socket(HOST,PORT);
			this.print = new PrintWriter(clientS.getOutputStream(), true);
			this.input = new BufferedReader(new InputStreamReader(clientS.getInputStream()));

			// Vars
			this.setName("client " + (clientIndex + 1));
			this.request = request;
			this.totalTime = 0;
		}
		catch(UnknownHostException e) {
			System.out.println("Server not found: " + e.getMessage());
			System.exit(1);
		}
		catch(IOException e) {
			System.out.println("I/O error: " + e.getMessage());
			System.exit(1);
		}
	}


	public void run() {
		// get the start time of the thread
		long timeStarted = System.currentTimeMillis();
		// Submit corresponding command to the server
		print.println(request.name());
		try {
			if (request != RequestType.Quit) {
				String result = this.getName() + "%n%n";
				//Reading output from server
				while((line = input.readLine()) != null) {
					if (line.isEmpty()) continue;
					result += String.format("%s %n", line);
				}
				System.out.printf(result + "%n");
			}

			input.close();
			print.close();
			clientS.close();
		} catch(IOException e) {
			//Exits system if error occurs
			System.out.println("Error occurred in I/O stream.\n");
			System.exit(1);
		}

		//Total time after thread was executed
		this.totalTime = System.currentTimeMillis() - timeStarted;
		times.add(this.totalTime);
	}
}





