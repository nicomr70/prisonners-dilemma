package fr.uga.m1miage.pc.joueur.mappers;

import fr.uga.m1miage.pc.joueur.responses.JoueurDTO;
import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface JoueurMapper {
    JoueurDTO toDTO(JoueurEntity joueur);

    List<JoueurDTO> toDTOs(List<JoueurEntity> joueurs);
}
