package ar.com.ada.sb.relationship.model.mapper.circular.dependency;

import ar.com.ada.sb.relationship.model.dto.FilmDTO;
import ar.com.ada.sb.relationship.model.entity.Film;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FilmCycleMapper extends DataCycleMapper<FilmDTO, Film> {
    FilmCycleMapper MAPPER = Mappers.getMapper(FilmCycleMapper.class);
}