package by.mark.mangareviewer.service;

import by.mark.mangareviewer.domain.Item;
import by.mark.mangareviewer.domain.user.User;

public interface ItemService {
    Item addNewItem(Item toAddItem);
    void deleteItem(Item itemToDelete);
    Item updateItem(Item itemFromDb, Item toUpdateItem);
    void processLike(Item item, User user);
}
