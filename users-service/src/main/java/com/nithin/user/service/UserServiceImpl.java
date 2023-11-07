package com.nithin.user.service;

import com.nithin.user.data.UserDTO;
import com.nithin.user.entity.UserEntity;
import com.nithin.user.repository.UsersRepositiry;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UsersRepositiry usersRepositiry;

    @Override
    public UserDTO createUser(UserDTO userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        ModelMapper modelMapper= new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity=modelMapper.map(userDetails,UserEntity.class);

        usersRepositiry.save(userEntity);
        UserDTO returnValue=modelMapper.map(userEntity, UserDTO.class);
        return returnValue;
    }

    @Override
    public UserDTO getUserDetailsByEmail(String email) {
       UserEntity userEntity= usersRepositiry.findByEmail(email);
       if(userEntity==null) throw new UsernameNotFoundException(email);
       return new ModelMapper().map(userEntity,UserDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity=usersRepositiry.findByEmail(username);
        if(username==null){
            throw new UsernameNotFoundException(username);
        }
        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),true,true,true,true,new ArrayList<>());
    }
}
