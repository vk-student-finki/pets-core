package dev.aucta.handgrenades.controllers;

import dev.aucta.handgrenades.models.Grenade;
import dev.aucta.handgrenades.services.GrenadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/grenades")
public class GrenadeController {

    @Autowired
    GrenadeService grenadeService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Grenade> all(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        return grenadeService.all(PageRequest.of(page, size));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Grenade get(
            @PathVariable("id") Long id
    ) {
        return grenadeService.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Grenade create(
            @RequestBody Grenade grenade
    ) {
        return grenadeService.create(grenade);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Grenade update(
            @RequestBody Grenade grenade
    ) {
        return grenadeService.update(grenade);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public Boolean delete(
            @PathVariable("id") Long id
    ) {
        return grenadeService.delete(id);
    }

    @RequestMapping(path= "/filterByProducer", method = RequestMethod.GET)
        public Page<Grenade> filterGrenadesByProducer(
                @RequestParam(value="producerID" ) Long id,
                @RequestParam("page") Integer page,
                @RequestParam("size") Integer size

            )
    {
        return  grenadeService.filterGrenadesByProducer(id,PageRequest.of(page,size));
    }

    @RequestMapping(path = "/filterByCountry", method = RequestMethod.GET)
    public Page<Grenade> filterGrenadesByCountry(
            @RequestParam(value="countryId") Long id,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ){
        return grenadeService.filterGrenadesByCountry(id, PageRequest.of(page, size));
    }

}
