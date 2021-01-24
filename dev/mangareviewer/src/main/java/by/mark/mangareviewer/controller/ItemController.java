package by.mark.mangareviewer.controller;

import by.mark.mangareviewer.domain.Item;
import by.mark.mangareviewer.domain.Value;
import by.mark.mangareviewer.domain.Views;
import by.mark.mangareviewer.service.ItemService;
import by.mark.mangareviewer.service.TagService;
import by.mark.mangareviewer.service.ValueService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @DeleteMapping("{id}")
    public void deleteItem(@PathVariable("id") Item item) {
        item.getTags().clear();
        valueService.deleteValues(item.getValues());
        itemService.deleteItem(item);
    }

    @PutMapping("{id}")
    @JsonView(Views.IdName.class)
    public Item updateItem(
            @PathVariable("id") Item itemFromDb,
            @RequestBody Item toUpdateItem
    ) {
        itemFromDb.setTags(new HashSet<>(tagService.saveAllTags(toUpdateItem.getTags())));
        itemFromDb.getValues().forEach(v -> {
            String newValue = toUpdateItem.getValues().stream()
                    .filter(uv -> uv.getField().getId().equals(v.getField().getId()))
                    .collect(Collectors.toList())
                    .get(0)
                    .getValue();
            v.setValue(newValue);
        });
        BeanUtils.copyProperties(toUpdateItem, itemFromDb, "id", "tags", "values");
        return itemService.updateItem(itemFromDb);
    }
}
