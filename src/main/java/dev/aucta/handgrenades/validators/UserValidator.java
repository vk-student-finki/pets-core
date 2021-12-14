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


}
