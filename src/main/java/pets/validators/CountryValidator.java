package pets.validators;

import pets.exceptions.BadRequestError;
import pets.exceptions.HttpException;
import pets.models.Country;
import pets.repositories.CountryRepository;
import pets.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountryValidator {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    PetRepository petRepository;

    public void validateCreate(Country country) throws HttpException{
        if(countryRepository.findByName(country.getName()) !=null) throw new BadRequestError("This country already exists.");
    }
    public void validateUpdate(Country country) throws HttpException{
        if(countryRepository.findByName(country.getName()) !=null) throw new BadRequestError("This country already exists.");
    }
    public void validateDelete(Long id) throws HttpException{
        if(petRepository.findFirstByCountryId(id) != null) {
            if (petRepository.findFirstByCountryId(id).getCountry().getId().equals(id)) {
                throw new BadRequestError("This country is connected to a pet. Please delete all pets connected to this country and try again.");
            }
        }
    }
}
