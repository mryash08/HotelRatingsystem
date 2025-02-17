package org.hotelrating.userservice.services;

import org.hotelrating.userservice.model.User;

import java.util.List;

public interface UserService{

    User saveUser(User user);

    //get all user
    List<User> getAllUser();

    //get single user of given userId

    User getUser(String userId);

}
