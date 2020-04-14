package ar.com.ada.sb.relationship.model.mapper.circular.dependency;

import ar.com.ada.sb.relationship.model.dto.ActorDTO;
import ar.com.ada.sb.relationship.model.entity.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ActorCycleMapper extends DataCycleMapper<ActorDTO, Actor> {
    ActorCycleMapper MAPPER = Mappers.getMapper(ActorCycleMapper.class);
}