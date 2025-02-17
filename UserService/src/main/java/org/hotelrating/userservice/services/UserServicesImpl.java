package org.hotelrating.userservice.services;

import org.hotelrating.userservice.external.services.HotelService;
import org.hotelrating.userservice.model.Hotel;
import org.hotelrating.userservice.model.Rating;
import org.hotelrating.userservice.model.User;
import org.hotelrating.userservice.repositiory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserServicesImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HotelService hotelService;

    @Autowired
    public UserServicesImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User saveUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {

        List<User> userList = userRepository.findAll();
        userList.forEach(user -> {
            Rating[] ratings = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+user.getUserId(), Rating[].class);
            List<Rating> ratingList = Arrays.stream(ratings).toList();
            ratingList.forEach(rating -> {
                //find hotel by hotelId
//                Hotel hotel = restTemplate.getForObject("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
                rating.setHotel(hotelService.getHotel(rating.getHotelId()));
            });
            user.setRatings(ratingList);
        });

        return userList;
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user!=null){
            Rating[] ratings = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+userId, Rating[].class);

            List<Rating> ratingList = Arrays.stream(ratings).toList();
            ratingList.forEach(rating -> {
                //find hotel by hotelId
//                Hotel hotel = restTemplate.getForObject("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
                rating.setHotel(hotelService.getHotel(rating.getHotelId()));
            });
            user.setRatings(ratingList);
        }
        return user;
    }

}
