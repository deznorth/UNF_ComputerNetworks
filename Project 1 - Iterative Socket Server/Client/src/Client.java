
import java.util.ArrayList;
import java.util.Scanner;

import entities.RequestType;
import entities.cThread;
import util.CSVExporter;

public class Client {

	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		// Variables
		int PORT, nClients = 1, operation;
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
				RequestType request = RequestType.DateTime; // Just so it is initialized
				switch (operation) {
					case 1 : request = RequestType.DateTime;
						break;
					case 2 : request = RequestType.Uptime;
						break;
					case 3 : request = RequestType.Memory;
						break;
					case 4 : request = RequestType.Netstat;
						break;
					case 5 : request = RequestType.CurrentUsers;
						break;
					case 6 : request = RequestType.RunningProcesses;
						break;
					case 7 : request = RequestType.Quit;
						break;
				}

				if (request != RequestType.Quit) {
					//Requests number of clients
					System.out.print("\nEnter the number of clients to generate: ");
					nClients = in.nextInt();
					System.out.println("\n");
				}

				//Generates threads based on # of clients
				cThread[] threads = new cThread[nClients];

				for(int i = 0; i < nClients; i++) {
					threads[i] = new cThread(HOST, PORT, i, request);
				}

				//Threads are executed
				for (cThread thread : threads) {
					thread.start();
				}

				// Join after all threads have started so the program waits for them to finish
				for (cThread thread : threads) {
					try {
						thread.join();
					} catch (InterruptedException e) {
						System.out.println("Exception while trying to join thread\n" + e.getMessage());
					}
				}

				if (request != RequestType.Quit) {
					CSVExporter exporter = new CSVExporter();
					ArrayList<String> col = new ArrayList<String>();

					col.add(request.name());

					// Calculate the average Server response time
					long sumOfTimes = 0;
					for (int i = 0; i< cThread.times.size(); i++) {
						long t = cThread.times.get(i);
						col.add(String.valueOf(t));
						System.out.printf("%nTime for Client Request #%d: \t%dms", i + 1, t);
						sumOfTimes += t;
					}
					double avgTime = sumOfTimes / (double)nClients;
					col.add("");
					col.add(String.valueOf(avgTime));
					col.add(String.valueOf(sumOfTimes));
					// Export values to csv file
					exporter.export(col, request.name());
					cThread.times.clear();
					System.out.printf("%n%nAverage time of response: \t%sms %n", avgTime);
					System.out.printf("%nTotal turn around time: \t%sms %n", sumOfTimes);
				}
			} else {
				System.out.println("Wrong command. Please try again\n");
				continue;
			}
		} while (operation != 7);
	}
}

