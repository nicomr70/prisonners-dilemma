package fr.uga.m1miage.pc.jeu.services;


import fr.uga.m1miage.pc.jeu.enums.StatutJeuEnum;
import fr.uga.m1miage.pc.jeu.models.JeuEntity;
import fr.uga.m1miage.pc.jeu.repository.JeuRepository;
import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.joueur.repository.JoueurRepository;
import fr.uga.m1miage.pc.partie.enums.StatutPartieEnum;
import fr.uga.m1miage.pc.partie.models.PartieEntity;
import fr.uga.m1miage.pc.partie.repository.PartieRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JeuService {


    @Autowired
    JoueurRepository joueurRepository ;
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
            throw new IllegalArgumentException("Le nombre de joueurs est atteint");
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




}
