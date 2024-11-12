package fr.uga.miage.m1.my_project.server;

import fr.uga.miage.m1.my_project.server.models.Joueur;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Server {
    private static final int PORT = 7842;
    private static boolean isRunning = true;
    private List<Joueur> joueursAttente;
    private static final Logger logger = Logger.getLogger(Server.class.getName());


    public Server() {
        joueursAttente = new ArrayList<>();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur en écoute sur le port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connexion de " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

                ClientHandler handler = new ClientHandler(clientSocket, joueursAttente);
                handler.start();
                if (!isRunning) break;

            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Server().start();
    }
}