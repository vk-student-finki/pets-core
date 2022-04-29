package pets.auth.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import pets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MfaService {

    @Autowired
    UserService userService;

    GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

    public boolean verifyCode(String username, int code) {
        return googleAuthenticator.authorize(userService.getUserByUsername(username).getMfaKey(), code);
    }

    public boolean isEnabled(String username) {
        return userService.getUserByUsername(username).getMfaEnabled() != null
                ? userService.getUserByUsername(username).getMfaEnabled() : false;
    }

    public String registerUser() {
        return googleAuthenticator.createCredentials().getKey();
    }
}
