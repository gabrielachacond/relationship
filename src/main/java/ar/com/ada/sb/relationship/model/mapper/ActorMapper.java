package ar.com.ada.sb.relationship.model.mapper;

import ar.com.ada.sb.relationship.model.dto.ActorDTO;
import ar.com.ada.sb.relationship.model.entity.Actor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ActorMapper extends DataMapper<ActorDTO, Actor> {

    //para que lo mande a Entity
    Actor toEntity(ActorDTO dto);

    ActorDTO toDto(Actor entity);
// por si acaso
    default Actor fromId(Long id) {
        if (id == null) return null;
        return new Actor(id);
    }
}