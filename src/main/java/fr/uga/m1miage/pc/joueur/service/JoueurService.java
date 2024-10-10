package fr.uga.m1miage.pc.joueur.service;


import fr.uga.m1miage.pc.joueur.enums.StrategieEnum;
import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.joueur.repository.JoueurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JoueurService {
    @Autowired
    private JoueurRepository joueurRepository;

    public JoueurEntity abandonnerJeu(UUID idJoueur, StrategieEnum strategie) {
        JoueurEntity joueur = joueurRepository.findById(idJoueur).orElseThrow();
        joueur.setStrategie(strategie);
        return joueurRepository.save(joueur);
    }
}
