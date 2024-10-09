package fr.uga.l3miage.pc.prisonersdilemma;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    Joueur joueur;

    //Chaque Client repr?sente un joueur
    public Client (Joueur joueur){
        this.joueur = joueur;
    }

    //Connexion au serveur
    public void seConnecter (String adresse,int port) throws IOException{
        socket = new Socket(adresse,port);
        out = new PrintWriter(socket.getOutputStream(),true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Connect? au serveur sur + " + adresse + ":" +port);
    }

    public void envoyerCoup(String coup) throws IOException{
        out.println(coup);
    }

    public void seDeconnecter() throws IOException{
        in.close();
        out.close();
        socket.close();
        System.out.println("D?connect? du serveur.");
    }

}