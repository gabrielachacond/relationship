package ar.com.ada.sb.relationship.model.mapper.circular.dependency;

import ar.com.ada.sb.relationship.model.dto.DirectorDTO;
import ar.com.ada.sb.relationship.model.entity.Director;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DirectorCycleMapper extends DataCycleMapper<DirectorDTO, Director> {
    DirectorCycleMapper MAPPER = Mappers.getMapper(DirectorCycleMapper.class);
}