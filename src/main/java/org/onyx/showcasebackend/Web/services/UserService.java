package org.onyx.showcasebackend.Web.services;


import org.onyx.showcasebackend.dao.UserRepository;
import org.onyx.showcasebackend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public  class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getUsers(){
        List<User> users = new ArrayList<User>();
        userRepository.findAll() .forEach(user -> users.add(user));
        return  users;
    }

    public User getUsersById(Long id){
        return userRepository.findById(id).get();
    }

    public void save(User user){
        userRepository.save(user);
    }


    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public void update(User user, Long id){
        user.setId(id);
        userRepository.save(user);
    }

    public Iterable<User> save(List<User> users) {
        users = users.stream().map((user)-> {
            String password = passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
            return user;
        }).collect(Collectors.toList());

        return userRepository.saveAll(users);
    }




    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
