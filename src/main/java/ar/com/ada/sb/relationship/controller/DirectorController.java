package ar.com.ada.sb.relationship.controller;

import ar.com.ada.sb.relationship.model.dto.DirectorDTO;
import ar.com.ada.sb.relationship.service.DirectorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/directors")
public class DirectorController {

    @Autowired @Qualifier("directorServices")
    private DirectorServices directorServices;

    @GetMapping({ "", "/" }) // localhost:8080/directors y localhost:8080/directors/ [GET]
    public ResponseEntity getAllActors() {
        List<DirectorDTO> all = directorServices.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping({ "/{id}", "/{id}/" }) // localhost:8080/directors/1 y localhost:8080/directors/1/ [GET]
    public ResponseEntity getDirectorById(@PathVariable Long id) {
        DirectorDTO directorById = directorServices.findDirectorById(id);
        return ResponseEntity.ok(directorById);
    }

    @PostMapping({ "", "/" }) // localhost:8080/directors y localhost:8080/directors/ [POST]
    public ResponseEntity addNewDirector(@Valid @RequestBody DirectorDTO directorDTO) throws URISyntaxException {
        DirectorDTO directorSaved = directorServices.save(directorDTO);
        return ResponseEntity
                .created(new URI("/directors/" + directorSaved.getId()))
                .body(directorSaved);
    }

    @PutMapping({ "/{id}", "/{id}/" }) // localhost:8080/directors/1 y localhost:8080/directors/1/ [PUT]
    public ResponseEntity updateDirectorById(@Valid @RequestBody DirectorDTO directorDTO, @PathVariable Long id) {
        DirectorDTO directorUpdated = directorServices.updateDirector(directorDTO, id);
        return ResponseEntity.ok(directorUpdated);
    }

    @DeleteMapping({ "/{id}", "/{id}/" }) // localhost:8080/directors/1 y localhost:8080/directors/1/ [DELETE]
    public ResponseEntity deleteDirector(@PathVariable Long id) {
        directorServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}