package by.mark.mangareviewer.service.impl;

import by.mark.mangareviewer.domain.Item;
import by.mark.mangareviewer.domain.Views;
import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.dto.EventType;
import by.mark.mangareviewer.dto.ObjectType;
import by.mark.mangareviewer.repo.ItemRepo;
import by.mark.mangareviewer.service.ItemService;
import by.mark.mangareviewer.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepo itemRepo;
    private final BiConsumer<EventType, Item> wsSender;


    @Autowired
    public ItemServiceImpl(ItemRepo itemRepo, WsSender wsSender) {
        this.itemRepo = itemRepo;
        this.wsSender = wsSender.getSender(ObjectType.LIKE, Views.IdName.class);
    }

    @Override
    public Item addNewItem(Item toAddItem) {
        return itemRepo.save(toAddItem);
    }

    @Override
    public void deleteItem(Item itemToDelete) {
        itemRepo.delete(itemToDelete);
    }

    @Override
    public Item updateItem(Item itemToUpdate) {
        return itemRepo.save(itemToUpdate);
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
