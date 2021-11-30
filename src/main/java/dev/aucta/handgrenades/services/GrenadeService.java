package dev.aucta.handgrenades.services;

import dev.aucta.handgrenades.models.Grenade;
import dev.aucta.handgrenades.repositories.GrenadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GrenadeService {

    @Autowired
    GrenadeRepository grenadeRepository;

    public Page<Grenade> all(Pageable pageable) {
        return grenadeRepository.findAll(pageable);
    }

    public Grenade get(Long id){
        Grenade grenade = grenadeRepository.getById(id);
        return grenade;
    }

    public Grenade create (Grenade grenade){
        if (grenade.getAttributes() != null) {
            grenade.getAttributes().forEach(attribute -> {
                attribute.setGrenade(grenade);
            });
        }
        return grenadeRepository.save(grenade);
    }

    public Grenade update(Grenade grenade){
        if (grenade.getAttributes() != null){
            grenade.getAttributes().forEach(attribute -> {
                attribute.setGrenade(grenade);
            });
        }
        return grenadeRepository.save(grenade);
    }

    public Boolean delete(Long id) {
        grenadeRepository.deleteById(id);
        return Boolean.TRUE;
    }
}
