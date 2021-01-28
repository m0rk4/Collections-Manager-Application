package by.mark.mangareviewer.service.impl;

import by.mark.mangareviewer.domain.Item;
import by.mark.mangareviewer.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@Slf4j
public class SearchServiceImpl implements SearchService {
    private final EntityManager entityManager;

    @Autowired
    public SearchServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Item> findByQuery(String query) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Item.class).get();

        Query luceneSingleWordQuery = qb
                .keyword()
                .wildcard()
                .onFields("tags.name", "title", "collection.title", "tags.name", "collection.theme.text")
                .matching(query + "*")
                .createQuery();

        Query luceneFuzzyWordQuery = qb
                .keyword()
                .fuzzy()
                .withThreshold(0.6F)
                .onFields("tags.name", "title", "collection.title", "tags.name",
                        "comments.text", "values.value", "collection.theme.text", "collection.user.name")
                .matching(query)
                .createQuery();

        javax.persistence.Query jpaSingleWordQuery = fullTextEntityManager
                .createFullTextQuery(luceneSingleWordQuery, Item.class);

        javax.persistence.Query jpaFuzzyWordQuery = fullTextEntityManager
                .createFullTextQuery(luceneFuzzyWordQuery, Item.class);

        Set<Item> resultSet = new HashSet<>();
        try {
            List<Item> singleQueryRes = jpaSingleWordQuery.getResultList();
            List<Item> fuzzyQueryRes = jpaFuzzyWordQuery.getResultList();
            if (singleQueryRes != null) {
                resultSet.addAll(singleQueryRes);
            }
            if (fuzzyQueryRes != null) {
                resultSet.addAll(fuzzyQueryRes);
            }
        } catch (NoResultException nre) {
            log.error("JpaQuery error {}", nre.getMessage(), nre);
        }
        return new ArrayList<>(resultSet);
    }

    @Override
    public List<Item> findByTag(String tag) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Item.class).get();

        Query luceneQuery = qb
                .keyword()
                .onField("tags.name")
                .matching(tag)
                .createQuery();

        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Item.class);

        List<Item> resultSet = null;
        try {
            resultSet = jpaQuery.getResultList();
        } catch (NoResultException nre) {
            log.error("JpaQuery error {}", nre.getMessage(), nre);
        }
        return resultSet;
    }
}