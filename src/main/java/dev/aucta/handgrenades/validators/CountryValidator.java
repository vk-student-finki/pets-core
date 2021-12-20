package dev.aucta.handgrenades.validators;

import dev.aucta.handgrenades.exceptions.BadRequestError;
import dev.aucta.handgrenades.exceptions.HttpException;
import dev.aucta.handgrenades.models.Country;
import dev.aucta.handgrenades.repositories.CountryRepository;
import dev.aucta.handgrenades.repositories.GrenadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountryValidator {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    GrenadeRepository grenadeRepository;

    public void validateCreate(Country country) throws HttpException{
        if(countryRepository.findByName(country.getName()) !=null) throw new BadRequestError("This country already exists.");
    }
    public void validateUpdate(Country country) throws HttpException{
        if(countryRepository.findByName(country.getName()) !=null) throw new BadRequestError("This country already exists.");
    }
    public void validateDelete(Long id) throws HttpException{
        if(grenadeRepository.findFirstByCountryId(id) != null) {
            if (grenadeRepository.findFirstByCountryId(id).getCountry().getId().equals(id)) {
                throw new BadRequestError("This country is connected to a grenade. Please delete all grenades connected to this countnry and try again");
            }
        }
    }
}
