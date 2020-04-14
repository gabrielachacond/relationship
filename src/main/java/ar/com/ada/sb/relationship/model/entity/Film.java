package ar.com.ada.sb.relationship.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Film")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "director_id", nullable = false)
    private Director director;

    @ManyToMany
    @JoinTable(
            name = "Actor_has_Film",
            joinColumns = @JoinColumn(name = "Film_id"),
            inverseJoinColumns = @JoinColumn(name = "Actor_id"))
    private Set<Actor> actors;

    public Film(Long id) {
        this.id = id;
    }
}