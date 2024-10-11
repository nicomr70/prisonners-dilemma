package fr.uga.miage.m1.my_project.server.models;

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
    protected Socket socket;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    public Joueur(String id, String nom, Socket socket, ObjectOutputStream out, ObjectInputStream in) throws Exception {
        this.id = id;
        this.nom = nom;
        this.score = 0;
        this.socket = socket;
        this.out = out;
        this.in = in;
    }

    public void addScore(int s) {
        this.score += s;
    }

    public abstract TypeAction jouer(List<TypeAction> historiqueAdversaire, int dernierResultat) throws Exception;

    public void sendMessage(Object obj) throws Exception {
        out.writeObject(obj);
        out.flush();
    }

    public Object receiveMessage() throws Exception {
        return in.readObject();
    }

    public void close() {
        try {
            socket.close();
        } catch (Exception e) {
            // Ignorer
        }
    }
}