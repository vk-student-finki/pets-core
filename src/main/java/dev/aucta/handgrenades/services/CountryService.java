package dev.aucta.handgrenades.services;

import dev.aucta.handgrenades.models.Country;
import dev.aucta.handgrenades.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    public Page<Country> all(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    public Country get(Long id) {
        Country country = countryRepository.getById(id);
        return country;
    }

    public Country create(Country country) {
        return countryRepository.save(country);
    }

    public Country update(Country country) {
        return countryRepository.save(country);
    }

    public Boolean delete(Long id) {
        countryRepository.deleteById(id);
        return Boolean.TRUE;
    }
}
