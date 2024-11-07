package fr.uga.m1miage.pc.joueur.strategies;

import fr.uga.m1miage.pc.partie.enums.CoupEnum;
import fr.uga.m1miage.pc.partie.models.PartieEntity;
import fr.uga.m1miage.pc.partie.models.PartieJoueurEntity;

import java.util.List;

public class RancunierStrategie implements StrategieInterface{

    @Override
    public CoupEnum getCoup(List<PartieEntity> parties) {
        List<PartieJoueurEntity> partieJoueurEntities = parties.stream().map(partie -> (PartieJoueurEntity) partie.getPartiesJoueur()).toList();
        PartieJoueurEntity partieJoueurCoupTrahir = partieJoueurEntities.stream().filter(partieJoueurEntity -> partieJoueurEntity.getCoup().equals(CoupEnum.TRAHIR)).findAny().orElse(null);

        return partieJoueurCoupTrahir == null ? CoupEnum.TRAHIR : CoupEnum.COOPERER;
    }
}
