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
        System.out.println("Connecte au serveur sur + " + adresse + ":" +port);
    }

    public void envoyerCoup(String coup) throws IOException{
        out.println(coup);
    }

    public String recevoirCoup () throws IOException{
     return joueur.decision();
    }

    public void askName() {
        joueur.setNom();
        out.println(joueur.getNom());
    }

    public void askTours() {
        String NbTours = joueur.getNbTours();
        out.println(NbTours);
    }

    public void seDeconnecter() throws IOException{
        in.close();
        out.close();
        socket.close();
        System.out.println("Deconnecte du serveur.");
    }

    //Demmande de la stratégie voulue par le joueur lors de son abandon
    public void askStategie() throws IOException{
       out.println(joueur.abandonner());
    }

}