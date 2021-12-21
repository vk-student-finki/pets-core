package dev.aucta.handgrenades.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aucta.handgrenades.models.Grenade;
import dev.aucta.handgrenades.services.GrenadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/grenades")
public class GrenadeController {

    @Autowired
    GrenadeService grenadeService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Grenade> all(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("searchParams") String filterMap
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> searchParams = objectMapper.readValue(filterMap, HashMap.class);
        return grenadeService.all(searchParams, PageRequest.of(page, size));
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


    @RequestMapping(path="/filter", method = RequestMethod.GET)
    public Page<Grenade> filterGrenades(
            @RequestParam(value="producerID", required = false) Long producerID,
            @RequestParam(value="countryID", required = false) Long countryID,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    )
    {
        return grenadeService.filterGrenades(producerID,countryID,PageRequest.of(page,size));
    }

}
