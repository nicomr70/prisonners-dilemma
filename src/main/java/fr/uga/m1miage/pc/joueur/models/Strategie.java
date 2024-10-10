package fr.uga.m1miage.pc.joueur.models;

import fr.uga.m1miage.pc.joueur.enums.StrategieEnum;
import fr.uga.m1miage.pc.partie.enums.CoupEnum;
import fr.uga.m1miage.pc.partie.enums.StatutPartieEnum;
import fr.uga.m1miage.pc.partie.models.PartieEntity;
import fr.uga.m1miage.pc.partie.models.PartieJoueurEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class Strategie {
    private CoupEnum toujoursTrahir() {
        return CoupEnum.TRAHIR;
    }


    private CoupEnum toujoursCooperer() {
        return CoupEnum.COOPERER;
    }

    private CoupEnum aleatoire() {
        CoupEnum[] coups = {CoupEnum.COOPERER, CoupEnum.TRAHIR};
        int randomNumber = new Random().nextInt(2);
        return coups[randomNumber];
    }

    private CoupEnum donnantDonnant(List<PartieEntity> parties) {
        Optional<PartieEntity> partieEnCours = recupererPartieEncours(parties);
        Optional<PartieEntity> precedentePartie = partieEnCours.stream().filter(partie -> {
            return partie.getOrdre() == (partieEnCours.get().getOrdre()-1);
        }).findAny();
        List<PartieJoueurEntity> partieJoueurEntities = precedentePartie.get().getPartiesJoueur();
        Optional<PartieJoueurEntity> partieJoueurAdverse = partieJoueurEntities.stream().filter(partieJoueur -> {
            return partieJoueur.getJoueur().getAbandon() == null;
        }).findAny();

        return partieJoueurAdverse.get().getCoup();
    }

    private CoupEnum rancunier(List<PartieEntity> parties) {
        List<PartieJoueurEntity> partieJoueurEntities = parties.stream().map(partie -> (PartieJoueurEntity) partie.getPartiesJoueur()).toList();
        PartieJoueurEntity partieJoueurCoupTrahir = partieJoueurEntities.stream().filter(partieJoueurEntity -> partieJoueurEntity.getCoup().equals(CoupEnum.TRAHIR)).findAny().orElse(null);

        return partieJoueurCoupTrahir == null ? CoupEnum.TRAHIR : CoupEnum.COOPERER;
    }

    private Optional<PartieEntity> recupererPartieEncours(List<PartieEntity> parties) {
        return parties.stream().filter(partie -> partie.getStatut().equals(StatutPartieEnum.EN_COURS)).findAny();
    }

    public CoupEnum getCoup(List<PartieEntity> parties, StrategieEnum strategie) {
        if (strategie.equals(StrategieEnum.TOUJOURS_TRAHIR)) {
            return toujoursTrahir();
        }
        if (strategie.equals(StrategieEnum.ALEATOIRE)) {
            return aleatoire();
        }
        if (strategie.equals(StrategieEnum.DONNANT_DONNANT)) {
            return donnantDonnant(parties);
        }
        if (strategie.equals(StrategieEnum.RANCUNIER)) {
            return rancunier(parties);
        }
        return toujoursCooperer();
    }
}
