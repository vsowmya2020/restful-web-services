package com.vsowmya.rest.webservices.restfulwebservices.users;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * User Dao class - currently has static data. Will need to point to database later.
 */
@Component
public class UserDao {

    private static List<User> userList = new ArrayList<>();
    private static int userCount =0 ;
    static {
        userList.add(new User(++userCount,"Sowmya", new Date()));
        userList.add(new User(++userCount,"Poorna", new Date()));
        userList.add(new User(++userCount,"Jishnu", new Date()));
    }

    public User findById(int id) {
        Optional<User> userOptional = userList.stream().filter(matchId->matchId.getId()==id).findAny();
        if(userOptional.isPresent())
            return userOptional.get();
        return null;
    }

    public List<User> findAll() {
        return userList;
    }

    public User save(User user) {
        if(user.getId() == null)
            user.setId(++userCount);
        userList.add(user);
        return user;
    }
    public User deleteById(int id) {
        Optional<User> userOptional = userList.stream().filter(matchId->matchId.getId()==id).findAny();
        if(userOptional.isPresent()) {
            User removeUser = userOptional.get();
            userList.remove(removeUser);
            return removeUser;
        }
        return null;
    }

}
