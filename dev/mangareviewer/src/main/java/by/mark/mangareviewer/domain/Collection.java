package by.mark.mangareviewer.domain;

import by.mark.mangareviewer.domain.user.User;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.swing.text.View;
import java.time.LocalDateTime;
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

    @JsonView(Views.IdText.class)
    private String title;

    @JsonView(Views.IdText.class)
    @Column(length = 1000)
    private String description;

    @JsonView(Views.IdText.class)
    private String pic;

    @Column(updatable = false)
    @JsonView(Views.IdText.class)
    private LocalDateTime creationDate;

    @PrePersist
    void setCreationDate() {
        this.creationDate = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = false)
    @JsonView(Views.IdText.class)
    private Theme theme;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonView({Views.FullCollection.class, Views.FullItem.class})
    private User user;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "collection_field",
            joinColumns = {@JoinColumn(name = "collection_id")},
            inverseJoinColumns = {@JoinColumn(name = "field_id")}
    )
    @JsonView(Views.FullCollection.class)
    private Set<Field> fields = new HashSet<>();

    @OneToMany(mappedBy = "collection")
    @JsonView(Views.FullCollection.class)
    private List<Item> items = new LinkedList<>();
}
