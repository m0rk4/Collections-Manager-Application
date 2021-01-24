package by.mark.mangareviewer.service.impl;

import by.mark.mangareviewer.domain.Value;
import by.mark.mangareviewer.repo.ValueRepo;
import by.mark.mangareviewer.service.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValueServiceImpl implements ValueService {

    private final ValueRepo valueRepo;

    @Autowired
    public ValueServiceImpl(ValueRepo valueRepo) {
        this.valueRepo = valueRepo;
    }

    @Override
    public List<Value> saveAllValues(Iterable<Value> valuesToSave) {
        return valueRepo.saveAll(valuesToSave);
    }

    @Override
    public void deleteValues(Iterable<Value> valuesToDelete) {
        valueRepo.deleteAll(valuesToDelete);
    }
}
