package fr.uga.m1miage.pc.joueur.strategies;

import fr.uga.m1miage.pc.joueur.enums.StrategieEnum;
import fr.uga.m1miage.pc.partie.enums.CoupEnum;
import fr.uga.m1miage.pc.partie.models.PartieEntity;

import java.util.List;

public class Strategie {
    public CoupEnum getCoup(List<PartieEntity> parties, StrategieEnum strategieEnum) {
        StrategieInterface strategieImpl = StrategieFactory.getStrategie(strategieEnum);
        return strategieImpl.getCoup(parties);
    }
}
