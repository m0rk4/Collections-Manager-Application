package by.mark.mangareviewer.service.impl;

import by.mark.mangareviewer.domain.Item;
import by.mark.mangareviewer.repo.ItemRepo;
import by.mark.mangareviewer.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepo itemRepo;

    @Autowired
    public ItemServiceImpl(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
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
}
