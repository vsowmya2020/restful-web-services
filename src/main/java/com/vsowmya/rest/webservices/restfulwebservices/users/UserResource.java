package com.vsowmya.rest.webservices.restfulwebservices.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import javax.validation.Valid;
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
    public Resource<User> retrieveUser(@PathVariable int id) {
        User user = userDao.findById(id);
        if(user==null)
            throw new UserNotFoundException("id="+id);
        //using HATEOAS to give a graph of links to use for navigation
        Resource<User> resource= new Resource<User>(user);
        ControllerLinkBuilder linkBuilder = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkBuilder.withRel("all-users"));
        return resource;
    }

    /**
     * Save a user by passing in a bean
     * Return status '201'(Created)
     * Create new URI to indicate changed status of created user
     * @param user
     * @return
     */
    @PostMapping(path="/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userDao.save(user);
        if(savedUser == null)
            throw new UserSaveException("name="+user.getName());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //Delete specific user
    @DeleteMapping(path="/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = userDao.deleteById(id);
        if(user==null)
            throw new UserNotFoundException("id="+id);

    }

}
