package ar.com.ada.sb.relationship.controller;

import ar.com.ada.sb.relationship.model.dto.FilmDTO;
import ar.com.ada.sb.relationship.service.FilmServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmServices filmServices;

    @GetMapping({ "", "/" }) // localhost:8080/films y localhost:8080/films/ [GET]
    public ResponseEntity getAllActors() {
        List<FilmDTO> all = filmServices.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping({ "/{id}", "/{id}/" }) // localhost:8080/films/1 y localhost:8080/films/1/ [GET]
    public ResponseEntity getDirectorById(@PathVariable Long id) {
        FilmDTO directorById = filmServices.findFilmById(id);
        return ResponseEntity.ok(directorById);
    }

    @PostMapping({ "", "/" }) // localhost:8080/films y localhost:8080/films/ [POST]
    public ResponseEntity addNewDirector(@Valid @RequestBody FilmDTO filmDTO) throws URISyntaxException {
        FilmDTO filmSaved = filmServices.save(filmDTO);
        return ResponseEntity
                .created(new URI("/directors/" + filmSaved.getId()))
                .body(filmSaved);
    }

    @PutMapping({ "/{id}", "/{id}/" }) // localhost:8080/films/1 y localhost:8080/films/1/ [PUT]
    public ResponseEntity updateDirectorById(@Valid @RequestBody FilmDTO filmDTO, @PathVariable Long id) {
        FilmDTO filmUpdated = filmServices.updateFilm(filmDTO, id);
        return ResponseEntity.ok(filmUpdated);
    }

    @DeleteMapping({ "/{id}", "/{id}/" }) // localhost:8080/films/1 y localhost:8080/films/1/ [DELETE]
    public ResponseEntity deleteDirector(@PathVariable Long id) {
        filmServices.delete(id);
        return ResponseEntity.noContent().build();
    }

    // localhost:8080/films/1/actors/20 y localhost:8080/films/1/actors/20/ [PUT]
    @PutMapping({"/{filmId}/actors/{actorId}", "/{fildId}/actors/{actorId}"})
    public ResponseEntity addActorToFilm(@PathVariable Long filmId, @PathVariable Long actorId) {
        FilmDTO filmDtoWithNewActor = filmServices.addActorToFilm(actorId, filmId);
        return ResponseEntity.ok(filmDtoWithNewActor);
    }

    // localhost:8080/films/1/directors/20 y localhost:8080/films/1/directors/20/ [PUT]
    @PutMapping({"/{filmId}/directors/{directorId}", "/{fildId}/directors/{directorId}"})
    public ResponseEntity addDirectorToFilm(@PathVariable Long filmId, @PathVariable Long directorId) {
        FilmDTO filmDtoWithDirector = filmServices.addDirectorToFilm(directorId, filmId);
        return ResponseEntity.ok(filmDtoWithDirector);
    }
}