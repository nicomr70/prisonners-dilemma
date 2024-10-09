package fr.uga.m1miage.pc.Partie.requests;

import fr.uga.m1miage.pc.Partie.enums.CoupEnum;
import lombok.Data;

@Data
public class CoupRequest {
    private CoupEnum coup;
}
