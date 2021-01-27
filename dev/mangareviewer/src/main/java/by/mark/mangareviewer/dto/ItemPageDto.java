package by.mark.mangareviewer.dto;

import by.mark.mangareviewer.domain.Item;
import by.mark.mangareviewer.domain.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonView(Views.FullItem.class)
public class ItemPageDto {
    private List<Item> items;
    private int currentPage;
    private int totalPages;
}
