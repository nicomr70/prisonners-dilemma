package fr.uga.m1miage.pc.Partie.repository;



import fr.uga.m1miage.pc.Partie.enums.StatutPartieEnum;
import fr.uga.m1miage.pc.Partie.models.PartieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PartieRepository extends JpaRepository<PartieEntity, UUID> {
    PartieEntity findByJeuIdAndStatut(Long idJeu,StatutPartieEnum statut);
}
