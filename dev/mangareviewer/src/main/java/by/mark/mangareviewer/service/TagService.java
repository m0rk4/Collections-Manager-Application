package by.mark.mangareviewer.service;

import by.mark.mangareviewer.domain.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();
    List<Tag> saveAllTags(Iterable<Tag> tagsToSave);
}
