package fr.uga.miage.m1.my_project.server.models;

import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Humain extends Joueur {

    public Humain(String id, String nom, Socket socket, ObjectOutputStream out, ObjectInputStream in) throws Exception {
        super(id, nom, socket, out, in);
    }

    @Override
    public TypeAction jouer(List<TypeAction> historiqueAdversaire, int dernierResultat) throws Exception {
        // Attendre que le client envoie l'action
        Object obj = receiveMessage();
        if (obj instanceof String) {
            String actionStr = ((String) obj).toUpperCase();
            try {
                return TypeAction.valueOf(actionStr);
            } catch (IllegalArgumentException e) {
                // Action invalide, par défaut COOPERER
                return TypeAction.COOPERER;
            }
        }
        return TypeAction.COOPERER; // Valeur par défaut
    }

    // Méthodes spécifiques aux humains peuvent être ajoutées ici
}