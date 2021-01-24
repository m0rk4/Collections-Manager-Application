package by.mark.mangareviewer.service;

import by.mark.mangareviewer.domain.Item;

public interface ItemService {
    Item addNewItem(Item toAddItem);
    void deleteItem(Item itemToDelete);
    Item updateItem(Item itemToUpdate);
}
