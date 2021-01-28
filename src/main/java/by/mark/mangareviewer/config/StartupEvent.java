package by.mark.mangareviewer.config;

import by.mark.mangareviewer.service.impl.IndexingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartupEvent implements ApplicationListener<ApplicationReadyEvent> {

    private final IndexingService service;

    @Autowired
    public StartupEvent(IndexingService service) {
        this.service = service;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            service.initiateIndexing();
        } catch (InterruptedException e) {
            log.error("Failed to reindex entities ",e);
        }
    }
}