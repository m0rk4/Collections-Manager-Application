package by.mark.mangareviewer.service.impl;

import by.mark.mangareviewer.domain.Theme;
import by.mark.mangareviewer.repo.ThemeRepo;
import by.mark.mangareviewer.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepo themeRepo;

    @Autowired
    public ThemeServiceImpl(ThemeRepo themeRepo) {
        this.themeRepo = themeRepo;
    }

    @Override
    public List<Theme> getAllThemes() {
        return themeRepo.findAll();
    }
}
