package by.mark.mangareviewer.service.impl;

import by.mark.mangareviewer.domain.Item;
import by.mark.mangareviewer.domain.Tag;
import by.mark.mangareviewer.domain.Value;
import by.mark.mangareviewer.domain.Views;
import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.dto.EventType;
import by.mark.mangareviewer.dto.ItemPageDto;
import by.mark.mangareviewer.dto.ObjectType;
import by.mark.mangareviewer.repo.ItemRepo;
import by.mark.mangareviewer.service.ItemService;
import by.mark.mangareviewer.service.SearchService;
import by.mark.mangareviewer.service.TagService;
import by.mark.mangareviewer.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

@Service
public class ItemServiceImpl implements ItemService {

    private final TagService tagService;
    private final ItemRepo itemRepo;
    private final BiConsumer<EventType, Item> wsSender;
    private final SearchService searchService;

    @Autowired
    public ItemServiceImpl(ItemRepo itemRepo, WsSender wsSender, TagService tagService, SearchService searchService) {
        this.itemRepo = itemRepo;
        this.wsSender = wsSender.getSender(ObjectType.LIKE, Views.FullItem.class);
        this.tagService = tagService;
        this.searchService = searchService;
    }

    @Override
    public Item addNewItem(Item toAddItem) {
        Set<Tag> newTags = toAddItem.getTags();
        List<Tag> savedTags = tagService.saveAllTags(newTags);
        toAddItem.setTags(new HashSet<>(savedTags));

        Set<Value> values = toAddItem.getValues();
        values.forEach(v -> v.setItem(toAddItem));

        return itemRepo.save(toAddItem);
    }

    @Override
    public void deleteItem(Item itemToDelete) {
        itemRepo.delete(itemToDelete);
    }

    @Override
    public Item updateItem(Item itemFromDb, Item toUpdateItem) {
        Set<Tag> newTags = toUpdateItem.getTags();
        List<Tag> savedTags = tagService.saveAllTags(newTags);
        itemFromDb.setTags(new HashSet<>(savedTags));

        String newTitle = toUpdateItem.getTitle();
        itemFromDb.setTitle(newTitle);

        Set<Value> oldValues = itemFromDb.getValues();
        Set<Value> newValues = toUpdateItem.getValues();

        oldValues.clear();
        oldValues.addAll(newValues);
        oldValues.forEach(v -> v.setItem(toUpdateItem));

        return itemRepo.save(itemFromDb);
    }

    @Override
    public void processLike(Item item, User user) {
        Set<User> likers = item.getLikers();
        if (likers.contains(user)) {
            likers.remove(user);
            wsSender.accept(EventType.REMOVE, item);
        } else {
            likers.add(user);
            wsSender.accept(EventType.CREATE, item);
        }
        item.setLikers(new HashSet<>(likers));
        itemRepo.save(item);
    }

    @Override
    public ItemPageDto findAll(Pageable pageable) {
        Page<Item> page = itemRepo.findAll(pageable);
        return new ItemPageDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }

    @Override
    public List<Item> findByQueryAndTag(String query, String tag) {
        Set<Item> resultSet = new HashSet<>();
        if (query != null && !query.isEmpty()) {
            List<Item> resQuery = searchService.findByQuery(query);
            resultSet.addAll(resQuery);
        }
        if (tag != null && !tag.isEmpty()) {
            List<Item> resTag = searchService.findByTag(tag);
            resultSet.addAll(resTag);
        }
        return new ArrayList<>(resultSet);
    }
}
