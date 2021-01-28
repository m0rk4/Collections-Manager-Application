package by.mark.mangareviewer.service;

import by.mark.mangareviewer.domain.Item;

import java.util.List;

public interface SearchService {
    List<Item> findByQuery(String query);
    List<Item> findByTag(String tag);
}
