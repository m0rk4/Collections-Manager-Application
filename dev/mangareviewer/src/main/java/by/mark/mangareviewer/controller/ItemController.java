package by.mark.mangareviewer.controller;

import by.mark.mangareviewer.domain.Item;
import by.mark.mangareviewer.domain.Value;
import by.mark.mangareviewer.domain.Views;
import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.service.AuthService;
import by.mark.mangareviewer.service.ItemService;
import by.mark.mangareviewer.service.TagService;
import by.mark.mangareviewer.service.ValueService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/item")
@PreAuthorize("hasAuthority('USER')")
public class ItemController {

    private final AuthService authService;
    private final ItemService itemService;
    private final TagService tagService;
    private final ValueService valueService;

    @Autowired
    public ItemController(AuthService authService, ItemService itemService, TagService tagService, ValueService valueService) {
        this.authService = authService;
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
        toUpdateItem.getValues().forEach(uv -> {
            Long fieldId = uv.getField().getId();
            Optional<Value> first = itemFromDb.getValues().stream()
                    .filter(v -> v.getField().getId().equals(fieldId))
                    .findFirst();
            if (first.isPresent()) {
                Value value = first.get();
                value.setValue(uv.getValue());
            } else {
                uv.setItem(itemFromDb);
                itemFromDb.getValues().add(valueService.saveValue(uv));
            }
        });
        BeanUtils.copyProperties(toUpdateItem, itemFromDb, "id", "tags", "values", "comments");
        return itemService.updateItem(itemFromDb);
    }

    @PostMapping("{id}/like")
    public void likeItem(
            @PathVariable("id") Item itemFromDb,
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @AuthenticationPrincipal User basicUser
    ) {
        User currentUser = authService.getCurrentUser(basicUser, oAuth2User);
        itemService.processLike(itemFromDb, currentUser);
    }
}
