package dev.aucta.handgrenades.controllers;

import dev.aucta.handgrenades.exceptions.HttpException;
import dev.aucta.handgrenades.models.Producer;
import dev.aucta.handgrenades.services.ProducerService;
import dev.aucta.handgrenades.validators.ProducerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/producers")
public class ProducerController {

    @Autowired
    ProducerService producerService;

    @Autowired
    ProducerValidator producerValidator;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Producer> all(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        return producerService.all(PageRequest.of(page, size));
    }

    @RequestMapping(path = "/id", method = RequestMethod.GET)
    public Producer get(
            @PathVariable("id") Long id
    ) {
        return producerService.get(id);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(method = RequestMethod.POST)
    public Producer create(
            @RequestBody Producer producer
    ) throws HttpException {
        producerValidator.validateCreate(producer);
        return producerService.create(producer);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(method = RequestMethod.PUT)
    public Producer update(
            @RequestBody Producer producer
    ) throws HttpException {
        producerValidator.validateUpdate(producer);
        return producerService.update(producer);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public Boolean delete(
            @PathVariable("id") Long id
    ) throws HttpException {
        producerValidator.validateDelete(id);
        return producerService.delete(id);
    }
}
