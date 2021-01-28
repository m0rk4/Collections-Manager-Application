package by.mark.mangareviewer.service;

import by.mark.mangareviewer.domain.Field;

import java.util.List;

public interface FieldService {
    List<Field> getAllFields();
    List<Field> saveFields(Iterable<Field> toSave);
    void deleteFields(Iterable<Field> toDelete);
}
