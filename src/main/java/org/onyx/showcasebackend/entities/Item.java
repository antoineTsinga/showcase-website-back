package org.onyx.showcasebackend.entities;


import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static org.onyx.showcasebackend.entities.Category.CHEMISES;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private int estimatedPrice;
    private boolean isInCatalog ;
    private boolean isInGallery;
    private String image;



    @ManyToOne
    @JoinColumn()
    private FashionCollection fashionCollection;
    @JsonIgnore
    @ManyToMany
    private Collection<Cart> carts;

    @Enumerated(EnumType.ORDINAL)
    private Category category;

    @Enumerated(EnumType.ORDINAL)
    private Genre genre;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item() {
    }


    public Item(String name, int estimatedPrice, boolean isInCatalog, boolean isInGallery, String image, FashionCollection fashionCollection,  Category category, Genre genre) {
        this.name = name;
        this.estimatedPrice = estimatedPrice;
        this.isInCatalog = isInCatalog;
        this.isInGallery = isInGallery;
        this.image = image;
        this.fashionCollection = fashionCollection;
        this.category = category;
        this.genre = genre;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(int estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public boolean isInCatalog() {
        return isInCatalog;
    }

    public void setInCatalog(boolean inCatalog) {
        isInCatalog = inCatalog;
    }

    public boolean isInGallery() {
        return isInGallery;
    }

    public void setInGallery(boolean inGallery) {
        isInGallery = inGallery;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public FashionCollection getFashionCollection() {
        return fashionCollection;
    }

    public void setFashionCollection(FashionCollection fashionCollection) {
        this.fashionCollection = fashionCollection;
    }

    public Collection<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Collection<Cart> carts) {
        this.carts = carts;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

}
