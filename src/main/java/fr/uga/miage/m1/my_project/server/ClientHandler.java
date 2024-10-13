package fr.uga.miage.m1.my_project.server;

import fr.uga.miage.m1.my_project.server.models.Humain;
import fr.uga.miage.m1.my_project.server.models.Joueur;
import fr.uga.miage.m1.my_project.server.models.enums.ChoiceCommand;
import fr.uga.miage.m1.my_project.server.models.enums.EtatJoueur;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;


public class ClientHandler extends Thread {
    private Socket clientSocket;
    private List<Joueur> joueursAttente;

    public ClientHandler(Socket socket, List<Joueur> joueursAttente) {
        this.clientSocket = socket;
        this.joueursAttente = joueursAttente;
    }

    @Override
    public void run() {
        try {
            // Initialiser les flux
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            // Envoyer le message de bienvenue
            out.writeObject("Bienvenue! Veuillez entrer votre nom:");
            out.flush();

            // Recevoir le nom
            String nom = (String) in.readObject();
            System.out.println("Received :" + nom);

            // Créer un joueur humain (vous pouvez ajouter la logique pour différencier Humain et Robot)
            Joueur joueur = new Humain(clientSocket.getInetAddress().toString() + ":" + clientSocket.getPort(), nom, clientSocket, out, in);
            joueur.setEtat(EtatJoueur.EN_MENU);

            boolean quitter = false;

            while (!quitter) {
                switch (joueur.getEtat()) {
                    case EN_ATTENTE, EN_PARTIE -> {
                        try {
                            // Met le thread en attente jusqu'à ce qu'un autre joueur rejoigne
                            // Met le threand en attente jusqu'à ce que la partie termine
                            sleep(5000);

                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();  // Réinitialise le flag d'interruption
                        }
                    }
                    case EN_MENU -> {
                        // Recevoir choix client
                        ChoiceCommand choiceCommand = (ChoiceCommand) in.readObject();
                        System.out.println("Received :" + choiceCommand);

                        synchronized (joueursAttente) {
                            switch (choiceCommand) {
                                case INITIER_PARTIE -> {
                                    // Envoyer le message de saisis du nombre de tour
                                    out.writeObject("Saisire nombre de tour");
                                    out.flush();
                                    int nombreTourChoisis = (int) in.readObject();
                                    Rencontre.addRencontreEnAttente(new Rencontre(joueur, nombreTourChoisis));
                                    joueursAttente.add(joueur);
                                    joueur.setEtat(EtatJoueur.EN_ATTENTE);
                                    joueur.sendMessage("En attente d'un autre joueur pour démarrer la rencontre...");

                                }
                                case REJOINDRE_PARTIE -> {
                                    // ici on dois avoir le choix de la partie ...
                                    List<Rencontre> rencontres = Rencontre.getRencontresEnAttente();
                                    if (rencontres.isEmpty()) {
                                        joueur.sendMessage("Aucun rencontre initié");
                                        // je dois le rediriger vers le choix...
                                    }
                                    else {
                                        joueursAttente.remove(joueur);
                                        Rencontre rencontre = rencontres.get(rencontres.size() - 1);
                                        rencontre.setAdversaire(joueur);
                                        joueur.setEtat(EtatJoueur.EN_ATTENTE);
                                        rencontre.start();
                                    }
                                }
                                case QUITTER_JEUX -> {
                                    out.writeObject("Bye Bye");
                                    out.flush();
                                    joueur.close();
                                    quitter = true;
                                }
                            }
                        }
                    }
                }


            }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}