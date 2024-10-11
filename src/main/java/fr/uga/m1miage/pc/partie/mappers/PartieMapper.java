package fr.uga.m1miage.pc.partie.mappers;

import fr.uga.m1miage.pc.partie.models.PartieEntity;
import fr.uga.m1miage.pc.partie.responses.PartieDTO;
import fr.uga.m1miage.pc.partie.responses.PartieDetailsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PartieMapper {
    ModelMapper mapper = new ModelMapper();

    @Autowired
    PartieJoueurMapper partieJoueurMapper;

    public PartieDTO mapEntityToDTO(PartieEntity partie) {
        return mapper.map(partie, PartieDTO.class);
    }

    public PartieDetailsDTO mapEntityToDetailsDTO(PartieEntity partie) {
        PartieDetailsDTO partieDetails = mapper.map(partie, PartieDetailsDTO.class);
        partieDetails.setPartiesJoueurs(partieJoueurMapper.toDtoList(partie.getPartiesJoueur()));
        return partieDetails;
    }

    public List<PartieDTO> mapEntitiesToDTOs(List<PartieEntity> parties) {
        return parties.stream().map(this::mapEntityToDTO).toList();
    }
}
