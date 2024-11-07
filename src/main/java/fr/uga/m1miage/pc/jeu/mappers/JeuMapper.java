package fr.uga.m1miage.pc.jeu.mappers;

import fr.uga.m1miage.pc.jeu.models.JeuEntity;
import fr.uga.m1miage.pc.jeu.response.JeuConnexionResponseDTO;
import fr.uga.m1miage.pc.jeu.response.JeuCreationResponseDTO;
import fr.uga.m1miage.pc.jeu.response.JeuDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JeuMapper {


     JeuCreationResponseDTO mapJeuEntityToJeuCreationResponseDTO(JeuEntity jeu) ;

     JeuConnexionResponseDTO mapJeuEntityToJeuConnexionResponseDTO(JeuEntity jeu) ;

     JeuDTO mapEntityToDTO(JeuEntity jeu) ;

}
