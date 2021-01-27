package by.mark.mangareviewer.service;

import by.mark.mangareviewer.domain.Collection;
import by.mark.mangareviewer.domain.user.User;

import java.util.List;

public interface CollectionService {
    Collection addNewCollection(Collection collection);

    List<Collection> getAllUserCollections(User user);

    void deleteCollection(Collection collection);

    Collection saveCollection(Collection collectionFromDb, Collection updatedCollection);
}
