package by.mark.mangareviewer.service;

import by.mark.mangareviewer.domain.Value;

import java.util.List;

public interface ValueService {
    List<Value> saveAllValues(Iterable<Value> valuesToSave);
    Value saveValue(Value valueToSave);
    void deleteValues(Iterable<Value> valuesToDelete);
}
