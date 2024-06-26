package org.onyx.showcasebackend.Web.services;

import org.onyx.showcasebackend.dao.AdminRepository;
import org.onyx.showcasebackend.entities.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAdmins(){
        List<Admin> admins = new ArrayList<Admin>();
        adminRepository.findAll() .forEach(admin -> admins.add(admin));
        return  admins;
    }

    public Admin getAdminById(Long id){
        return adminRepository.findById(id).get();
    }

    public void saveAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public void deleteAdmin(Long id){
        adminRepository.deleteById(id);
    }

    public void updateAdmin(Admin admin, Long id){
        admin.setId(id);
        adminRepository.save(admin);
    }



}
