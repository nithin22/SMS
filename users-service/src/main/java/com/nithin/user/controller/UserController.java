package com.nithin.user.controller;

import com.nithin.user.data.UserDTO;
import com.nithin.user.model.CreateUserRequestModel;
import com.nithin.user.model.CreateUserResponseModel;
import com.nithin.user.service.UserService;
import com.nithin.user.service.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private Environment environment;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/status/check")
    public String showString(){
        return "Working on port "+environment.getProperty("local.server.port")+", With token ="+environment.getProperty("token.secret");
    }

    @PostMapping()
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails){
        ModelMapper modelMapper= new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDTO=modelMapper.map(userDetails, UserDTO.class);
        UserDTO createdUser=userService.createUser(userDTO);
        CreateUserResponseModel result=modelMapper.map(createdUser, CreateUserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
