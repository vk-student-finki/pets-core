package dev.aucta.handgrenades.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
public class FooController {

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
}
