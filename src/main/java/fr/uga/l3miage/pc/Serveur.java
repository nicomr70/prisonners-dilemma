package fr.uga.l3miage.pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Serveur en attente des joueurs...");

            // Connexion des deux joueurs
            Socket player1 = serverSocket.accept();
            System.out.println("Joueur 1 connecté.");
            // Streams pour la communication du joueur 1
            DataInputStream in1 = new DataInputStream(player1.getInputStream());
            DataOutputStream out1 = new DataOutputStream(player1.getOutputStream());

            out1.writeInt(1);
            //Demande du nombre de tours au premier joueur
            int nbTour = in1.readInt();
            System.out.println("nb tours  : "+nbTour);

            Socket player2 = serverSocket.accept();
            System.out.println("Joueur 2 connecté.");
            // Streams pour la communication du joueur 2
            DataInputStream in2 = new DataInputStream(player2.getInputStream());
            DataOutputStream out2 = new DataOutputStream(player2.getOutputStream());
            out2.writeInt(2);
            out2.writeInt(nbTour);

            //variarbles pour ld reoulement du jeu
            int score1 = 0;
            int score2 = 0;

            for(int i =0; i<nbTour; i++) {
                // Récupérer les choix des joueurs
                String choice1 = in1.readUTF();
                String choice2 = in2.readUTF();
                System.out.println("choix j1 : "+choice1);
                System.out.println("choix j2 : "+choice2);


                //Envoie des résultats
                if(choice1.equals("trahir") && choice2.equals("trahir")){
                    out1.writeUTF("Vous avez tous les deux trahis l'autre");
                    out2.writeUTF("Vous avez tous les deux trahis l'autre");
                    score1+=1;
                    score2+=1;
                }
                else if(choice1.equals("cooperer") && choice2.equals("cooperer")){
                    out1.writeUTF("Vous avez tous les deux coopérer");
                    out2.writeUTF("Vous avez tous les deux coopérer");
                    score1+=3;
                    score2+=3;
                }
                else if(choice1.equals("trahir") && choice2.equals("cooperer")){
                    out1.writeUTF("Tu as réussi a trahir le deuxiéme joueur");
                    out2.writeUTF("Tu as été tarhis par le deuxiéme joueur");
                    score1+=5;
                }
                else if(choice1.equals("cooperer") && choice2.equals("trahir")){
                    out1.writeUTF("Tu as été tarhis par le deuxiéme joueur");
                    out2.writeUTF("Tu as réussi a trahir le deuxiéme joueur");
                    score2+=5;
                }

            }
            System.out.println("score1 : "+score1);
            System.out.println("score2 : "+score2);

            // Fermer les connexions
            player1.close();
            player2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
