package org.onyx.showcasebackend.Web.controllers;

import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.onyx.showcasebackend.Web.services.FashionCollectionService;
import org.onyx.showcasebackend.entities.Client;
import org.onyx.showcasebackend.entities.FashionCollection;
import org.onyx.showcasebackend.payload.request.PagingHeaders;
import org.onyx.showcasebackend.payload.request.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import javax.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api")
public class FashionCollectionController {
    @Autowired
    private FashionCollectionService fashionCollectionService;



  /*  @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/fashion_collections")
    private ResponseEntity<?> getAllFashionCollections(){
        HashMap<String,Object> data = new HashMap<>();
        List<FashionCollection> items = fashionCollectionService.getFashionCollections();
        data.put("results", items);
        data.put("count", items.size());
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
*/

    @Transactional
    @GetMapping(value = "/fashion_collections", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> get(
            @And({
                    @Spec(path = "name", params = "name", spec = Like.class),
                    @Spec(path = "creationAt", params = "creationAt", spec = Equal.class),
                    @Spec(path = "creationAt", params = {"creationAtGt", "creationAtLt"}, spec = DateBetween.class)
            }) Specification<FashionCollection> spec,
            Sort sort,
            @RequestHeader HttpHeaders headers) {
        final PagingResponse response = fashionCollectionService.get(spec, headers, sort);

        HashMap<String,Object> data = new HashMap<>();
        data.put("results", response.getElements().stream().findFirst().stream().findFirst().get());
        data.put("count", ((List)(response.getElements().stream().findFirst().stream().findFirst().get())).size());

        return new ResponseEntity<>(data, returnHttpHeaders(response), HttpStatus.OK);
    }

    public HttpHeaders returnHttpHeaders(PagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }


    @GetMapping(value = "/fashion_collections/{id}")
    public FashionCollection getIFashionCollection(@PathVariable("id") Long id) {
        return fashionCollectionService.getFashionCollectionsById(id);
    }

    @DeleteMapping(value = "/fashion_collections/{id}")
    public void deleteFashionCollection(@PathVariable("id") Long id) {
        fashionCollectionService.delete(id);
    }

    @PostMapping(value = "/fashion_collections")
    public FashionCollection saveFashionCollection(@RequestBody FashionCollection fashionCollection)
    {
        fashionCollectionService.save(fashionCollection);
        return fashionCollection;

    }

    @PutMapping(value = "/fashion_collections/{id}")
    public FashionCollection update(@RequestBody FashionCollection fashionCollection, @PathVariable("id") Long id) {
        fashionCollectionService.update(fashionCollection, id);
        return fashionCollection;
    }

}
