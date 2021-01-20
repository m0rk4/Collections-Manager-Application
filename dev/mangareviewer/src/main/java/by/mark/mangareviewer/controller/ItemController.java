package by.mark.mangareviewer.controller;

import by.mark.mangareviewer.domain.Item;
import by.mark.mangareviewer.domain.Value;
import by.mark.mangareviewer.domain.Views;
import by.mark.mangareviewer.service.ItemService;
import by.mark.mangareviewer.service.TagService;
import by.mark.mangareviewer.service.ValueService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/item")
@PreAuthorize("hasAuthority('USER')")
public class ItemController {

    private final ItemService itemService;
    private final TagService tagService;
    private final ValueService valueService;


    @Autowired
    public ItemController(ItemService itemService, TagService tagService, ValueService valueService) {
        this.itemService = itemService;
        this.tagService = tagService;
        this.valueService = valueService;
    }

    @PostMapping
    @JsonView(Views.IdName.class)
    public Item addNewItem(@RequestBody Item item) {
        item.setTags(new HashSet<>(tagService.saveAllTags(item.getTags())));
        Item savedItem = itemService.addNewItem(item);
        Set<Value> itemValues = item.getValues();
        itemValues.forEach(v -> v.setItem(savedItem));
        savedItem.setValues(new HashSet<>(valueService.saveAllValues(itemValues)));
        return savedItem;
    }
}
