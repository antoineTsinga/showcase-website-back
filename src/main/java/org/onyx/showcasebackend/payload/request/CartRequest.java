package org.onyx.showcasebackend.payload.request;

import java.util.Collection;

public class CartRequest {

    private Long id;
    private Long client;
    private Collection<Long> items;

    public CartRequest() {
    }

    public CartRequest(Long id, Long client, Collection<Long> items) {
        this.id = id;
        this.client = client;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public Collection<Long> getItems() {
        return items;
    }

    public void setItems(Collection<Long> items) {
        this.items = items;
    }
}
