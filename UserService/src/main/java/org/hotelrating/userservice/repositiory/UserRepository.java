package org.hotelrating.userservice.repositiory;

import org.hotelrating.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, String> {


}
