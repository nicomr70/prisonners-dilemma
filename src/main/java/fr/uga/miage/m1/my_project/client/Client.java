package fr.uga.miage.m1.my_project.client;

import fr.uga.miage.m1.my_project.server.models.enums.ChoiceCommand;
import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String host;
    private int port;
    private String nom;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try (Socket socket = new Socket(host, port)) {
            // Créer les flux une seule fois, dans cet ordre
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);

            // Recevoir le message de bienvenue
            String welcome = (String) in.readObject();
            System.out.println("Serveur: " + welcome);

            // Entrer le nom
            System.out.print("Entrez votre nom: ");
            nom = scanner.nextLine();
            out.writeObject(nom);
            out.flush();

            // Choisir une option
            ChoiceCommand choix = null;
            while (choix == null) {
                System.out.print("Entrez votre choix (INITIER_PARTIE / REJOINDRE_PARTIE): ");
                String input = scanner.nextLine().toUpperCase();
                try {
                    choix = ChoiceCommand.valueOf(input);
                } catch (IllegalArgumentException e) {
                    System.out.println("Choix invalide. Veuillez entrer INITIER_PARTIE ou REJOINDRE_PARTIE.");
                }
            }

            // Envoyer la commande au serveur
            out.writeObject(choix);
            out.flush();

            // Recevoir le message suivant du serveur
            String message = (String) in.readObject();
            System.out.println("Serveur: " + message);

            if (choix == ChoiceCommand.INITIER_PARTIE) {
                // Ici on doit mentionner le nombre de tours qu'on veut...
                // Recevoir la confirmation ou attendre
                int nbTour = demanderNombreDeTours(scanner);
                out.writeObject(nbTour);
                out.flush();

            } else if (choix == ChoiceCommand.REJOINDRE_PARTIE) {
                // Ici le client doit choisir une partie parmi les parties existantes
                // Si aucune partie n'existe, informer le client, le mettre en attente,
                // et éventuellement proposer d'initier une partie s'il le souhaite...
                // Cette logique dépend de la mise en œuvre côté serveur
            }

            // Commencer à écouter les messages du serveur
            while (true) {
                Object obj = in.readObject();
                if (obj instanceof String) {
                    String serveurMessage = (String) obj;
                    System.out.println("Serveur: " + serveurMessage);

                    if (serveurMessage.startsWith("Tour")) {
                        TypeAction action = null;
                        while (action == null) {
                            System.out.print("Choisissez votre action (COOPERER/TRAHIR): ");
                            String actionInput = scanner.nextLine().toUpperCase();
                            try {
                                action = TypeAction.valueOf(actionInput);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Action invalide. Veuillez choisir COOPERER ou TRAHIR.");
                            }
                        }
                        out.writeObject(action);
                        out.flush();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Connexion fermée.");
            // e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String host = "127.0.0.1"; // Adresse du serveur
        int port = 7842;

        Client client = new Client(host, port);
        client.start();
    }

    private int demanderNombreDeTours(Scanner scanner) {
        int nbTour = 0;
        boolean valide = false;

        while (!valide) {
            System.out.print("Entrez le nombre de tours : ");
            String input = scanner.nextLine();

            try {
                nbTour = Integer.parseInt(input);
                if (nbTour > 0) {
                    valide = true;
                } else {
                    System.out.println("Le nombre de tours doit être un entier positif.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un entier positif.");
            }
        }

        return nbTour;
    }
}
