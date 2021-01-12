package by.mark.mangareviewer.controller;

import by.mark.mangareviewer.domain.Collection;
import by.mark.mangareviewer.domain.Field;
import by.mark.mangareviewer.domain.Theme;
import by.mark.mangareviewer.domain.Views;
import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.service.*;
import com.fasterxml.jackson.annotation.JsonView;
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


    @Autowired
    public CollectionController(ThemeService themeService, FieldService fieldService, CollectionService collectionService, AuthService authService) {
        this.themeService = themeService;
        this.fieldService = fieldService;
        this.collectionService = collectionService;
        this.authService = authService;
    }

    @GetMapping("theme")
    @JsonView(Views.IdName.class)
    public List<Theme> getAllThemes() {
        return themeService.getAllThemes();
    }

    @GetMapping("field")
    @JsonView(Views.IdName.class)
    public List<Field> getAllFields() {
        return fieldService.getAllFields();
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

    @GetMapping("user/{id}")
    @JsonView(Views.IdName.class)
    public List<Collection> getAllUserCollections(@PathVariable("id") User user) {
        return collectionService.getAllUserCollections(user);
    }

}
