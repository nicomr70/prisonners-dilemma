package fr.uga.miage.m1.my_project.server.models;

import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Robot extends Joueur {

    public Robot(String id, String nom, Socket socket, ObjectOutputStream out, ObjectInputStream in) throws Exception {
        super(id, nom, socket, out, in);
    }

    @Override
    public TypeAction jouer(List<TypeAction> historiqueAdversaire, int dernierResultat) throws Exception {
        if (strategieAutomatique != null) {
            return strategieAutomatique.getAction(historiqueAdversaire, dernierResultat);
        }
        return TypeAction.COOPERER; // Valeur par défaut
    }

    // Méthodes spécifiques aux robots peuvent être ajoutées ici
}