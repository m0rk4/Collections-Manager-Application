package by.mark.mangareviewer.service.impl;

import by.mark.mangareviewer.domain.Tag;
import by.mark.mangareviewer.repo.TagRepo;
import by.mark.mangareviewer.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepo tagRepo;

    @Autowired
    public TagServiceImpl(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }


    @Override
    public List<Tag> getAllTags() {
        return tagRepo.findAll();
    }

    @Override
    public List<Tag> saveAllTags(Iterable<Tag> tagsToSave) {
        return tagRepo.saveAll(tagsToSave);
    }
}
