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
@EqualsAndHashCode(of = {"id", "text"})
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

    @OneToMany(mappedBy = "field")
    private Set<Value> values = new HashSet<>();
}
