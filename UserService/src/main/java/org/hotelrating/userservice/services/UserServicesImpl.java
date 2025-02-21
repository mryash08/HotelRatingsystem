package org.hotelrating.userservice.services;

import org.hotelrating.userservice.external.services.HotelService;
import org.hotelrating.userservice.model.Hotel;
import org.hotelrating.userservice.model.Rating;
import org.hotelrating.userservice.model.User;
import org.hotelrating.userservice.repositiory.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServicesImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HotelService hotelService;

    RedisTemplate<String, Object> redisTemplate;

    Logger logger = LoggerFactory.getLogger(UserServicesImpl.class);

    @Autowired
    public UserServicesImpl(UserRepository userRepository, RedisTemplate<String, Object> redisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
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

//    @Override
//    @Cacheable(value = "users", key = "#userId")
//    public User getUser(String userId) {
//        User user = userRepository.findById(userId).orElse(null);
//        if(user!=null){
//            Rating[] ratings = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+userId, Rating[].class);
//
//            List<Rating> ratingList = Arrays.stream(ratings).toList();
//            ratingList.forEach(rating -> {
//                //find hotel by hotelId
////                Hotel hotel = restTemplate.getForObject("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//                rating.setHotel(hotelService.getHotel(rating.getHotelId()));
//            });
//            user.setRatings(ratingList);
//        }
//        return user;
//    }

  @Cacheable(value = "users", key = "#userId") // Cache user data in Redis
    public User getUser(String userId) {
        logger.info("Fetching user from DB and external services...");
        // Check Redis first
//        User cachedUser = (User) redisTemplate.opsForValue().get("USER_" + userId);
//        if (cachedUser != null) {
//            logger.info("Returning user from Redis cache");
//            return cachedUser;
//        }

        // Fetch user from DB
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Rating[] ratings = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/" + userId, Rating[].class);
            List<Rating> ratingList = Arrays.asList(ratings);

            ratingList.forEach(rating -> {
                rating.setHotel(hotelService.getHotel(rating.getHotelId())); // Fetch hotel details
            });

            user.setRatings(ratingList);

            // Store in Redis with TTL (10 minutes)
//            redisTemplate.opsForValue().set("USER_" + userId, user, 100, TimeUnit.MINUTES);
        }
        return user;
    }

}
