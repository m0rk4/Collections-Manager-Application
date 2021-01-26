package by.mark.mangareviewer.service.impl;

import by.mark.mangareviewer.domain.*;
import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.dto.EventType;
import by.mark.mangareviewer.dto.ObjectType;
import by.mark.mangareviewer.repo.ItemRepo;
import by.mark.mangareviewer.service.ItemService;
import by.mark.mangareviewer.service.TagService;
import by.mark.mangareviewer.service.ValueService;
import by.mark.mangareviewer.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

@Service
public class ItemServiceImpl implements ItemService {

    private final ValueService valueService;
    private final TagService tagService;
    private final ItemRepo itemRepo;
    private final BiConsumer<EventType, Item> wsSender;

    @Autowired
    public ItemServiceImpl(ItemRepo itemRepo, WsSender wsSender, TagService tagService, ValueService valueService) {
        this.itemRepo = itemRepo;
        this.wsSender = wsSender.getSender(ObjectType.LIKE, Views.IdName.class);
        this.tagService = tagService;
        this.valueService = valueService;
    }

    @Override
    public Item addNewItem(Item toAddItem) {
        Set<Tag> newTags = toAddItem.getTags();
        List<Tag> savedTags = tagService.saveAllTags(newTags);
        toAddItem.setTags(new HashSet<>(savedTags));

        Item savedItem = itemRepo.save(toAddItem);

        Set<Value> itemValues = toAddItem.getValues();
        itemValues.forEach(v -> v.setItem(savedItem));
        List<Value> savedValues = valueService.saveAllValues(itemValues);
        savedItem.setValues(new HashSet<>(savedValues));

        return savedItem;
    }

    @Override
    public void deleteItem(Item itemToDelete) {
        itemToDelete.getTags().clear();
        valueService.deleteValues(itemToDelete.getValues());
        itemRepo.delete(itemToDelete);
    }

    @Override
    public Item updateItem(Item itemFromDb, Item toUpdateItem) {
        Set<Tag> newTags = toUpdateItem.getTags();
        List<Tag> savedTags = tagService.saveAllTags(newTags);
        itemFromDb.setTags(new HashSet<>(savedTags));

        Set<Value> oldValues = itemFromDb.getValues();

        toUpdateItem.getValues().forEach(uv -> {
            Field currField = uv.getField();
            Long currFieldId = currField.getId();

            Optional<Value> oldValueOptional = oldValues.stream()
                    .filter(v -> v.getField().getId().equals(currFieldId))
                    .findFirst();

            if (oldValueOptional.isPresent()) {
                Value oldValue = oldValueOptional.get();
                String newValueText = uv.getValue();
                oldValue.setValue(newValueText);
            } else {
                uv.setItem(itemFromDb);
                Value newValue = valueService.saveValue(uv);
                oldValues.add(newValue);
            }
        });

        BeanUtils.copyProperties(toUpdateItem, itemFromDb,
                "id", "tags", "values", "comments");
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


}
