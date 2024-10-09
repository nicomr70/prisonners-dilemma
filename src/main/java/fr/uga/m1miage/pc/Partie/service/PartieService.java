package fr.uga.m1miage.pc.Partie.service;


import fr.uga.m1miage.pc.Jeu.models.JeuEntity;
import fr.uga.m1miage.pc.Jeu.repository.JeuRepository;
import fr.uga.m1miage.pc.Joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.Joueur.repository.JoueurRepository;
import fr.uga.m1miage.pc.Partie.enums.CoupEnum;
import fr.uga.m1miage.pc.Partie.enums.StatutPartieEnum;
import fr.uga.m1miage.pc.Partie.models.PartieEntity;
import fr.uga.m1miage.pc.Partie.models.PartieJoueurEntity;
import fr.uga.m1miage.pc.Partie.repository.PartieJoueurRepository;
import fr.uga.m1miage.pc.Partie.repository.PartieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PartieService {

    @Autowired
    JeuRepository jeuRepository;

    @Autowired
    JoueurRepository joueurRepository;

    @Autowired
    PartieRepository partieRepository;

    @Autowired
    PartieJoueurRepository partieJoueurRepository;

    public PartieJoueurEntity joueurCoup(UUID idJoueur, Long idJeu, CoupEnum coup) {

        JoueurEntity joueur = joueurRepository.findById(idJoueur).orElseThrow();

        PartieEntity partieEnCours = partieRepository.findByJeuIdAndStatut(idJeu,StatutPartieEnum.EN_COURS);

        PartieJoueurEntity partieJoueur = PartieJoueurEntity
                .builder()
                .partie(partieEnCours)
                .coup(coup)
                .joueur(joueur)
                .build();
        partieJoueurRepository.save(partieJoueur);

        if(partieEnCours.getPartiesJoueur().size() == 2) {
            calculerScore(partieEnCours.getPartiesJoueur());
            terminerPartie(partieEnCours);
            creerPartie(joueur.getJeu());
        }

        return partieJoueur;
    }

    public void terminerPartie(PartieEntity partie) {
        partie.setStatut(StatutPartieEnum.TERMINE);
        partieRepository.save(partie);
    }

    public void creerPartie(JeuEntity jeu)  {
        if (jeu.getNombreParties() >= jeu.getParties().size()) {
            PartieEntity partie = PartieEntity
                    .builder()
                    .jeu(jeu)
                    .statut(StatutPartieEnum.EN_COURS)
                    .build();
            partieRepository.save(partie);
        }

    }

    public void calculerScore(List<PartieJoueurEntity> partiesJoueurs) {

        PartieJoueurEntity partieJoueur1 = partiesJoueurs.get(0);
        PartieJoueurEntity partieJoueur2 = partiesJoueurs.get(1);

        if (partieJoueur1.getCoup().equals(CoupEnum.COOPERER) && partieJoueur2.getCoup().equals(CoupEnum.COOPERER)) {
            partieJoueur1.setScore(3);
            partieJoueur2.setScore(3);
        }
        if (partieJoueur1.getCoup().equals(CoupEnum.COOPERER) && partieJoueur2.getCoup().equals(CoupEnum.TRAHIR)) {
            partieJoueur1.setScore(0);
            partieJoueur2.setScore(5);
        }
        if (partieJoueur1.getCoup().equals(CoupEnum.TRAHIR) && partieJoueur2.getCoup().equals(CoupEnum.COOPERER)) {
            partieJoueur1.setScore(5);
            partieJoueur2.setScore(0);
        }
        if (partieJoueur1.getCoup().equals(CoupEnum.TRAHIR) && partieJoueur2.getCoup().equals(CoupEnum.TRAHIR)) {
            partieJoueur1.setScore(1);
            partieJoueur2.setScore(1);
        }
        partieJoueurRepository.saveAll(List.of(partieJoueur1,partieJoueur2));
    }

}
