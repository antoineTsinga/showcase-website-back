package org.onyx.showcasebackend.payload.request;

public class ItemRequest {
    private long id;
    private String name;

    private int estimatedPrice;
    private String image;
    private Long fashionCollection;
    private int category;
    private int genre;
    private Boolean inGallery;
    private Boolean inCatalog;

    public ItemRequest(long id, String name, int estimatedPrice, String image, Long fashionCollection, int category, int genre, Boolean inGallery, Boolean inCatalog) {
        this.id = id;
        this.name = name;
        this.estimatedPrice = estimatedPrice;
        this.image = image;
        this.fashionCollection = fashionCollection;
        this.category = category;
        this.genre = genre;
        this.inGallery = inGallery;
        this.inCatalog = inCatalog;
    }

    public ItemRequest() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getFashionCollection() {
        return fashionCollection;
    }

    public void setFashionCollection(Long fashionCollection) {
        this.fashionCollection = fashionCollection;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public Boolean getInGallery() {
        return inGallery;
    }

    public void setInGallery(Boolean inGallery) {
        this.inGallery = inGallery;
    }

    public Boolean getInCatalog() {
        return inCatalog;
    }

    public void setInCatalog(Boolean inCatalog) {
        this.inCatalog = inCatalog;
    }
}
