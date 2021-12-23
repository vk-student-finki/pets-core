package dev.aucta.handgrenades.services;

import dev.aucta.handgrenades.models.Grenade;
import dev.aucta.handgrenades.repositories.GrenadeRepository;
import dev.aucta.handgrenades.repositories.specifications.GrenadeSpecification;
import dev.aucta.handgrenades.repositories.specifications.SearchCriteria;
import dev.aucta.handgrenades.repositories.specifications.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class GrenadeService {

    @Autowired
    GrenadeRepository grenadeRepository;

    public Page<Grenade> all(Pageable pageable) {
        return grenadeRepository.findAll(pageable);
    }

    public Page<Grenade> all(HashMap<String, Object> searchParams, Pageable pageable) {
        Iterator<Map.Entry<String, Object>> iterator = searchParams.entrySet().iterator();
        GrenadeSpecification specification = new GrenadeSpecification();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            if(entry.getKey().equals("country.id") || entry.getKey().equals("producer.id")) {
                specification.add(new SearchCriteria(entry.getKey(), Long.valueOf(entry.getValue().toString()), SearchOperation.EQUAL));
            } else {
                specification.add(new SearchCriteria(entry.getKey(), entry.getValue(), SearchOperation.MATCH));
            }
        }
        return grenadeRepository.findAll(specification, pageable);
    }

    public Grenade get(Long id) {
        Grenade grenade = grenadeRepository.getById(id);
        return grenade;
    }

    public Grenade create(Grenade grenade) {
        if (grenade.getAttributes() != null) {
            grenade.getAttributes().forEach(attribute -> {
                attribute.setGrenade(grenade);
            });
        }

        return grenadeRepository.save(grenade);
    }

    public Grenade update(Grenade grenade) {
        if (grenade.getAttributes() != null) {
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

    public Page<Grenade> filterGrenades(Long producerID, Long countryID, Pageable pageable) {
        if (countryID != null & producerID != null)
            return grenadeRepository.findAllByProducerIdAndCountryId(producerID, countryID, pageable);
        if (producerID == null & countryID != null) return grenadeRepository.findAllByCountryId(countryID, pageable);
        else return grenadeRepository.findAllByProducerId(producerID, pageable);
    }


}
