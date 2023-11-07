package com.nithin.user.service;

import com.nithin.user.data.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
     UserDTO createUser(UserDTO userDetails);

     UserDTO getUserDetailsByEmail(String email);
}
