package fr.uga.m1miage.pc.partie.service;


import fr.uga.m1miage.pc.jeu.models.JeuEntity;
import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.joueur.repository.JoueurRepository;
import fr.uga.m1miage.pc.partie.enums.CoupEnum;
import fr.uga.m1miage.pc.partie.enums.StatutPartieEnum;
import fr.uga.m1miage.pc.partie.models.PartieEntity;
import fr.uga.m1miage.pc.partie.models.PartieJoueurEntity;
import fr.uga.m1miage.pc.partie.repository.PartieJoueurRepository;
import fr.uga.m1miage.pc.partie.repository.PartieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PartieService {


    private final JoueurRepository joueurRepository;


    private final PartieRepository partieRepository;


    private final PartieJoueurRepository partieJoueurRepository;

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
