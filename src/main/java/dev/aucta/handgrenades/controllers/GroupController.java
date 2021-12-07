package dev.aucta.handgrenades.controllers;

import dev.aucta.handgrenades.models.Group;
import dev.aucta.handgrenades.models.Privilege;
import dev.aucta.handgrenades.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/groups")
public class GroupController {

    @Autowired
    GroupService groupService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Group> getAll(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) throws InterruptedException {
//        Thread.sleep(2000);
        return groupService.getAll(PageRequest.of(page, size));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Group get(
            @PathVariable("id") Long id
    ){
        return groupService.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Group create(
            @RequestBody Group group
    ){
        return groupService.create(group);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Group update(
            @RequestBody Group group
    ){
        return groupService.update(group);

    }

    @RequestMapping(path="/{id}",method = RequestMethod.DELETE)
    public Boolean delete(
            @PathVariable("id") Long id
    ){
        return groupService.delete(id);
    }

    @RequestMapping(path = "/{id}/addPrivilege", method = RequestMethod.PUT)
    public Group addPrivilege(
            @PathVariable("id") Long groupId,
            @RequestBody Privilege privilege
            ){
        return groupService.addPrivilege(groupId, privilege);
    }

    @RequestMapping(path = "/{id}/removePrivilege", method = RequestMethod.DELETE)
    public Group removePrivilege(
            @PathVariable("id") Long groupId,
            @RequestBody Privilege privilege
    ){
        return groupService.removePrivilege(groupId, privilege);
    }
}
