package org.onyx.showcasebackend.dao;

import org.onyx.showcasebackend.entities.FashionCollection;
import org.onyx.showcasebackend.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long>, JpaRepository<Order,Long> {

    Page<Order> findAll(Specification<Order> spec, Pageable pageable);

    List<Order> findAll(Specification<Order> spec, Sort sort);

    List<Order> findAllByClientId(Long id);

    List<Order> findOrderByAppointment(Date appointment);

    List<Order> findOrderByAppointmentAfterAndAppointmentBefore(Date dateAfter, Date dateBefore);
}