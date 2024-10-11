package fr.uga.miage.m1.my_project.simple_app;

import java.io.*;
import java.net.*;
import java.util.Random;

public class SimpleServer {
    private static final int PORT = 12345; // Port d'écoute

    public static void main(String[] args) {
        System.out.println("Serveur démarré. En attente de connexions...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // Accepter une connexion client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouvelle connexion de " + clientSocket.getInetAddress());

                // Créer et démarrer un nouveau thread pour gérer ce client
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur du serveur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

// Classe pour gérer la communication avec un client
class ClientHandler extends Thread {
    private Socket socket;
    private int numberToGuess;
    private int maxAttempts = 10;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        Random rand = new Random();
        this.numberToGuess = rand.nextInt(100) + 1; // Choisit un nombre entre 1 et 100

    }

    @Override
    public void run() {
        try (
                // Initialiser les flux d'entrée et de sortie
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            out.println("Bienvenue au serveur! Tapez 'exit' pour quitter.");

            String message;
            int attempts = 0;

            while ((message = in.readLine()) != null) {
                System.out.println("Reçu de " + socket.getInetAddress() + ": " + message);
                if (message.equalsIgnoreCase("exit")) {
                    out.println("Au revoir!");
                    break;
                }

                try {
                    int guess = Integer.parseInt(message);
                    if (guess < numberToGuess) {
                        out.println("Trop bas!");
                    } else if (guess > numberToGuess) {
                        out.println("Trop haut!");
                    } else {
                        out.println("Félicitations! Vous avez deviné le nombre en " + attempts + " tentatives.");
                        break;
                    }

                    if (attempts >= maxAttempts) {
                        out.println("Désolé, vous avez atteint le nombre maximum de tentatives. Le nombre était " + numberToGuess + ".");
                        break;
                    }
                } catch (NumberFormatException e) {
                    out.println("Veuillez entrer un nombre valide ou 'exit' pour quitter.");
                }

            }
        } catch (IOException e) {
            System.err.println("Erreur avec le client " + socket.getInetAddress() + ": " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Connexion fermée avec " + socket.getInetAddress());
            } catch (IOException e) {
                System.err.println("Erreur lors de la fermeture du socket: " + e.getMessage());
            }
        }
    }
}
