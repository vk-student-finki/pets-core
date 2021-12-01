package dev.aucta.handgrenades.controllers;

import dev.aucta.handgrenades.models.Producer;
import dev.aucta.handgrenades.services.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/producers")
public class ProducerController {

    @Autowired
    ProducerService producerService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Producer> all(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ){
        return producerService.all(PageRequest.of(page, size));
    }

    @RequestMapping(path = "/id", method = RequestMethod.GET)
    public Producer get(
            @PathVariable("id") Long id
    ){
        return producerService.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Producer create(
            @RequestBody Producer producer
    ){
        return producerService.create(producer);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Producer update(
            @RequestBody Producer producer
    ){
        return producerService.update(producer);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public Boolean delete(
            @PathVariable("id") Long id
    ){
        return producerService.delete(id);
    }
}
