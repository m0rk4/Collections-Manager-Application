package by.mark.mangareviewer.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.swing.text.View;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString(of = {"id", "value"})
@EqualsAndHashCode(of = {"id", "value"})
public class Value {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String value;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @JsonView(Views.IdName.class)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    @JsonView(Views.IdName.class)
    private Field field;

}
