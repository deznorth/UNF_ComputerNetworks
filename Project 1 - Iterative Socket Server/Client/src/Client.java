import java.net.Socket;
import java.rmi.UnknownHostException;
import java.io.*;
import java.util.Scanner;

public class Client {
	
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length < 1) {
			return;
		}
		
		String domainName = args[0];
		
		//Requests port number
		System.out.print("Enter the port number: ");
		int port = in.nextInt();
		System.out.println();
		
		//Requests host name
		System.out.print("Enter the network address: ");
		String host = in.next();
		System.out.println();
		
		try(Socket socket = new Socket(host, port)) {
			
			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			writer.println(domainName);
			
			InputStream input = socket.getInputStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			String line;
			
			while((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			
		} catch(UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
			
		} catch(IOException ex) {
			System.out.println("I/O error: " + ex.getMessage());
		}
		
		//Requests operation
		System.out.print("\nList of operations: \n\n"
				+ "1 - Date and Time\n"
				+ "2 - Uptime\n"
				+ "3 - Memory Use\n"
				+ "4 - Netstat\n"
				+ "5 - Current Users\n"
				+ "6 - Running Processes\n\n"
				+ "Enter the operation that you would like to request");
		
		int operation = in.nextInt();
		System.out.println();
		
		while(operation != 7) {
			
			if(operation == 1) {
				
			}
			
			else {
				System.out.println("Wrong command entered. Please try again.\n");
			}
		}

	}

}

