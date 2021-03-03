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
		int PORT, nClients, operation;
		String HOST;

		//Requests PORT number
		System.out.print("Enter the port number: ");
		PORT = in.nextInt();
		System.out.println();

		//Requests HOST name
		System.out.print("Enter the network address: ");
		HOST = in.next();
		System.out.println();

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
				
				if(operation > 0 && operation < 8) {
				
					//Requests number of clients
					System.out.print("\nEnter the number of clients to generate: ");
					nClients = in.nextInt();
					System.out.println("\n");
					
					//Generates threads based on # of clients
					cThread[] thread = new cThread[nClients];
					for(int i = 0; i < nClients; i++) {
						
						thread[i] = new cThread(HOST, PORT, operation);
					}
					
					//Threads are executed
					for(int i = 0; i < nClients; i++) {
						
						//thread[i].
					}
				}
				
				else {
					System.out.println("Wrong command. Please try again\n");
					continue;
				}
				
		} while (operation != 7); 			
	}
}

