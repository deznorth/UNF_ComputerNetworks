import java.net.Socket;
import java.rmi.UnknownHostException;
import java.io.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import entities.RequestType;

public class Client extends Thread{ //CHANGED CLASS ////////////////////////////////
	
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		// Variables
		int PORT, nClients;
		String HOST;

		//Requests PORT number
		System.out.print("Enter the port number: ");
		PORT = in.nextInt();
		System.out.println();

		//Requests HOST name
		System.out.print("Enter the network address: ");
		HOST = in.next();
		System.out.println();

		try (
			Socket socket = new Socket(HOST, PORT);
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		) {
			String line;

			if (input.ready())
			while((line = input.readLine()) != null) {
				System.out.println(line);
			}

			int operation;

			do {
				//Requests operation
				System.out.print("\nList of operations: \n\n"
				+ "1 - Date and Time\n"
				+ "2 - Uptime\n"
				+ "3 - Memory Use\n"
				+ "4 - Netstat\n"
				+ "5 - Current Users\n"
				+ "6 - Running Processes\n"
				+ "7 - Exit \n\n"
				+ "Enter the operation that you would like to request: ");

				operation = in.nextInt();
				System.out.println();
				
				//Requests number of clients ////////////////////////////////////////
				System.out.print("\nEnter the number of clients to generate: ");
				nClients = in.nextInt();
				System.out.println("\n");
				
				//Generates threads based on # of clients
				cThread[] thread = new cThread[nClients];
				for(int i = 0; i < nClients; i++) {
					
					thread[i] = new cThread(HOST, PORT, operation);
				}
				
				
				for(int i = 0; i < nClients; i++) {

				switch (operation) {
					case 1 : output.println(RequestType.DateTime.name());
						break;
					case 2 : output.println(RequestType.Uptime.name());
						break;
					case 3 : output.println(RequestType.Memory.name());
						break;
					case 4 : output.println(RequestType.Netstat.name());
						break;
					case 5 : output.println(RequestType.CurrentUsers.name());
						break;
					case 6 : output.println(RequestType.RunningProcesses.name());
						break;
					case 7 :
						output.println(RequestType.Quit.name());
						break;
					default:
						System.out.println("Wrong command entered. Please try again.\n");
						break;
				}
			} while (operation != 7);
		} catch(UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch(IOException ex) {
			System.out.println("I/O error: " + ex.getMessage());
		}
	}
}

