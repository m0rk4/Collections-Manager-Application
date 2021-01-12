package by.mark.mangareviewer.domain;

import by.mark.mangareviewer.domain.user.User;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.swing.text.View;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
public class Collection {
    @JsonView(Views.Id.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonView(Views.IdName.class)
    private String title;
    @JsonView(Views.IdName.class)
    private String description;
    @JsonView(Views.IdName.class)
    private String pic;

    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = false)
    @JsonView(Views.IdName.class)
    private Theme theme;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "collection_field",
            joinColumns = {@JoinColumn(name = "collection_id")},
            inverseJoinColumns = {@JoinColumn(name = "field_id")}
    )
    private Set<Field> fields = new HashSet<>();

    @OneToMany(mappedBy = "collection")
    private Set<Item> items = new HashSet<>();
}
