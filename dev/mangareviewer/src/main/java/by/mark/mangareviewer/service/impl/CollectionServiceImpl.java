package by.mark.mangareviewer.service.impl;

import by.mark.mangareviewer.domain.Collection;
import by.mark.mangareviewer.domain.Field;
import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.repo.CollectionRepo;
import by.mark.mangareviewer.service.CollectionService;
import by.mark.mangareviewer.service.FieldService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepo collectionRepo;
    private final FieldService fieldService;

    @Autowired
    public CollectionServiceImpl(CollectionRepo collectionRepo, FieldService fieldService) {
        this.collectionRepo = collectionRepo;
        this.fieldService = fieldService;
    }

    @Override
    public Collection addNewCollection(Collection collection, User currentUser) {
        Set<Field> newFields = collection.getFields();
        List<Field> savedFields = fieldService.saveFields(newFields);
        collection.setFields(new HashSet<>(savedFields));

        collection.setUser(currentUser);
        return collectionRepo.save(collection);
    }

    @Override
    public List<Collection> getAllUserCollections(User user) {
        return collectionRepo.findAllByUser(user);
    }

    @Override
    public void deleteCollection(Collection collection) {
        collectionRepo.delete(collection);
    }

    @Override
    public Collection saveCollection(Collection collectionFromDb, Collection updatedCollection) {
        Set<Field> newFields = updatedCollection.getFields();
        List<Field> savedFields = fieldService.saveFields(newFields);
        collectionFromDb.setFields(new HashSet<>(savedFields));

        BeanUtils.copyProperties(updatedCollection, collectionFromDb, "id", "user", "fields");
        return collectionRepo.save(collectionFromDb);
    }
}
