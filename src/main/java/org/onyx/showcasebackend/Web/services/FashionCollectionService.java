package org.onyx.showcasebackend.Web.services;

import org.onyx.showcasebackend.dao.FashionCollectionRepository;
import org.onyx.showcasebackend.entities.FashionCollection;
import org.onyx.showcasebackend.payload.request.PagingHeaders;
import org.onyx.showcasebackend.payload.request.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class FashionCollectionService {

    @Autowired
    private FashionCollectionRepository fashionCollectionRepository;

    public List<FashionCollection> getFashionCollections(){
        List<FashionCollection> fashionCollections = new ArrayList<FashionCollection>();
        fashionCollectionRepository.findAll().forEach(fashionCollection -> fashionCollections.add(fashionCollection));
        return fashionCollections;
    }

    public FashionCollection getFashionCollectionsById(Long id){
        return fashionCollectionRepository.findById(id).get();
    }

    public void save(FashionCollection fashionCollection){
        fashionCollectionRepository.save(fashionCollection);
    }

    public void delete(Long id){
        fashionCollectionRepository.deleteById(id);
    }

    public void update(FashionCollection fashionCollection, Long id){
        fashionCollection.setId(id);
        fashionCollectionRepository.save(fashionCollection);
    }
    /**
     * get element using Criteria.
     *
     * @param spec    *
     * @param headers pagination data
     * @param sort    sort criteria
     * @return retrieve elements with pagination
     */
    public PagingResponse get(Specification<FashionCollection> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            List<FashionCollection> entities = get(spec, sort);
            return new PagingResponse((long) entities.size(), 0L, 0L, 0L, 0L, Collections.singletonList(entities));
        }
    }

    private boolean isRequestPaged(HttpHeaders headers) {
        return headers.containsKey(PagingHeaders.PAGE_NUMBER.getName()) && headers.containsKey(PagingHeaders.PAGE_SIZE.getName());
    }

    private Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
        int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_NUMBER.getName())).get(0));
        int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_SIZE.getName())).get(0));
        return PageRequest.of(page, size, sort);
    }

    /**
     * get elements using Criteria.
     *
     * @param spec     *
     * @param pageable pagination data
     * @return retrieve elements with pagination
     */
    public PagingResponse get(Specification<FashionCollection> spec, Pageable pageable) {
        Page<FashionCollection> page = fashionCollectionRepository.findAll(spec, pageable);
        List<FashionCollection> content = page.getContent();
        return new PagingResponse(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), Collections.singletonList(content));
    }

    /**
     * get elements using Criteria.
     *
     * @param spec *
     * @return elements
     */
    public List<FashionCollection> get(Specification<FashionCollection> spec, Sort sort) {
        return fashionCollectionRepository.findAll(spec, sort);
    }




}
