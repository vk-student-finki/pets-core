package pets.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pets.exceptions.BadRequestError;
import pets.exceptions.HttpException;
import pets.models.Group;
import pets.models.User;
import pets.services.UserService;
import pets.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserValidator userValidator;

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(method = RequestMethod.GET)
    public Page<User> getAll(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("searchParams") String filterMap
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> searchParams = objectMapper.readValue(filterMap, HashMap.class);
        return userService.getAll(searchParams, PageRequest.of(page, size));
    }

    @Secured({"ROLE_USER", "ROLE_ADMINISTRATOR"})
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public User get(
            @PathVariable("id") Long id
    ) {
        return userService.getById(id);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public User create(
            @RequestBody User user
    ) throws BadRequestError {
        userValidator.validateCreate(user);
        return userService.create(user);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public User update(
            @RequestBody User user
    ) throws HttpException {
        userValidator.validateUpdate(user);
        return userService.update(user);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public Boolean delete(
            @PathVariable("id") Long id
    ) {
        return userService.delete(id);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(path = "/{id}/addGroup", method = RequestMethod.PUT)
    public User addGroup(
            @PathVariable("id") Long userId,
            @RequestBody Group group
    ) {
        return userService.addGroup(userId, group);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(path = "/{id}/removeGroup", method = RequestMethod.DELETE)
    public User removeGroup(
            @PathVariable("id") Long userId,
            @RequestBody Group group
    ) {
        return userService.removeGroup(userId, group);
    }

}
