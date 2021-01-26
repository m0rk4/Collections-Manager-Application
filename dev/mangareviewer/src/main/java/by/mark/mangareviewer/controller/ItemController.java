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

    @Autowired
    public ItemController(AuthService authService, ItemService itemService) {
        this.authService = authService;
        this.itemService = itemService;
    }

    @PostMapping
    @JsonView(Views.IdName.class)
    public Item addNewItem(@RequestBody Item item) {
        return itemService.addNewItem(item);
    }

    @DeleteMapping("{id}")
    public void deleteItem(@PathVariable("id") Item item) {
        itemService.deleteItem(item);
    }

    @PutMapping("{id}")
    @JsonView(Views.IdName.class)
    public Item updateItem(
            @PathVariable("id") Item itemFromDb,
            @RequestBody Item toUpdateItem
    ) {
        return itemService.updateItem(itemFromDb, toUpdateItem);
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
