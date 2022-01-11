package dev.aucta.handgrenades.auth.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class AuthenticationResponse implements Serializable {

    private final String jwt;
    private String additionalActionRequired;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public AuthenticationResponse(String jwt, String additionalActionRequired) {
        this.jwt = jwt;
        this.additionalActionRequired = additionalActionRequired;
    }
}