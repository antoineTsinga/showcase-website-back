package org.onyx.showcasebackend.Web.controllers;

import org.onyx.showcasebackend.Web.services.UserService;
import org.onyx.showcasebackend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
@RequestMapping("/api")
public  class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public List<User> getAllUsers(){
        return userService.getUsers();
    }

    @GetMapping(value = "/users/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUsersById(id);
    }

    @DeleteMapping(value = "/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @PostMapping(value = "/users")
    public User saveUser(@RequestBody User user)
    {
         userService.save(user);
         return user;

    }

    /**
     * Met à jour un utilisateur en fonction des permissions
     */
  //  @PreAuthorize("@authorizationSE.can('update', 'User') or @authorizationSE.can('update', 'User', #id)")
    @PutMapping(value = "/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") Long id)
    {
        userService.update(user, id);
        return user;
    }




}
