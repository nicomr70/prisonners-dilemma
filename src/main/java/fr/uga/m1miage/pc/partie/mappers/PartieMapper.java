package fr.uga.m1miage.pc.partie.mappers;

import fr.uga.m1miage.pc.partie.models.PartieEntity;
import fr.uga.m1miage.pc.partie.responses.PartieDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PartieMapper {
    ModelMapper mapper = new ModelMapper();

    public PartieDTO mapEntityToDTO(PartieEntity partie) {
        return mapper.map(partie, PartieDTO.class);
    }

    public List<PartieDTO> mapEntitiesToDTOs(List<PartieEntity> parties) {
        return parties.stream().map(this::mapEntityToDTO).toList();
    }
}
