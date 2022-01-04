package dev.aucta.handgrenades.validators;


import dev.aucta.handgrenades.exceptions.BadRequestError;
import dev.aucta.handgrenades.exceptions.HttpException;
import dev.aucta.handgrenades.models.Grenade;
import dev.aucta.handgrenades.models.PictureType;
import dev.aucta.handgrenades.models.User;
import dev.aucta.handgrenades.repositories.GrenadeRepository;
import dev.aucta.handgrenades.services.GrenadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Objects;

@Component
public class GrenadeValidator {

    @Autowired
    GrenadeService grenadeService;
    @Autowired
    GrenadeRepository grenadeRepository;

    public void validateUpdate(Grenade grenade) throws HttpException {
       if(grenadeRepository.findFirstByName(grenade.getName()) != null)
       {
           throw new BadRequestError("A grenade with this name already exists");
       }
        }
    public void validateCreate(Grenade grenade) throws HttpException {
        if(grenadeRepository.findFirstByName(grenade.getName()) != null)
        {
            throw new BadRequestError("A grenade with this name already exists");
        }
    }

}
