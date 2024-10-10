package fr.uga.m1miage.pc.jeu.repository;

import fr.uga.m1miage.pc.jeu.models.JeuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JeuRepository extends JpaRepository<JeuEntity,Long> {
}
