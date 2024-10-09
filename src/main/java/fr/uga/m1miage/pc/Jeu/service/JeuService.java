package fr.uga.m1miage.pc.Jeu.service;


import fr.uga.m1miage.pc.Jeu.enums.StatutJeuEnum;
import fr.uga.m1miage.pc.Jeu.models.JeuEntity;
import fr.uga.m1miage.pc.Jeu.repository.JeuRepository;
import fr.uga.m1miage.pc.Joueur.enums.StrategieEnum;
import fr.uga.m1miage.pc.Joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.Joueur.repository.JoueurRepository;
import fr.uga.m1miage.pc.Partie.enums.StatutPartieEnum;
import fr.uga.m1miage.pc.Partie.models.PartieEntity;
import fr.uga.m1miage.pc.Partie.repository.PartieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JeuService {

    @Autowired
    JoueurRepository joueurRepository;

    @Autowired
    JeuRepository jeuRepository;

    @Autowired
    PartieRepository partieRepository;

    public JeuEntity creerJeu(String nomJoueur, int nombreParties) {
        JeuEntity jeu = JeuEntity
                .builder()
                .nombreParties(nombreParties)
                .statut(StatutJeuEnum.EN_ATTENTE)
                .build();
        JeuEntity jeuEnregistrer = jeuRepository.save(jeu);

        JoueurEntity joueur = JoueurEntity
                .builder()
                .nomJoueur(nomJoueur)
                .jeu(jeuEnregistrer)
                .build();
        joueurRepository.save(joueur);

        return jeuEnregistrer;
    }

    public JeuEntity joindreJeu(String pseudo, Long id) {
        JeuEntity jeu = jeuRepository.findById(id).orElseThrow();
        if (jeu.getStatut().equals(StatutJeuEnum.EN_COURS)) {
            throw new RuntimeException("Le nombre de joueurs est atteint");
        }
        JoueurEntity secondJoueur = JoueurEntity
                .builder()
                .jeu(jeu)
                .nomJoueur(pseudo)
                .build();
        joueurRepository.save(secondJoueur);
        PartieEntity partie = PartieEntity
                .builder()
                .jeu(jeu)
                .statut(StatutPartieEnum.EN_COURS)
                .build();
        partieRepository.save(partie);
        jeu.setStatut(StatutJeuEnum.EN_COURS);

        return jeuRepository.save(jeu);
    }


    public JeuEntity recupererJeu(Long idJeu) {
        return jeuRepository.findById(idJeu).orElseThrow();
    }

    public void abandonnerJeu(UUID idJoueur, StrategieEnum strategie) {
        JoueurEntity joueur = joueurRepository.findById(idJoueur).orElseThrow();
        joueur.setAbandon(true);
        joueur.setStrategie(strategie);
        joueurRepository.save(joueur);
    }


}
