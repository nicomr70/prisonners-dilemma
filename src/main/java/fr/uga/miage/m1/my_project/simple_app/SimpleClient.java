package fr.uga.miage.m1.my_project.simple_app;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SimpleClient {
    private static final String SERVER_ADDRESS = "127.0.0.1"; // Adresse du serveur
    private static final int SERVER_PORT = 12345; // Port du serveur

    public static void main(String[] args) {
        try (
                // Se connecter au serveur
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                // Initialiser les flux d'entrée et de sortie
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in);
        ) {
            // Lire et afficher le message de bienvenue du serveur
            String welcomeMessage = in.readLine();
            System.out.println("Serveur: " + welcomeMessage);

            String userInput;
            while (true) {
                // Lire l'entrée utilisateur
                System.out.print("Vous: ");
                userInput = scanner.nextLine();
                out.println(userInput); // Envoyer le message au serveur

                // Lire la réponse du serveur
                String response = in.readLine();
                System.out.println(response);

                if (userInput.equalsIgnoreCase("exit")) {
                    String bye = in.readLine();
                    System.out.println(bye);
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Hôte inconnu: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erreur I/O: " + e.getMessage());
        }
    }
}
