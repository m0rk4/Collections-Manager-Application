package by.mark.mangareviewer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
public class Field {

    @JsonView(Views.Id.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonView(Views.IdName.class)
    private String text;
    @JsonView(Views.IdName.class)
    @JsonProperty(value = "isMarkDownSupported")
    private boolean isMarkDownSupported;


    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "field_value",
            joinColumns = {@JoinColumn(name = "field_id")},
            inverseJoinColumns = {@JoinColumn(name = "value_id")}
    )
    private Set<Value> values = new HashSet<>();



}
