package fr.uga.m1miage.pc.Jeu.service;


import fr.uga.m1miage.pc.Jeu.enums.StatutEnum;
import fr.uga.m1miage.pc.Jeu.models.JeuEntity;
import fr.uga.m1miage.pc.Jeu.repository.JeuRepository;
import fr.uga.m1miage.pc.Joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.Joueur.repository.JoueurRepository;
import fr.uga.m1miage.pc.Partie.enums.StatutPartieEnum;
import fr.uga.m1miage.pc.Partie.models.PartieEntity;
import fr.uga.m1miage.pc.Partie.repository.PartieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .statut(StatutEnum.EN_ATTENTE)
                .build();
        JeuEntity jeuEnregistrer = jeuRepository.save(jeu);

        JoueurEntity joueur = JoueurEntity
                .builder()
                .nomJoueur(nomJoueur)
                .jeu(jeuEnregistrer)
                .build();
        joueurRepository.save(joueur);

        PartieEntity partie = PartieEntity
                .builder()
                .statut(StatutPartieEnum.EN_COURS)
                .jeu(jeuEnregistrer)
                .build();
        partieRepository.save(partie);

        return jeuEnregistrer;


    }

    public JeuEntity JoindreJeu(String pseudo, Long id) {
        JeuEntity jeu = jeuRepository.findById(id).orElseThrow();
        JoueurEntity secondJoueur = JoueurEntity
                .builder()
                .jeu(jeu)
                .nomJoueur(pseudo)
                .build();
        JoueurEntity joueurEnregistre = joueurRepository.save(secondJoueur);
        PartieEntity partie = PartieEntity
                .builder()
                .jeu(jeu)
                .statut(StatutPartieEnum.EN_COURS)
                .build();
        PartieEntity partieEnregistre = partieRepository.save(partie);
        jeu.setStatut(StatutEnum.EN_COURS);

        return jeuRepository.save(jeu);
    }


}
