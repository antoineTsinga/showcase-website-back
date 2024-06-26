package org.onyx.showcasebackend.dao;

import org.onyx.showcasebackend.entities.FashionCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "fashion_collections", path = "fashion_collections")

public interface FashionCollectionRepository extends PagingAndSortingRepository<FashionCollection, Long>, JpaRepository<FashionCollection, Long> {

    Page<FashionCollection> findAll(Specification<FashionCollection> spec, Pageable pageable);

    List<FashionCollection> findAll(Specification<FashionCollection> spec, Sort sort);

    List<FashionCollection> findFashionCollectionByName(String name);
}