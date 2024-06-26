package org.onyx.showcasebackend.Web.controllers;

import org.onyx.showcasebackend.Web.services.CartService;
import org.onyx.showcasebackend.Web.services.ClientService;
import org.onyx.showcasebackend.dao.ClientRepository;
import org.onyx.showcasebackend.dao.RoleRepository;
import org.onyx.showcasebackend.entities.*;

import org.onyx.showcasebackend.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ClientRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    private ResponseEntity<?> getAllClients() {
        HashMap<String,Object> data = new HashMap<>();
        List<Client> items = clientService.getClients();
        data.put("results", items);
        data.put("count", items.size());
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    // creating a get mapping that retrieves the detail of a specific client
    @GetMapping("/clients/{id}")
    private Client getClients(@PathVariable("id") long clientId) {
        return clientService.getClientById(clientId);
    }

    // creating a deleted mapping that deletes a specified client
    @DeleteMapping("/clients/{id}")
    private void deleteClient(@PathVariable("id") long clientId) {
        clientService.deleteClient(clientId);
    }

    // creating post mapping that post the client detail in the database
    @PostMapping("/clients")
    private long saveClient(@RequestBody Client client) {

        clientService.saveClient(client);
        return client.getId();
    }

    // creating put mapping that updates the client detail
    @PutMapping("/clients/{id}")
    private Client update(@PathVariable("id") long clientId, @RequestBody Client client) {
        Optional<Client> clientOptional = userRepository.findById(clientId);
        Client clientResolve = clientOptional.get();

        if(clientResolve==null) return null;
        String firstName = client.getFirstName()!=null?client.getFirstName():clientResolve.getFirstName();
        String lastName = client.getLastName()!=null? client.getLastName() : clientResolve.getLastName();
        Long tel = client.getTel()!=null?client.getTel():clientResolve.getTel();
        String avatar = client.getAvatar()!=null?client.getAvatar():clientResolve.getAvatar();
        String email = client.getEmail()!=null? client.getEmail() : clientResolve.getEmail();
        String password = client.getPassword()!=null?client.getPassword():clientResolve.getPassword();
        Collection<Order> orders = client.getOrders()!=null?client.getOrders():clientResolve.getOrders();
        Cart cart = client.getCart()!=null?client.getCart():clientResolve.getCart();
        Role role = clientResolve.getRole();

        Client clientToSave = new Client();
        clientToSave.setId(clientId);
        clientToSave.setRole(role);
        clientToSave.setPassword(password);
        clientToSave.setCart(cart);
        clientToSave.setOrders(orders);
        clientToSave.setAvatar(avatar);
        clientToSave.setEmail(email);
        clientToSave.setTel(tel);
        clientToSave.setFirstName(firstName);
        clientToSave.setLastName(lastName);



        clientService.updateClient(clientToSave, clientId);
        return clientToSave;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody Client client) {
        if (userRepository.existsByEmail(client.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Error: Email is already in use !");

        }
        // Set Role User

        Role role = roleRepository.findByName("ROLE_USER");
        client.setRole(role);
        client.setPassword(passwordEncoder.encode(client.getPassword()));

        // set Cart

        List<Item> items = new ArrayList<>();
        Cart cart = new Cart();
        cart.setItems(items);
        client.setCart(cart);

        cartService.saveCart(cart);
        clientService.save(client);


        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("User registered successfully!");
    }

    @GetMapping("/clients/current")
    public ResponseEntity<?> currentClient(){

        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(null);
        }

        MyUserDetails currentUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(currentUser.getUsername()==null){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(null);
        }
        HashMap<String,Object> data = new HashMap<>();
        data.put("username", currentUser.getUsername());
        data.put("id", currentUser.getId());
        data.put("Authorities", currentUser.getAuthorities());
        data.put("accountNonExpired", currentUser.isAccountNonExpired());
        data.put("credentialsNonExpired", currentUser.isAccountNonExpired());
        data.put("accountNonLocked", currentUser.isAccountNonLocked());


        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.POST)
    public ResponseEntity<?> logoutDo(HttpServletRequest request ){
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(null);
        }

        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);

        request.getSession(false);
        HttpSession session;
        SecurityContextHolder.clearContext();
        session= request.getSession(false);

            session.invalidate();


        if(request.getCookies() !=null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("JSESSIONID")) {
                    cookie.setMaxAge(0);
                }
                cookie.setMaxAge(0);

            }
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }


}
