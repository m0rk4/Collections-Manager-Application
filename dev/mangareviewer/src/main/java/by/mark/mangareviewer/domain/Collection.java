package by.mark.mangareviewer.domain;

import by.mark.mangareviewer.domain.user.User;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


@Entity
@Data
@ToString(of = {"id", "title"})
@EqualsAndHashCode(of = {"id"})
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String title;

    @JsonView(Views.IdName.class)
    @Column(length = 1000)
    private String description;

    @JsonView(Views.IdName.class)
    private String pic;

    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = false)
    @JsonView(Views.IdName.class)
    private Theme theme;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonView(Views.FullCollection.class)
    private User user;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "collection_field",
            joinColumns = {@JoinColumn(name = "collection_id")},
            inverseJoinColumns = {@JoinColumn(name = "field_id")}
    )
    @JsonView(Views.FullCollection.class)
    private Set<Field> fields = new HashSet<>();

    @OneToMany(mappedBy = "collection", orphanRemoval = true)
    @JsonView(Views.FullCollection.class)
    private List<Item> items = new LinkedList<>();
}
