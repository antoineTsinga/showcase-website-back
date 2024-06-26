package org.onyx.showcasebackend.dao;

import org.onyx.showcasebackend.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "clients", path = "clients")

public interface ClientRepository extends JpaRepository<Client, Long> {


    boolean existsByEmail(String email);
}