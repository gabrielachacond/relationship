package ar.com.ada.sb.relationship.model.mapper;

import ar.com.ada.sb.relationship.model.dto.FilmDTO;
import ar.com.ada.sb.relationship.model.entity.Film;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface FilmMapper extends DataMapper<FilmDTO, Film> {

    Film toEntity(FilmDTO dto);

    FilmDTO toDto(Film entity);

    default Film fromId(Long id) {
        if (id == null) return null;
        return new Film(id);
    }
}