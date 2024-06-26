package org.onyx.showcasebackend.dao;

import org.onyx.showcasebackend.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "admins", path = "admins")
public interface AdminRepository extends JpaRepository<Admin, Long> {
}