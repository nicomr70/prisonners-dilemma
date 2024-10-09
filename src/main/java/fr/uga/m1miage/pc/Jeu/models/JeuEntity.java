package fr.uga.m1miage.pc.Jeu.models;


import fr.uga.m1miage.pc.Jeu.enums.StatutEnum;
import fr.uga.m1miage.pc.Joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.Partie.models.PartieEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "jeu")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JeuEntity {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;


    private int nombreParties;


    @Enumerated(EnumType.STRING)
    private StatutEnum statut;

    @OneToMany(mappedBy = "jeu")
    private List<PartieEntity> parties;

    @OneToMany(mappedBy = "jeu")
    private List<JoueurEntity> joueurs;







}
