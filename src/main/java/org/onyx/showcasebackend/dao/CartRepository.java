package org.onyx.showcasebackend.dao;

import org.onyx.showcasebackend.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "carts", path = "carts")

public interface CartRepository extends JpaRepository<Cart, Long> {
}