package by.mark.mangareviewer.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString(of = {"id", "value"})
@EqualsAndHashCode(of = {"id", "value"})
public class Value {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdText.class)
    private String value;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @JsonView(Views.IdText.class)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    @JsonView(Views.IdText.class)
    private Field field;

}
