package ar.com.ada.sb.relationship.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Director")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, length = 100)
    public String name;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    public String bio;

    @OneToMany(mappedBy = "director")
    private List<Film> films;

    public Director(Long id) {
        this.id = id;
    }

}