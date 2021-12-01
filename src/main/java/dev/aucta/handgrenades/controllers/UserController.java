package dev.aucta.handgrenades.controllers;

import dev.aucta.handgrenades.models.Group;
import dev.aucta.handgrenades.models.User;
import dev.aucta.handgrenades.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<User> getAll(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        return userService.getAll(PageRequest.of(page, size));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public User get(
            @PathVariable("id") Long id
    ) {
        return userService.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User create(
            @RequestBody User user
    ) {
        return userService.create(user);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public User update(
            @RequestBody User user
    ) {
        return userService.update(user);
    }

    @RequestMapping(path="/{id}",method = RequestMethod.DELETE)
    public Boolean delete(
            @PathVariable("id") Long id
    ){
        return userService.delete(id);
    }

    @RequestMapping(path="/{id}/addGroup", method = RequestMethod.PUT)
    public User addGroup(
            @PathVariable("id") Long userId,
            @RequestBody Group group
            ){
        return userService.addGroup(userId,group);
    }

    @RequestMapping(path = "/{id}/removeGroup",method = RequestMethod.DELETE)
    public User removeGroup(
            @PathVariable("id") Long userId,
            @RequestBody Group group
    ){
        return userService.removeGroup(userId,group);
    }

}
