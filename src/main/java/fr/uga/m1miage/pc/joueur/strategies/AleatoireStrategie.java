package fr.uga.m1miage.pc.joueur.strategies;

import fr.uga.m1miage.pc.partie.enums.CoupEnum;
import fr.uga.m1miage.pc.partie.models.PartieEntity;

import java.security.SecureRandom;
import java.util.List;

public class AleatoireStrategie implements StrategieInterface{

    private SecureRandom random = new SecureRandom();

    @Override
    public CoupEnum getCoup(List<PartieEntity> parties) {
        CoupEnum[] coups = {CoupEnum.COOPERER, CoupEnum.TRAHIR};
        int randomNumber = this.random.nextInt(2);
        return coups[randomNumber];
    }
}
