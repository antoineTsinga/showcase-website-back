package org.onyx.showcasebackend.Web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.onyx.showcasebackend.Web.services.CartService;
import org.onyx.showcasebackend.Web.services.ClientService;
import org.onyx.showcasebackend.dao.CartRepository;
import org.onyx.showcasebackend.entities.Cart;
import org.onyx.showcasebackend.entities.Client;
import org.onyx.showcasebackend.entities.Item;
import org.onyx.showcasebackend.payload.request.CartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ClientService clientService;

    @GetMapping("/carts")
    private List<Cart> getAllCarts() {
        return cartService.getCarts();
    }

    // creating a get mapping that retrieves the detail of a specific cart
    @GetMapping("/carts/{id}")
    private Cart getCarts(@PathVariable("id") long cartId) {
        return cartService.getCartById(cartId);
    }

    // creating a deleted mapping that deletes a specified cart
    @DeleteMapping("/carts/{id}")
    private void deleteCart(@PathVariable("id") long cartId) {
        cartService.deleteCart(cartId);
    }

    // creating post mapping that post the cart detail in the database
    @PostMapping("/carts")
    private long saveCart(@RequestBody CartRequest cartRequest) {
        Cart cart = CartRequestToCart(cartRequest);
        cartService.saveCart(cart);
        return cart.getId();
    }

    // creating put mapping that updates the cart detail
    @PutMapping("/carts/{id}")
    private Long updateCart(@RequestBody CartRequest cartRequest,@PathVariable("id") Long id) {
        Cart cart = CartRequestToCart(cartRequest);
        cartService.updateCart(cart,id);
        return cart.getId();
    }

    @PatchMapping(path = "/carts/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody JsonPatch patch) {
        try {
            Cart cart = cartRepository.findById(id).orElseThrow(NoClassDefFoundError::new);

            Cart cartPatched = applyPatchToCustomer(patch, cart);
            cartService.updateCart(cartPatched, cartPatched.getId());
            return ResponseEntity.ok(cartPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NoClassDefFoundError e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private Cart applyPatchToCustomer(
            JsonPatch patch, Cart targetCar) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCar, JsonNode.class));
        return objectMapper.treeToValue(patched, Cart.class);
    }
    private Cart CartRequestToCart(@RequestBody CartRequest cartRequest){


        Client client = clientService.getClientById(cartRequest.getClient());
        List<Item> items = new ArrayList<>();

        for (long item_id: cartRequest.getItems()) {
            Item item = new Item();
            item.setId(item_id);
            items.add(item);
        }
        Cart cart = new Cart();
        cart.setClient(client);
        cart.setItems(items);

        return cart;
    }

    private CartRequest CartToCartRequest(@RequestBody Cart cart){

        List<Long> items = cart.getItems().stream().map(item -> item.getId()).collect(Collectors.toList());

        return new CartRequest(cart.getId(),cart.getClient().getId(), items);
    }

}
