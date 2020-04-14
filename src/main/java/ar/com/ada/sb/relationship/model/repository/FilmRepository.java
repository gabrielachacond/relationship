package ar.com.ada.sb.relationship.model.repository;

import ar.com.ada.sb.relationship.model.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
}