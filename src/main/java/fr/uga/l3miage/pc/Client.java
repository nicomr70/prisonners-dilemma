package fr.uga.l3miage.pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)) {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);
            int nbTour = 0;
            int numeroConnexion = in.readInt();
            System.out.println("numero de connexion : "+numeroConnexion);
            if(numeroConnexion == 1){
                System.out.println("Combien de tours voulez vous jouer ?");
                nbTour = parseInt(scanner.nextLine());
                out.writeInt(nbTour);
                System.out.println("nb tour envoyer au serveru : "+nbTour);
            }
            else if(numeroConnexion == 2){
                nbTour = in.readInt();
            }


            for (int i = 0; i < nbTour; i++) {
                System.out.println(" - Faites votre choix : 'cooperer' ou 'trahir' ?");
                String choice = scanner.nextLine();
                out.writeUTF(choice);

                String resultat = in.readUTF();
                System.out.println(resultat);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
