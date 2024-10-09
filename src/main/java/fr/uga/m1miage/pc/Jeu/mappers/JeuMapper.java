package fr.uga.m1miage.pc.Jeu.mappers;

import fr.uga.m1miage.pc.Jeu.models.JeuEntity;
import fr.uga.m1miage.pc.Jeu.response.JeuConnexionResponseDTO;
import fr.uga.m1miage.pc.Jeu.response.JeuCreationResponseDTO;
import fr.uga.m1miage.pc.Jeu.response.JeuDTO;
import fr.uga.m1miage.pc.Joueur.mappers.JoueurMapper;
import fr.uga.m1miage.pc.Partie.mappers.PartieMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JeuMapper {
    ModelMapper mapper = new ModelMapper();

    @Autowired
    JoueurMapper joueurMapper;

    @Autowired
    PartieMapper partieMapper;

    public JeuCreationResponseDTO mapJeuEntityToJeuCreationResponseDTO(JeuEntity jeu) {
        return mapper.map(jeu, JeuCreationResponseDTO.class);
    }

    public JeuConnexionResponseDTO mapJeuEntityToJeuConnexionResponseDTO(JeuEntity jeu) {
        return mapper.map(jeu, JeuConnexionResponseDTO.class);
    }

    public JeuDTO mapEntityToDTO(JeuEntity jeu) {
        return JeuDTO.builder()
                .id(jeu.getId())
                .statut(jeu.getStatut())
                .nombreParties(jeu.getNombreParties())
                .joueurs(joueurMapper.mapEntitiesToDTOs(jeu.getJoueurs()))
                .parties(partieMapper.mapEntitiesToDTOs(jeu.getParties()))
                .build();
    }
}
