package dev.aucta.handgrenades.controllers;

import dev.aucta.handgrenades.exceptions.HttpException;
import dev.aucta.handgrenades.models.Country;
import dev.aucta.handgrenades.repositories.CountryRepository;
import dev.aucta.handgrenades.services.CountryService;
import dev.aucta.handgrenades.validators.CountryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/countries")
public class CountryController {

    @Autowired
    CountryService countryService;
    @Autowired
    CountryValidator countryValidator;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Country> all(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        return countryService.all(PageRequest.of(page, size));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Country get(
            @PathVariable("id") Long id
    ) {
        return countryService.get(id);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(method = RequestMethod.POST)
    public Country create(
            @RequestBody Country country
    ) throws HttpException {
        countryValidator.validateCreate(country);
        return countryService.create(country);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(method = RequestMethod.PUT)
    public Country update(
            @RequestBody Country country
    ) throws HttpException {
        countryValidator.validateUpdate(country);
        return countryService.update(country);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public Boolean delete(
            @PathVariable("id") Long id
    ) throws HttpException {
        countryValidator.validateDelete(id);
        return countryService.delete(id);
    }
}
