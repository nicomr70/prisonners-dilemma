package fr.uga.l3miage.pc.prisonersdilemma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    // Method to start the connection to the server
    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sends a message to the server
    public String sendMessage(String msg) {
        try {
            out.println(msg);
            return in.readLine(); // Wait for the server's response
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to stop the connection
    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Main method to run the client
    public static void main(String[] args) {
        Client client = new Client();
        client.startConnection("127.0.0.1", 6666); // Connect to localhost on port 6666

        // Test message exchange with the server
        String response = client.sendMessage("Hello Server!");
        System.out.println(response); // Print server's response

        response = client.sendMessage("bye"); // Disconnect by sending 'bye'
        System.out.println(response);

        client.stopConnection();
    }
}

