package dev.aucta.handgrenades.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestError extends HttpException{
    public BadRequestError (String message) {super (message);}
}
