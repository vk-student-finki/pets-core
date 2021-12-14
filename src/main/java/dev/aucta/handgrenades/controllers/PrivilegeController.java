package dev.aucta.handgrenades.controllers;

import dev.aucta.handgrenades.models.Privilege;
import dev.aucta.handgrenades.services.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/privileges")
public class PrivilegeController {

    @Autowired
    PrivilegeService privilegeService;

    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(method = RequestMethod.GET)
    public Page<Privilege> getAll(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) throws InterruptedException {
//        Thread.sleep(2000);
        return privilegeService.getAll(PageRequest.of(page, size));
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Privilege get(
            @PathVariable("id") Long id
    ){
        return privilegeService.getById(id);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(path = "/generatePrivileges", method = RequestMethod.POST)
    public Boolean generatePrivileges(){
        privilegeService.generatePrivileges();
        return Boolean.TRUE;
    }
}
