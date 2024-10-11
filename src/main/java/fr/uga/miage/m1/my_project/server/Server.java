package fr.uga.miage.m1.my_project.server;

import fr.uga.miage.m1.my_project.server.models.Joueur;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 7842;
    private List<Joueur> joueursAttente;

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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().start();
    }
}