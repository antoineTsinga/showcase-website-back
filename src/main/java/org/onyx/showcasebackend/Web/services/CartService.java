package org.onyx.showcasebackend.Web.services;


import org.onyx.showcasebackend.dao.CartRepository;
import org.onyx.showcasebackend.entities.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    public List<Cart> getCarts(){
        return cartRepository.findAll();
    }

    public Cart getCartById(Long id){
        return cartRepository.findById(id).isPresent()? cartRepository.findById(id).get() : null;
    }

    public void saveCart(Cart cart){
        cartRepository.save(cart);
    }

    public void deleteCart(long id){
        cartRepository.deleteById(id);
    }
    public void updateCart(Cart cart, Long id){
        cart.setId(id);
        cartRepository.save(cart);
    }
}
