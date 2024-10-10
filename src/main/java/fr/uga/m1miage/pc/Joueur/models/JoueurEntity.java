package fr.uga.m1miage.pc.Joueur.models;


import fr.uga.m1miage.pc.Jeu.models.JeuEntity;
import fr.uga.m1miage.pc.Joueur.enums.StrategieEnum;
import fr.uga.m1miage.pc.Partie.models.PartieJoueurEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "joueur")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoueurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nomJoueur;

    private Boolean abandon ;

    @Enumerated(EnumType.STRING)
    private StrategieEnum strategie;

    @ManyToOne
    private JeuEntity jeu;

    @OneToMany(mappedBy = "joueur")
    private List<PartieJoueurEntity> partieJoueurs;

}
