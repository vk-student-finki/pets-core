package dev.aucta.handgrenades.controllers;

import dev.aucta.handgrenades.models.Country;
import dev.aucta.handgrenades.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/countries")
public class CountryController {

    @Autowired
    CountryService countryService;

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

    @RequestMapping(method = RequestMethod.POST)
    public Country create(
            @RequestBody Country country
    ) {
        return countryService.create(country);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Country update(
            @RequestBody Country country
    ) {
        return countryService.update(country);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public Boolean delete(
            @PathVariable("id") Long id
    ) {
        return countryService.delete(id);
    }
}
