package by.mark.mangareviewer.controller;

import by.mark.mangareviewer.domain.*;
import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.service.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/collection")
public class CollectionController {

    private final ThemeService themeService;
    private final FieldService fieldService;
    private final CollectionService collectionService;
    private final TagService tagService;

    @Autowired
    public CollectionController(ThemeService themeService, FieldService fieldService, CollectionService collectionService, TagService tagService) {
        this.themeService = themeService;
        this.fieldService = fieldService;
        this.collectionService = collectionService;
        this.tagService = tagService;
    }

    @GetMapping
    @JsonView(Views.FullCollection.class)
    public List<Collection> getAllCollections() {
        return collectionService.getAllCollections();
    }

    @GetMapping("theme")
    @JsonView(Views.IdText.class)
    public List<Theme> getAllThemes() {
        return themeService.getAllThemes();
    }

    @GetMapping("tag")
    @JsonView(Views.IdText.class)
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("field")
    @JsonView(Views.IdText.class)
    public List<Field> getAllFields() {
        return fieldService.getAllFields();
    }

    @GetMapping("user/{id}")
    @JsonView(Views.FullCollection.class)
    public List<Collection> getAllUserCollections(@PathVariable("id") User user) {
        return collectionService.getAllUserCollections(user);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullCollection.class)
    public Collection getCollection(@PathVariable("id") Collection collectionFromDb) {
        return collectionFromDb;
    }

    @PostMapping
    @JsonView(Views.FullCollection.class)
    @PreAuthorize("hasAuthority('USER')")
    public Collection addNewCollection(@RequestBody Collection collection) {
        return collectionService.addNewCollection(collection);
    }

    @PutMapping("{id}")
    @JsonView(Views.IdText.class)
    @PreAuthorize("hasAuthority('USER')")
    public Collection updateCollection(
            @PathVariable("id") Collection collectionFromDb,
            @RequestBody Collection updatedCollection
    ) {
        return collectionService.saveCollection(collectionFromDb, updatedCollection);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteCollection(@PathVariable("id") Collection collectionToDelete) {
        collectionService.deleteCollection(collectionToDelete);
    }
}
