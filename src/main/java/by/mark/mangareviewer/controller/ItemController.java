package by.mark.mangareviewer.controller;

import by.mark.mangareviewer.domain.Item;
import by.mark.mangareviewer.domain.Views;
import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.dto.ItemPageDto;
import by.mark.mangareviewer.service.AuthService;
import by.mark.mangareviewer.service.ItemService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/item")
public class ItemController {

    private final AuthService authService;
    private final ItemService itemService;

    @Autowired
    public ItemController(AuthService authService, ItemService itemService) {
        this.authService = authService;
        this.itemService = itemService;
    }

    @GetMapping("search")
    @JsonView(Views.FullItem.class)
    public List<Item> itemsBySearch(
            @RequestParam(value = "query", required = false)
                    String query,
            @RequestParam(value = "tag", required = false)
                    String tag
    ) {
        return itemService.findByQueryAndTag(query, tag);
    }

    @GetMapping
    @JsonView(Views.FullItem.class)
    public ItemPageDto itemsList(
            @PageableDefault(
                    size = 3,
                    sort = {"creationTime"},
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return itemService.findAll(pageable);
    }

    @PostMapping
    @JsonView(Views.FullItem.class)
    @PreAuthorize("hasAuthority('USER')")
    public Item addNewItem(@RequestBody Item item) {
        return itemService.addNewItem(item);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullItem.class)
    @PreAuthorize("hasAuthority('USER')")
    public Item updateItem(
            @PathVariable("id") Item itemFromDb,
            @RequestBody Item toUpdateItem
    ) {
        return itemService.updateItem(itemFromDb, toUpdateItem);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteItem(@PathVariable("id") Item item) {
        itemService.deleteItem(item);
    }

    @PostMapping("{id}/like")
    @PreAuthorize("hasAuthority('USER')")
    public void likeItem(
            @PathVariable("id") Item itemFromDb,
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @AuthenticationPrincipal User basicUser
    ) {
        User currentUser = authService.getCurrentUser(basicUser, oAuth2User);
        itemService.processLike(itemFromDb, currentUser);
    }
}
