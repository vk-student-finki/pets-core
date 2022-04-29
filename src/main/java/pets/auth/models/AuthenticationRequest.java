package pets.auth.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class AuthenticationRequest implements Serializable {

    private String username;
    private String password;

    //need default constructor for JSON Parsing
    public AuthenticationRequest() {

    }

    public AuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public AuthenticationRequest(String username, String password, Integer mfaToken) {
        this.setUsername(username);
        this.setPassword(password);
    }
}