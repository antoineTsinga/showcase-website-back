package org.onyx.showcasebackend.Web.controllers;

import org.onyx.showcasebackend.Web.services.AdminService;
import org.onyx.showcasebackend.dao.AdminRepository;
import org.onyx.showcasebackend.dao.UserRepository;
import org.onyx.showcasebackend.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    AdminRepository userRepository;

    @GetMapping("/admins")
    private ResponseEntity<?> getAllAdmins() {
        HashMap<String,Object> data = new HashMap<>();
        List<Admin> items = adminService.getAdmins();
        data.put("results", items);
        data.put("count", items.size());
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    // creating a get mapping that retrieves the detail of a specific admin
    @GetMapping("/admins/{id}")
    private Admin getAdmins(@PathVariable("id") long adminId) {
        return adminService.getAdminById(adminId);
    }

    // creating a deleted mapping that deletes a specified admin
    @DeleteMapping("/admins/{id}")
    private void deleteAdmin(@PathVariable("id") long adminId) {
        adminService.deleteAdmin(adminId);
    }

    // creating post mapping that post the admin detail in the database
    @PostMapping("/admins")
    private long saveAdmin(@RequestBody Admin admin) {
        adminService.saveAdmin(admin);
        return admin.getId();
    }

    // creating put mapping that updates the admin detail
    @PutMapping("/admins/{id}")
    private Admin update(@RequestBody Admin client, @PathVariable("id") long adminId) {


        Optional<Admin> clientOptional = userRepository.findById(adminId);
        Admin clientResolve = clientOptional.get();

        if(clientResolve==null) return null;
        String firstName = client.getFirstName()!=null?client.getFirstName():clientResolve.getFirstName();
        String lastName = client.getLastName()!=null? client.getLastName() : clientResolve.getLastName();
        Long tel = client.getTel()!=null?client.getTel():clientResolve.getTel();
        String avatar = client.getAvatar()!=null?client.getAvatar():clientResolve.getAvatar();
        String description = client.getDescription()!=null?client.getDescription():clientResolve.getDescription();
        String email = client.getEmail()!=null? client.getEmail() : clientResolve.getEmail();
        String password = client.getPassword()!=null?client.getPassword():clientResolve.getPassword();
        Role role = clientResolve.getRole();

        System.out.println(role);

        Admin clientToSave = new Admin();
        clientToSave.setId(adminId);
        clientToSave.setRole(role);
        clientToSave.setPassword(password);
        clientToSave.setAvatar(avatar);
        clientToSave.setEmail(email);
        clientToSave.setTel(tel);
        clientToSave.setFirstName(firstName);
        clientToSave.setLastName(lastName);
        clientToSave.setDescription(description);

        adminService.updateAdmin(client, adminId);
        return clientToSave;
    }

}
