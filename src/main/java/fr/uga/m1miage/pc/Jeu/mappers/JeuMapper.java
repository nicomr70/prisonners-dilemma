package fr.uga.m1miage.pc.Jeu.mappers;

import fr.uga.m1miage.pc.Jeu.models.JeuEntity;
import fr.uga.m1miage.pc.Jeu.response.JeuConnexionResponseDTO;
import fr.uga.m1miage.pc.Jeu.response.JeuCreationResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class JeuMapper {
    ModelMapper mapper = new ModelMapper();

    public JeuCreationResponseDTO mapJeuEntityToJeuCreationResponseDTO(JeuEntity jeu) {
        return mapper.map(jeu, JeuCreationResponseDTO.class);
    }

    public JeuConnexionResponseDTO mapJeuEntityToJeuConnexionResponseDTO(JeuEntity jeu) {
        return mapper.map(jeu, JeuConnexionResponseDTO.class);
    }
}
