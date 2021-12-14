package dev.aucta.handgrenades.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenAccess extends HttpException{
    public ForbiddenAccess (String message) {super (message);}
}
