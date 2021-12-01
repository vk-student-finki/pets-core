package dev.aucta.handgrenades.controllers;

import dev.aucta.handgrenades.models.User;
import dev.aucta.handgrenades.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
public class FooController {

    @Autowired
    UserService userService;

//    @Secured({"ROLE_ADMINISTRATOR"})
    @GetMapping("/protected")
    public Boolean test(){
        SecurityContextHolder.getContext().getAuthentication();
        return Boolean.TRUE;
    }

    @GetMapping("/notProtected")
    public Boolean notProtectedRoute(){
        SecurityContextHolder.getContext().getAuthentication();
        return Boolean.TRUE;
    }

    @GetMapping("/createRandomUser")
    public User createRandomUser(){
        User user = new User();
        user.setFirstName("Trpe");
        user.setLastName("Perov");
        user.setUsername("user2");
        user.setPassword("pass1234");
        return userService.create(user);
    }
}
