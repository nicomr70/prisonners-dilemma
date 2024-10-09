package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.stratégies.Strategie;
import fr.uga.l3miage.pc.stratégies.StrategieFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Serveur {
    private static Serveur instance;
    private ServerSocket serverSocket;
    private BufferedReader inClient1;
    private BufferedReader inClient2;
    private PrintWriter outClient1;
    private PrintWriter outClient2;
    private String coup;
    private int scoreTotalClient1;
    private int scoreTotalClient2;
    private Client client1;
    private Client client2;
    private String[] historiqueClient1;
    private String[] historiqueClient2;
    private int nbTours;
    Joueur joueur1,joueur2;
    private Partie jeu;

    //Constructeur privé pour le patron Singleton
    private Serveur() {
        this.scoreTotalClient1 = 0;
        this.scoreTotalClient2 = 0;
    }

    //Méthode statique pour obtenir l'instance unique du serveur
    public static Serveur getInstance() {
        if (instance == null) {
            instance = new Serveur();
        }
        return instance;
    }

    //Méthode pour démarrer le serveur
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Serveur démaré sur le port " + port);

        //Le serveur doit s'assusrer que deux joueurs ( clients ) soient connectés
        System.out.println("En attente du premier joueur...");
        Socket client1Socket = serverSocket.accept();
        System.out.println("Joueur 1 connecté");

        System.out.println("En attente du second joueur...");
        Socket client2Socket = serverSocket.accept();
        System.out.println("Joueur 2 connecté");

        outClient1 = new PrintWriter(client1Socket.getOutputStream(), true);
        outClient2 = new PrintWriter(client2Socket.getOutputStream(), true);
        inClient1 = new BufferedReader(new InputStreamReader(client1Socket.getInputStream()));
        inClient2 = new BufferedReader(new InputStreamReader(client2Socket.getInputStream()));

        joueur1 = new Joueur();
        joueur2 = new Joueur();

        client1 = new Client(joueur1);
        client2 = new Client(joueur2);


        outClient1.println("Bienvenue ! Veuillez choisir un nom.");
        client1.askName();
        String nomJoueur1 = inClient1.readLine();
        outClient2.println("Bienvenue ! Veuillez choisir un nom.");
        client2.askName();
        String nomJoueur2 = inClient2.readLine();

        outClient1.println("Veuillez choisir le nombre de tours.");
        nbTours = 0;
        outClient1.println("Combien de tours voulez-vous jouer ?");
        client1.askTours();
        String input = inClient1.readLine();
        try {
            do { //if (nbTours <= 0) {
                nbTours = Integer.parseInt(input);
                if (nbTours<=0) {
                outClient1.println("Veuillez entrer un nombre positif."); }
                else {outClient2.println("Le nombre de tours choisi est : "+ nbTours);}
            } while (nbTours<=0);
        }
        catch (NumberFormatException e) {
            outClient1.println("Entrée invalide. Veuillez entrer un nombre valide.");
        }

        //Créer et lancer la partie
        jeu = new Partie(client1,client2,nbTours);
        jeu.commencer();//,inClient1,inClient2,outClient1,outClient2).commencer();
    }

    public void askCoup(Client client) throws IOException {
        if (client == client1) {
            outClient1.println("C'est à votre tour de jouer.");
            client1.envoyerCoup(client1.recevoirCoup());

        }
        else if (client == client2) {
            outClient2.println("C'est à votre tour de jouer.");
            client2.envoyerCoup(client1.recevoirCoup());
        }
    }


    public void calculScore() throws IOException {
        int scoreClient1 = 0, scoreClient2 = 0;
        String coupClient1 = inClient1.readLine();
        historiqueClient1[nbTours] = coupClient1;
        String coupClient2 = inClient2.readLine();
        historiqueClient2[nbTours] = coupClient2;

        if (coupClient1.equals("c") && coupClient2.equals("c")) {
            scoreClient1 = 3;
            scoreClient2 = scoreClient1;
        }
        else if (coupClient1.equals("c") && coupClient2.equals("t")) {
            scoreClient1 = 5;
            //scoreClient2 = 0;
        }
        else if (coupClient1.equals("t") && coupClient2.equals("c")) {
            //scoreClient1 = 0;
            scoreClient2 = 5;
        }
        else if (coupClient1.equals("t") && coupClient2.equals("t")) {
            scoreClient1 = 1;
            scoreClient2 = 1;
        } else if(coupClient1.equals("a") && !Objects.equals(coupClient2, coupClient1)) {
            outClient1.println("Abandon");
            client1.askStategie();
            Strategie strategie = StrategieFactory.getStrategie(inClient1.readLine());
            jeu.partieSuivantAbandon(client2,strategie);
        } else if (coupClient2.equals("a") && !Objects.equals(coupClient2, coupClient1)) {
            outClient2.println("Abandon");
            client2.askStategie();
            Strategie strategie = StrategieFactory.getStrategie(inClient2.readLine());
            jeu.partieSuivantAbandon(client1,strategie);
        } else if (coupClient1.equals("a") && coupClient2.equals(coupClient1)) {
            jeu.fin();
        }

        scoreTotalClient1 += scoreClient1;
        scoreTotalClient2 += scoreClient2;
    }

    public void calculScoreCasAbandon(String coupServeur,Client client) throws IOException {
        int scoreClient = 0,scoreServeur = 0; String coupClient;
        if (client == client1) {coupClient= inClient1.readLine();}
        else {
            coupClient = inClient2.readLine();
        }
        if (coupClient.equals("c") && coupServeur.equals("c")) {
            scoreClient = 3;
            scoreServeur= scoreClient;
        }
        else if (coupClient.equals("t") && coupServeur.equals("t")) {
            scoreClient = 1;
            scoreServeur = 3;
        }
        else if (coupClient.equals("t") && coupServeur.equals("c")) {
            scoreServeur= 5;
        }
        else if (coupClient.equals("c") && coupServeur.equals("t")) {
            scoreClient = 5;
        }
        if (client==client1) {scoreTotalClient1 += scoreClient;
            scoreTotalClient2 += scoreServeur;
        }
        else {
            scoreTotalClient2 += scoreClient;
            scoreTotalClient1 += scoreServeur;
        }

    }

    public void envoyerScores() throws IOException {
        outClient1.println("Voici les scores : Vous = "+ scoreTotalClient1 + ", Votre adversaire = " +scoreTotalClient2);
        outClient2.println("Voici les scores : Vous = "+ scoreTotalClient2 + ", Votre adversaire = " +scoreTotalClient1);
        if (scoreTotalClient1 > scoreTotalClient2) {
            outClient1.println("Le gagnant de ce tour est : " + scoreTotalClient1);
            outClient2.println("Le gagnant de ce tour est : " + scoreTotalClient1);
        }
        else if (scoreTotalClient2 > scoreTotalClient1) {
            outClient1.println("Le gagnant de ce tour est : " + scoreTotalClient2);
            outClient2.println("Le gagnant de ce tour est : " + scoreTotalClient2);
        }
        else if (scoreTotalClient2 == scoreTotalClient2) {
            outClient1.println("Égalité !");
            outClient2.println("Égalité !");
        }
    }

    public void envoyerScoresCasAbandon(Client client) throws IOException {
        if (client==client1) {
            outClient1.println("Voici les scores : Vous = "+ scoreTotalClient1 + ", Votre adversaire = " +scoreTotalClient2);
        }
        else {
            outClient2.println("Voici les scores : Vous = "+ scoreTotalClient2 + ", Votre adversaire = " +scoreTotalClient1);
        }
    }

    public void vainceur() throws IOException {
        if (scoreTotalClient1> scoreTotalClient2) {
            outClient1.println("Vous êtes le vainceur, Bravo : "+ joueur1.getNom());
            outClient2.println("Le vainceur est : "+ joueur1.getNom());
        }
        else if (scoreTotalClient2> scoreTotalClient1) {
            outClient1.println("Le vainceur est : "+ joueur2.getNom());
            outClient2.println("Vous êtes le vainceur, Bravo : "+ joueur2.getNom());
        }
        else if (scoreTotalClient2 == scoreTotalClient2) {
            outClient1.println("Egalite!");
            outClient2.println("Egalite!");
        }
    }

    public void vainceurAbandon(Client client){
        if (client==client1) {
            if (scoreTotalClient1> scoreTotalClient2) {
                outClient1.println("Vous êtes le vainceur, Bravo "+ joueur1.getNom()); }
            else if (scoreTotalClient2>scoreTotalClient1) {
                outClient1.println("Vous avez perdu.");
            }
            else if (scoreTotalClient2==scoreTotalClient2) {
                outClient1.println("Egalité!");
            }
        }
        else {
            if (scoreTotalClient1> scoreTotalClient2) {
                outClient2.println("Vous avez perdu.");
            }
            else if (scoreTotalClient2>scoreTotalClient1) {
                outClient2.println("Vous êtes le vainceur, Bravo "+ joueur1.getNom());
            }
            else if (scoreTotalClient2==scoreTotalClient2) {
                outClient2.println("Egalité!");
            }
        }
    }

    //Méthode pour arréter le serveur
    public void stop() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
    }

}
