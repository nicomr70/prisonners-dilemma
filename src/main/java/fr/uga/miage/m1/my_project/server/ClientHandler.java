package fr.uga.miage.m1.my_project.server;

import fr.uga.miage.m1.my_project.server.models.Humain;
import fr.uga.miage.m1.my_project.server.models.Joueur;
import fr.uga.miage.m1.my_project.server.models.enums.ChoiceCommand;
import fr.uga.miage.m1.my_project.server.models.enums.EtatJoueur;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Objects;


public class ClientHandler extends Thread {
    private Socket clientSocket;
    private List<Joueur> joueursAttentes;

    public ClientHandler(Socket socket, List<Joueur> joueursAttente) {
        this.clientSocket = socket;
        this.joueursAttentes = joueursAttente;
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


            while (Objects.requireNonNull(joueur.getEtat()) == EtatJoueur.EN_MENU) {// Recevoir choix client
                System.out.println(joueur.getEtat());
                ChoiceCommand choiceCommand = (ChoiceCommand) in.readObject();
                System.out.println("Received :" + choiceCommand);

                synchronized (joueursAttentes) {
                    switch (choiceCommand) {
                        case INITIER_PARTIE -> {
                            // Envoyer le message de saisis du nombre de tour
                            out.writeObject("Saisire nombre de tour");
                            out.flush();
                            int nombreTourChoisis = (int) in.readObject();
                            Rencontre.addRencontreEnAttente(new Rencontre(joueur, nombreTourChoisis));
                            joueursAttentes.add(joueur);
                            joueur.setEtat(EtatJoueur.EN_ATTENTE);
                            joueur.sendMessage("En attente d'un autre joueur pour démarrer la rencontre...");

                        }
                        case REJOINDRE_PARTIE -> {
                            // ici on dois avoir le choix de la partie ...
                            List<Rencontre> rencontres = Rencontre.getRencontresEnAttente();
                            if (rencontres.isEmpty()) {
                                joueur.sendMessage("Aucun rencontre initié");
                            } else {
                                joueursAttentes.remove(joueur);
                                Rencontre rencontre = rencontres.get(rencontres.size() - 1);
                                rencontre.setAdversaire(joueur);
                                joueur.setEtat(EtatJoueur.EN_ATTENTE);
                                rencontre.start();
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