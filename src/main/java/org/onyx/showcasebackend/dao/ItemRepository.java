package org.onyx.showcasebackend.dao;

import org.onyx.showcasebackend.entities.FashionCollection;
import org.onyx.showcasebackend.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "items", path = "items")
public interface ItemRepository extends JpaRepository<Item,Long> {
    Item findItemByName(String name);

    List<Item> findAll(Specification<Item>spec, Sort sort);


    Page<Item> findAll(Specification<Item> spec, Pageable pageable);
}
