package by.mark.mangareviewer.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    private String pic;
    private Theme theme;

    @OneToMany(mappedBy = "collection")
    private Set<Item> items = new HashSet<>();


}
