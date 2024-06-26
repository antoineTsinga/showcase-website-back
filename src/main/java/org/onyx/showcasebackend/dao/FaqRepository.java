package org.onyx.showcasebackend.dao;

import org.onyx.showcasebackend.entities.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "faqs", path = "faqs")

public interface FaqRepository extends JpaRepository<Faq, Long> {
}