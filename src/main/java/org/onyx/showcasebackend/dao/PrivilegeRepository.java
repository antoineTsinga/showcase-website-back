package org.onyx.showcasebackend.dao;

import org.onyx.showcasebackend.entities.Privilege;
import org.onyx.showcasebackend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "privileges", path = "privileges")
public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {

    Privilege findByActionAndEntityAndRole(String action, String entity, Role role);

}
