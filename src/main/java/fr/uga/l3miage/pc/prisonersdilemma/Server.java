package fr.uga.l3miage.pc.prisonersdilemma;

import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;

    // Starts the server
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                // Accept client connection
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    // Stops the server
    public void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Thread to handle individual client connections
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                // Input and output streams for communication
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received from client: " + inputLine);

                    // Echo the message back to the client
                    out.println("Server response: " + inputLine);

                    // Break the loop if the client sends 'bye'
                    if ("bye".equalsIgnoreCase(inputLine)) {
                        System.out.println("Client disconnected");
                        break;
                    }
                }

                // Clean up
                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Main method to start the server
    public static void main(String[] args) {
        Server server = new Server();
        server.start(8080); // The server will listen on port 6666
    }
}
