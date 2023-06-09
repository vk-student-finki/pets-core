package pets.validators;

import pets.exceptions.BadRequestError;
import pets.exceptions.HttpException;
import pets.models.User;
import pets.services.UserService;
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
        if(user.getGroups().isEmpty()) throw new BadRequestError("User must belong to a group");
        if(userService.findByEmail(user.getEmail()) != null) throw new BadRequestError("This email is taken, please choose a new one.");
        if(userService.findByUsername(user.getUsername()) != null) throw new BadRequestError("This username is taken, please choose another one.");
    }
}
