package ar.com.ada.sb.relationship.service;

import ar.com.ada.sb.relationship.component.BusinessLogicExceptionComponent;
import ar.com.ada.sb.relationship.exception.ApiEntityError;
import ar.com.ada.sb.relationship.exception.BusinessLogicException;
import ar.com.ada.sb.relationship.model.dto.FilmDTO;
import ar.com.ada.sb.relationship.model.entity.Actor;
import ar.com.ada.sb.relationship.model.entity.Director;
import ar.com.ada.sb.relationship.model.entity.Film;
import ar.com.ada.sb.relationship.model.mapper.circular.dependency.CycleAvoidingMappingContext;
import ar.com.ada.sb.relationship.model.mapper.circular.dependency.FilmCycleMapper;
import ar.com.ada.sb.relationship.model.repository.ActorRepository;
import ar.com.ada.sb.relationship.model.repository.DirectorRepository;
import ar.com.ada.sb.relationship.model.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("filmServices")
public class FilmServices implements Services<FilmDTO> {

    @Autowired @Qualifier("businessLogicExceptionComponent")
    private BusinessLogicExceptionComponent logicExceptionComponent;

    @Autowired @Qualifier("filmRepository")
    private FilmRepository filmRepository;

    @Autowired @Qualifier("actorRepository")
    private ActorRepository actorRepository;

    @Autowired @Qualifier("directorRepository")
    private DirectorRepository directorRepository;

    private FilmCycleMapper filmCycleMapper = FilmCycleMapper.MAPPER;

    @Autowired @Qualifier("cycleAvoidingMappingContext")
    private CycleAvoidingMappingContext context;

    @Override
    public List<FilmDTO> findAll() {
        List<Film> all = filmRepository.findAll();
        List<FilmDTO> filmDtoList = filmCycleMapper.toDto(all, context);
        return filmDtoList;
    }

    public FilmDTO findFilmById(Long id) {
        Optional<Film> byIdOptional = filmRepository.findById(id);
        FilmDTO filmDTO = null;

        if (byIdOptional.isPresent()) {
            Film film = byIdOptional.get();
            filmDTO = filmCycleMapper.toDto(film, context);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Film", id);
        }

        return filmDTO;
    }

    @Override
    public FilmDTO save(FilmDTO dto) {
        Film filmToSave = filmCycleMapper.toEntity(dto, context);
        Film filmSaved = filmRepository.save(filmToSave);
        FilmDTO filmDtoSaved = filmCycleMapper.toDto(filmSaved, context);
        return filmDtoSaved;
    }

    public FilmDTO updateFilm(FilmDTO filmDtoToUpdate, Long id) {
        Optional<Film> byIdOptional = filmRepository.findById(id);
        FilmDTO filmDtoUpdated = null;

        if (byIdOptional.isPresent()) {
            Film filmById = byIdOptional.get();
            filmDtoToUpdate.setId(filmById.getId());
            Film filmToUpdate = filmCycleMapper.toEntity(filmDtoToUpdate, context);
            Film filmUpdated = filmRepository.save(filmToUpdate);
            filmDtoUpdated = filmCycleMapper.toDto(filmUpdated, context);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Film", id);
        }

        return filmDtoUpdated;
    }

    @Override
    public void delete(Long id) {
        Optional<Film> byIdOptional = filmRepository.findById(id);

        if (byIdOptional.isPresent()) {
            Film filmToDelete = byIdOptional.get();
            filmRepository.delete(filmToDelete);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Film", id);
        }
    }

    public FilmDTO addActorToFilm(Long actorId, Long filmId) {
        Optional<Film> filmByIdOptional = filmRepository.findById(filmId);
        Optional<Actor> actorByIdOptional = actorRepository.findById(actorId);
        FilmDTO filmDtoWithNewActor = null;

        if (!filmByIdOptional.isPresent())
            logicExceptionComponent.throwExceptionEntityNotFound("Film", filmId);

        if (!actorByIdOptional.isPresent())
            logicExceptionComponent.throwExceptionEntityNotFound("Actor", actorId);

        Film film = filmByIdOptional.get();
        Actor actorToAdd = actorByIdOptional.get();

        boolean hasActorInFilm = film.getActors().stream()
                .anyMatch(actor -> actor.getName().equals(actorToAdd.getName()));

        if (!hasActorInFilm) {
            film.addActor(actorToAdd);
            Film filmWithNewActor = filmRepository.save(film);
            filmDtoWithNewActor = filmCycleMapper.toDto(filmWithNewActor, context);
        } else {
            ApiEntityError apiEntityError = new ApiEntityError(
                    "Actor",
                    "AlreadyExist",
                    "The Actor with id '" + actorId + "' already exist in the film"
            );
            throw new BusinessLogicException(
                    "Actor already exist in the film",
                    HttpStatus.BAD_REQUEST,
                    apiEntityError
            );
        }

        return filmDtoWithNewActor;
    }

    public FilmDTO addDirectorToFilm(Long directorId, Long filmId) {
        Optional<Film> filmByIdOptional = filmRepository.findById(filmId);
        Optional<Director> directorByIdOptional = directorRepository.findById(directorId);
        FilmDTO filmDtoWithDirector = null;

        if (!filmByIdOptional.isPresent())
            logicExceptionComponent.throwExceptionEntityNotFound("Film", filmId);

        if (!directorByIdOptional.isPresent())
            logicExceptionComponent.throwExceptionEntityNotFound("Director", directorId);

        Film film = filmByIdOptional.get();
        Director directorToSet = directorByIdOptional.get();

        film.setDirector(directorToSet);
        Film filmWithDirector = filmRepository.save(film);
        filmDtoWithDirector = filmCycleMapper.toDto(filmWithDirector, context);

        return filmDtoWithDirector;
    }
}