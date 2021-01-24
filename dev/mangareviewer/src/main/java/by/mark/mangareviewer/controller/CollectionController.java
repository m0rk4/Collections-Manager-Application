package by.mark.mangareviewer.controller;

import by.mark.mangareviewer.domain.*;
import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.service.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("api/collection")
@PreAuthorize("hasAuthority('USER')")
public class CollectionController {

    private final ThemeService themeService;
    private final FieldService fieldService;
    private final CollectionService collectionService;
    private final AuthService authService;
    private final TagService tagService;

    @Autowired
    public CollectionController(ThemeService themeService, FieldService fieldService, CollectionService collectionService, AuthService authService, TagService tagService) {
        this.themeService = themeService;
        this.fieldService = fieldService;
        this.collectionService = collectionService;
        this.authService = authService;
        this.tagService = tagService;
    }

    @GetMapping("theme")
    @JsonView(Views.IdName.class)
    public List<Theme> getAllThemes() {
        return themeService.getAllThemes();
    }

    @GetMapping("tag")
    @JsonView(Views.IdName.class)
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("field")
    @JsonView(Views.IdName.class)
    public List<Field> getAllFields() {
        return fieldService.getAllFields();
    }

    @GetMapping("user/{id}")
    @JsonView(Views.IdName.class)
    public List<Collection> getAllUserCollections(@PathVariable("id") User user) {
        return collectionService.getAllUserCollections(user);
    }

    @PostMapping
    public Collection addNewCollection(
            @RequestBody Collection collection,
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @AuthenticationPrincipal User basicUser
    ) {
        User currentUser = authService.getCurrentUser(basicUser, oAuth2User);
        collection.setFields(new HashSet<>(fieldService.saveFields(collection.getFields())));
        collection.setUser(currentUser);
        return collectionService.addNewCollection(collection);
    }

    @DeleteMapping("{id}")
    public void deleteCollection(@PathVariable("id") Collection collectionToDelete) {
        collectionToDelete.getFields().clear();
        collectionService.deleteCollection(collectionToDelete);
    }

    @PutMapping("{id}")
    public Collection updateCollection(
            @PathVariable("id") Collection collectionFromDb,
            @RequestBody Collection updatedCollection
    ) {
        collectionFromDb.setFields(new HashSet<>(fieldService.saveFields(updatedCollection.getFields())));
        BeanUtils.copyProperties(updatedCollection, collectionFromDb, "id", "user", "fields");
        return collectionService.saveCollection(collectionFromDb);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullCollection.class)
    public Collection getCollection(@PathVariable("id") Collection collectionFromDb) {
        return collectionFromDb;
    }


}
