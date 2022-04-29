package pets.validators;


import pets.exceptions.BadRequestError;
import pets.exceptions.HttpException;
import pets.models.Pet;
import pets.repositories.PetRepository;
import pets.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetValidator {

    @Autowired
    PetService petService;
    @Autowired
    PetRepository petRepository;

    public void validateUpdate(Pet pet) throws HttpException {
       if(petRepository.findFirstByName(pet.getName()) != null)
       {
           throw new BadRequestError("A pet with this name already exists");
       }
        }
    public void validateCreate(Pet pet) throws HttpException {
        if(petRepository.findFirstByName(pet.getName()) != null)
        {
            throw new BadRequestError("A pet with this name already exists");
        }
    }

}
