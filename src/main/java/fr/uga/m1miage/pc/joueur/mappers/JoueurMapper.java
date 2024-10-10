package fr.uga.m1miage.pc.joueur.mappers;

import fr.uga.m1miage.pc.joueur.responses.JoueurDTO;
import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JoueurMapper {

    ModelMapper mapper = new ModelMapper();

    public JoueurDTO mapEntityToDTO(JoueurEntity joueur) {
        return mapper.map(joueur, JoueurDTO.class);
    }

    public List<JoueurDTO> mapEntitiesToDTOs(List<JoueurEntity> joueurs) {
        return joueurs.stream().map(this::mapEntityToDTO).toList();
    }
}
