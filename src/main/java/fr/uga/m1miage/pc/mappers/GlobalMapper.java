package fr.uga.m1miage.pc.mappers;

import fr.uga.m1miage.pc.jeu.models.JeuEntity;
import fr.uga.m1miage.pc.jeu.requests.JeuConnexionRequestDTO;
import fr.uga.m1miage.pc.jeu.response.JeuConnexionResponseDTO;
import fr.uga.m1miage.pc.jeu.response.JeuCreationResponseDTO;
import fr.uga.m1miage.pc.jeu.response.JeuDTO;
import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.joueur.responses.JoueurDTO;
import fr.uga.m1miage.pc.partie.models.PartieEntity;
import fr.uga.m1miage.pc.partie.models.PartieJoueurEntity;
import fr.uga.m1miage.pc.partie.responses.PartieDTO;
import fr.uga.m1miage.pc.partie.responses.PartieDetailsDTO;
import fr.uga.m1miage.pc.partie.responses.PartieJoueurDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GlobalMapper {
    GlobalMapper INSTANCE = Mappers.getMapper(GlobalMapper.class);
    JeuCreationResponseDTO mapJeuEntityToJeuCreationResponseDTO(JeuEntity jeu) ;
    JeuConnexionResponseDTO mapJeuEntityToJeuConnexionResponseDTO(JeuEntity jeu) ;
    JeuConnexionRequestDTO mapJeuEntityToJeuConnexionRequestDTO(JeuEntity jeu) ;

    JeuDTO mapJeuEntityToJeuDto(JeuEntity entity);
    @Mapping(source = "nomJoueur", target = "nom")
    JoueurDTO mapJoueurEntityToJoueurDTO(JoueurEntity joueur);

    @Mapping(target = "partiesJoueurs", source = "partiesJoueur")
    PartieDetailsDTO mapEntityToDetailsDTO(PartieEntity partie);

    PartieJoueurDTO toDto(PartieJoueurEntity partieJoueurEntity);

    PartieJoueurEntity toEntity(PartieJoueurDTO partieJoueurDTO);

    PartieDTO mapEntityToDTO(PartieEntity partie);

}
