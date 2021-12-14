package dev.aucta.handgrenades.validators;

import dev.aucta.handgrenades.exceptions.BadRequestError;
import dev.aucta.handgrenades.exceptions.ForbiddenAccess;
import dev.aucta.handgrenades.exceptions.HttpException;
import dev.aucta.handgrenades.models.User;
import dev.aucta.handgrenades.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    @Autowired
    UserService userService;

    public void validateUpdate(User user) throws HttpException {
        User oldUser = userService.getById(user.getId());
        if(!oldUser.getEmail().equals(user.getEmail()) || !oldUser.getUsername().equals(user.getUsername()))
            throw new BadRequestError("You are not allowed to change email address or username");
    }
    public void validateCreate(User user) throws BadRequestError{
        if(user.getUsername().isBlank()) throw new BadRequestError("Username is required");
        if(user.getNewPassword().isBlank()) throw new BadRequestError("Password is required");
        if(user.getEmail().isBlank()) throw new BadRequestError("Email is required");
        if(user.getPhoneNumber().isBlank()) throw new BadRequestError("Phone number is required");
        if(user.getGroups().isEmpty()) throw new BadRequestError("User must belong to a group");
    }


}
