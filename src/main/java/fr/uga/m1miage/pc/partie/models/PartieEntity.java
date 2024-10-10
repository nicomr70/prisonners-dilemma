package fr.uga.m1miage.pc.partie.models;

import fr.uga.m1miage.pc.jeu.models.JeuEntity;
import fr.uga.m1miage.pc.partie.enums.StatutPartieEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "partie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private StatutPartieEnum statut;

    @ManyToOne
    private JeuEntity jeu;

    @OneToMany(mappedBy = "partie")
    private List<PartieJoueurEntity> partiesJoueur;


}
