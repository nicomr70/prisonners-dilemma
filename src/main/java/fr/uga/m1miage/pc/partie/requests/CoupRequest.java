package fr.uga.m1miage.pc.partie.requests;

import fr.uga.m1miage.pc.partie.enums.CoupEnum;
import lombok.*;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CoupRequest {
    private CoupEnum coup;
}
