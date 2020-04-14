package ar.com.ada.sb.relationship.model.repository;

import ar.com.ada.sb.relationship.model.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("actorRepository")
public interface ActorRepository extends JpaRepository<Actor, Long> {

    // Optional<Actor> findByNameOrGender(String name, String gender);
    // SELECT * FROM Actor WHERE name = ? AND gender = ?
    // SELECT * FROM Actor WHERE name = ? OR gender = ?
}