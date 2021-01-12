package by.mark.mangareviewer.service.impl;

import by.mark.mangareviewer.domain.Field;
import by.mark.mangareviewer.repo.FieldRepo;
import by.mark.mangareviewer.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldServiceImpl implements FieldService {

    private final FieldRepo fieldRepo;

    @Autowired
    public FieldServiceImpl(FieldRepo fieldRepo) {
        this.fieldRepo = fieldRepo;
    }

    @Override
    public List<Field> getAllFields() {
        return fieldRepo.findAll();
    }

    @Override
    public List<Field> saveFields(Iterable<Field> toSave) {
       return fieldRepo.saveAll(toSave);
    }
}
