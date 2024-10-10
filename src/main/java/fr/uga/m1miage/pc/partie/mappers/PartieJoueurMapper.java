package fr.uga.m1miage.pc.partie.mappers;

import fr.uga.m1miage.pc.partie.models.PartieJoueurEntity;
import fr.uga.m1miage.pc.partie.responses.PartieJoueurDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class PartieJoueurMapper {

    private final ModelMapper mapper = new ModelMapper();

    public PartieJoueurDTO toDto(PartieJoueurEntity partieJoueurEntity) {
        return mapper.map(partieJoueurEntity, PartieJoueurDTO.class);
    }

    public PartieJoueurEntity toEntity(PartieJoueurDTO partieJoueurDTO) {
        return mapper.map(partieJoueurDTO, PartieJoueurEntity.class);
    }

    public List<PartieJoueurDTO> toDtoList(List<PartieJoueurEntity> partieJoueurEntities) {
        return partieJoueurEntities.stream()
                .map(this::toDto)
                .toList();
    }




}
