package org.onyx.showcasebackend.entities;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Client extends User{
    @OneToMany(mappedBy = "client")
    private Collection<Order> orders;

    @OneToOne
    @JoinColumn(name="cart_id", referencedColumnName = "id")
    private Cart cart;



    public Client(String firstName, String lastName, Long tel, String avatar, String email, Role role, String password, Collection<Order> orders, Cart cart) {
        super(firstName, lastName, tel, avatar, email, password,role);
        this.orders = orders;
        this.cart = cart;
    }

    public Client() {

    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }




}
