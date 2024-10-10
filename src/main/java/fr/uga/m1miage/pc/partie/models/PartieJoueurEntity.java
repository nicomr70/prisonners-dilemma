package fr.uga.m1miage.pc.partie.models;


import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.partie.enums.CoupEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@Table(name = "partie_joueur")
@NoArgsConstructor
@AllArgsConstructor
public class PartieJoueurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    private int score;

    @Enumerated(EnumType.STRING)
    private CoupEnum coup;

    @ManyToOne
    private JoueurEntity joueur;

    @ManyToOne
    private PartieEntity partie;


}
