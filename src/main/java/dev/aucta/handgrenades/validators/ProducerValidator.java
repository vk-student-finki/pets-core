package dev.aucta.handgrenades.validators;


import dev.aucta.handgrenades.exceptions.BadRequestError;
import dev.aucta.handgrenades.exceptions.HttpException;
import dev.aucta.handgrenades.models.Grenade;
import dev.aucta.handgrenades.models.Producer;
import dev.aucta.handgrenades.repositories.GrenadeRepository;
import dev.aucta.handgrenades.repositories.ProducerRepository;
import dev.aucta.handgrenades.services.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProducerValidator{


    @Autowired
    GrenadeRepository grenadeRepository;

    @Autowired
    ProducerRepository producerRepository;

    public void validateDelete(Long id) throws HttpException{
        if(grenadeRepository.findFirstByProducerId(id) != null) {
            if (grenadeRepository.findFirstByProducerId(id).getProducer().getId().equals(id)) {
                throw new BadRequestError("This producer is connected to a grenade.Please delete all grenades connected to this producer and try again");
            }
        }
    }
    public void validateCreate(Producer producer) throws HttpException {
        if(producerRepository.findByName(producer.getName()) !=null) throw new BadRequestError("This producer already exists.");
    }
    public void validateUpdate(Producer producer) throws HttpException{
        if(producerRepository.findByName(producer.getName()) !=null) throw new BadRequestError("This producer already exists.");
    }

}
