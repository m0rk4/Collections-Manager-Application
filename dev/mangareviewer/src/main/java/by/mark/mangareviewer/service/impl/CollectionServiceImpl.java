package by.mark.mangareviewer.service.impl;

import by.mark.mangareviewer.domain.Collection;
import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.repo.CollectionRepo;
import by.mark.mangareviewer.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepo collectionRepo;

    @Autowired
    public CollectionServiceImpl(CollectionRepo collectionRepo) {
        this.collectionRepo = collectionRepo;
    }

    @Override
    public Collection addNewCollection(Collection collection) {
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
    public Collection saveCollection(Collection toSave) {
        return collectionRepo.save(toSave);
    }
}
