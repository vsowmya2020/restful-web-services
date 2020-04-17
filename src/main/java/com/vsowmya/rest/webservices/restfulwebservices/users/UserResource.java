package com.vsowmya.rest.webservices.restfulwebservices.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
    @Autowired
    private UserDao userDao;
    //Retrieve all users
    @GetMapping(path="/users")
    public List<User> retrieveAllUsers() {
        return userDao.findAll();
    }


    //Retrieve specific user
    @GetMapping(path="/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = userDao.findById(id);
        if(user==null)
            throw new UserNotFoundException("id="+id);
        return user;
    }

    /**
     * Save a user by passing in a bean
     * Return status '201'(Created)
     * Create new URI to indicate changed status of created user
     * @param user
     * @return
     */
    @PostMapping(path="/users")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User savedUser = userDao.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
