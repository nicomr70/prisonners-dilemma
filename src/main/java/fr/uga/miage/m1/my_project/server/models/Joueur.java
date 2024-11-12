package fr.uga.miage.m1.my_project.server.models;

import fr.uga.miage.m1.my_project.server.models.enums.EtatJoueur;
import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;
import fr.uga.miage.m1.my_project.server.models.strategies.Strategie;
import lombok.Data;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

@Data
public abstract class Joueur {
    protected String id;
    protected String nom;
    protected int score;
    protected Strategie strategieAutomatique;
    protected EtatJoueur etat;

    public Joueur(String id, String nom) throws Exception {
        this.id = id;
        this.nom = nom;
        this.score = 0;
    }

    public void addScore(int s) {
        this.score += s;
    }

    public abstract TypeAction jouer(List<TypeAction> historiqueAdversaire, int dernierResultat) throws Exception;

    public void sendMessage(Object obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object receiveMessage() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}