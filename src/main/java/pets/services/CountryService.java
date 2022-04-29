package pets.services;

import pets.models.Country;
import pets.repositories.CountryRepository;
import pets.repositories.specifications.CountrySpecification;
import pets.repositories.specifications.SearchCriteria;
import pets.repositories.specifications.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    public Page<Country> all(HashMap<String, Object> searchParams, Pageable pageable)
    {
        Iterator<Map.Entry<String, Object>> iterator = searchParams.entrySet().iterator();
        CountrySpecification specification = new CountrySpecification();
        while(iterator.hasNext())
        {
            Map.Entry<String, Object> entry = iterator.next();
            specification.add(new SearchCriteria(entry.getKey(), entry.getValue(), SearchOperation.MATCH));
        }
        return countryRepository.findAll(specification, pageable);
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
