package by.mark.mangareviewer.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
