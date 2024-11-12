package fr.uga.miage.m1.my_project.client;

import fr.uga.miage.m1.my_project.server.models.enums.ChoiceCommand;
import fr.uga.miage.m1.my_project.server.models.enums.TypeStrategie;
import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;



public class Client {
    public static final String SERVEUR_MESSAGE = "Serveur: {}";
    private final String host;
    private final int port;
    private final Logger logger = LoggerFactory.getLogger(Client.class.getName());

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
            recevoirMessageBienvenue(in);
            envoyerNomJoueur(scanner, out);
            choixDeMethode(scanner, out, in);
            // Commencer à écouter les messages du serveur
            ecouteClient(out, in, scanner);
        } catch (Exception e) {
            logger.info("Connexion fermée.");
        }
    }

    private void recevoirMessageBienvenue(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // Recevoir le message de bienvenue
        String welcome = (String) in.readObject();
        logger.info(SERVEUR_MESSAGE, welcome);
    }

    private void envoyerNomJoueur(Scanner scanner, ObjectOutputStream out) throws IOException {
        // Entrer le nom
        logger.info("Entrez votre nom: ");
        String nom = scanner.nextLine();
        out.writeObject(nom);
        out.flush();
    }

    private void choixDeMethode(Scanner scanner, ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException {
        // Choisir une option
        ChoiceCommand choix = null;
        while (choix == null) {
            logger.info("Entrez votre choix (INITIER_PARTIE / REJOINDRE_PARTIE): ");
            String input = scanner.nextLine().toUpperCase();
            try {
                choix = ChoiceCommand.valueOf(input);
            } catch (IllegalArgumentException e) {
                logger.info("Choix invalide. Veuillez entrer INITIER_PARTIE, REJOINDRE_PARTIE ou QUITTER_JEUX.");
                continue;
            }
            // Envoyer la commande au serveur
            out.writeObject(choix);
            out.flush();

            // Recevoir le message suivant du serveur
            String message = (String) in.readObject();
            logger.info(SERVEUR_MESSAGE, message);

            if (choix == ChoiceCommand.INITIER_PARTIE) {
                // Ici on doit mentionner le nombre de tours qu'on veut...
                // Recevoir la confirmation ou attendre
                int nbTour = demanderNombreDeTours(scanner);
                out.writeObject(nbTour);
                out.flush();

            } else if (choix == ChoiceCommand.REJOINDRE_PARTIE && (message.startsWith("Aucun"))) {
                // Ici le client doit choisir une partie parmi les parties existantes
                // Si aucune partie n'existe, informer le client, le mettre en attente,
                // et éventuellement proposer d'initier une partie s'il le souhaite...
                // Cette logique dépend de la mise en œuvre côté serveur
                    choix = null;
            }
        }
    }


    private void ecouteClient(ObjectOutputStream out, ObjectInputStream in, Scanner scanner) throws IOException, ClassNotFoundException {
        while (true) {
            Object obj = in.readObject();

            if (obj instanceof String serveurMessage) {
                logger.info(SERVEUR_MESSAGE, serveurMessage);

                if (serveurMessage.startsWith("Tour")) {
                    traiterTour(out, scanner);
                } else if (serveurMessage.startsWith("Bye")) {
                    break;
                } else if (serveurMessage.startsWith("choisir une strategie automatique parmi")) {
                    traiterChoixStrategie(out, scanner);
                }
            }
        }
    }

    private void traiterTour(ObjectOutputStream out, Scanner scanner) throws IOException {
        TypeAction action = null;
        while (action == null) {
            logger.info("Choisissez votre action (COOPERER/TRAHIR/ABONDONNER): ");
            String actionInput = scanner.nextLine().toUpperCase();
            action = getTypeAction(null, actionInput);
        }
        envoyerObjet(out, action);
    }

    private void traiterChoixStrategie(ObjectOutputStream out, Scanner scanner) throws IOException {
        TypeStrategie typeStrategie = null;
        while (typeStrategie == null) {
            logger.info("Choisissez votre strategie automatique : ");
            String actionInput = scanner.nextLine().toUpperCase();
            typeStrategie = getTypeStrategie(null, actionInput);
        }
        envoyerObjet(out, typeStrategie);
    }

    private void envoyerObjet(ObjectOutputStream out, Object obj) throws IOException {
        out.writeObject(obj);
        out.flush();
    }

    private TypeStrategie getTypeStrategie(TypeStrategie typeStrategie, String actionInput) {
        try {
            typeStrategie = TypeStrategie.valueOf(actionInput);
        } catch (IllegalArgumentException e) {
            logger.info("Action invalide. Veuillez choisir entre : ");
            for (TypeStrategie act : TypeStrategie.values()) {
                logger.info("- {}" , act);
            }
            logger.info("Choisissez votre strategie automatique : ");
        }
        return typeStrategie;
    }

    private TypeAction getTypeAction(TypeAction action, String actionInput) {
        try {
            action = TypeAction.valueOf(actionInput);
        } catch (IllegalArgumentException e) {
            logger.info("Action invalide. Veuillez choisir COOPERER ou TRAHIR.");
        }
        return action;
    }

    public static void main(String[] args) {
        String host = "127.0.0.1"; // Adresse du serveur
        int port = 7842;

        Client client = new Client(host, port);
        client.start();
    }

    private int demanderNombreDeTours(Scanner scanner) {
        int nbTour;

        while (true) {
            logger.info("Entrez le nombre de tours : ");
            String input = scanner.nextLine();
            if (isPositiveInteger(input)) {
                nbTour = Integer.parseInt(input);
                break;
            }
            else {
                logger.info("Le nombre de tours doit être un entier positif.");
            }

        }
        return nbTour;
    }

    private boolean isPositiveInteger(String input) {
        try {
            int number = Integer.parseInt(input);
            return number > 0;
        }
        catch (NumberFormatException e) {
            logger.info("Entrée invalide. Veuillez entrer un entier.");
            return false;
        }
    }
}
