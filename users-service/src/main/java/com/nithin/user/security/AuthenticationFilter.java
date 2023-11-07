package com.nithin.user.security;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nithin.user.data.UserDTO;
import com.nithin.user.model.LoginRequestModel;
import com.nithin.user.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;


import static java.time.Instant.now;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;

    private Environment environment;


    public AuthenticationFilter(AuthenticationManager authenticationManager,Environment environment,UserService userService){
        super(authenticationManager);
        this.environment=environment;
        this.userService=userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try{
            LoginRequestModel creds=new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequestModel.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
       User user= (User) authResult.getPrincipal();
       UserDTO userDetails=userService.getUserDetailsByEmail(user.getUsername());
       String tokenSecret=environment.getProperty("token.secret");
       byte[] secretKeyBytes=Base64.getEncoder().encode(tokenSecret.getBytes());
       SecretKey secretKey= new SecretKeySpec(secretKeyBytes,SignatureAlgorithm.HS512.getJcaName());
       Instant now=Instant.now();

       String token=Jwts.builder()
               .setSubject(userDetails.getUserId())
               .setExpiration(Date.from(Instant.now().plusMillis(Long.parseLong(Objects.requireNonNull(
                       environment.getProperty("token.expiration_time")
               )))))
               .setIssuedAt(Date.from(Instant.now()))
               .signWith(secretKey, SignatureAlgorithm.HS512).compact();
       response.addHeader("token",token);
       response.addHeader("userId",userDetails.getUserId());
    }
}
