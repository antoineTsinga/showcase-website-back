package org.onyx.showcasebackend.Web.controllers;

import net.kaczmarzyk.spring.data.jpa.domain.DateBetween;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.onyx.showcasebackend.Web.services.OrderService;
import org.onyx.showcasebackend.entities.Client;
import org.onyx.showcasebackend.entities.FashionCollection;
import org.onyx.showcasebackend.entities.Item;
import org.onyx.showcasebackend.entities.Order;
import org.onyx.showcasebackend.payload.request.OrderRequest;
import org.onyx.showcasebackend.payload.request.PagingHeaders;
import org.onyx.showcasebackend.payload.request.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/orders")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> get(
            @And({
                    @Spec(path = "appointment", params = "appointment", spec = Equal.class),
                    @Spec(path = "appointment", params = {"appointmentGt", "appointmentLt"}, spec = DateBetween.class),
                    @Spec(path = "creationAt", params = "creationAt", spec = Equal.class),
                    @Spec(path = "creationAt", params = {"creationAtGt", "creationAtLt"}, spec = DateBetween.class)
            }) Specification<Order> spec,
            Sort sort,
            @RequestHeader HttpHeaders headers) {
        final PagingResponse response = orderService.get(spec, headers, sort);

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

    @GetMapping(value = "/orders/{id}")
    public Order getOrder(@PathVariable("id") Long id) {
        return orderService.getOrderById(id);
    }


    @GetMapping(value = "/orders/all/{id}")
    public List<Order> getAllOrder(@PathVariable("id") Long id) {
        return orderService.getOrders(id);
    }

    @DeleteMapping(value = "/orders/{id}")
    public void deleteOrder(@PathVariable("id") Long id) {
        orderService.delete(id);
    }

    @PostMapping(value = "/orders")
    private Long saveOrder(@RequestBody OrderRequest orderRequest)
    {
        Order order = OrderRequestToOrder(orderRequest);
        orderService.save(order);

        return order.getId();

    }

    @PutMapping(value = "/orders/{id}")
    public Long updateOrder(@RequestBody OrderRequest orderRequest, @PathVariable("id") Long id)
    {
        Order order = OrderRequestToOrder(orderRequest);
        orderService.update(order, id);
        return order.getId();
    }

    private Order OrderRequestToOrder(@RequestBody OrderRequest orderRequest) {
        Client client = new Client();
        client.setId(orderRequest.getClient_id());
        List<Item> items = new ArrayList<>();

        for (long item_id: orderRequest.getItems()) {
            Item item = new Item();
            item.setId(item_id);
            items.add(item);
        }
         return new Order(orderRequest.getAppointment(), client, items);
    }

}
