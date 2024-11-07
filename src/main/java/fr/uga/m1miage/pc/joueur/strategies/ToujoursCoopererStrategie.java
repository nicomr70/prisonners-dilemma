package fr.uga.m1miage.pc.joueur.strategies;

import fr.uga.m1miage.pc.partie.enums.CoupEnum;
import fr.uga.m1miage.pc.partie.models.PartieEntity;

import java.util.List;

public class ToujoursCoopererStrategie implements StrategieInterface{

    @Override
    public CoupEnum getCoup(List<PartieEntity> parties) {
        return CoupEnum.COOPERER;
    }
}
