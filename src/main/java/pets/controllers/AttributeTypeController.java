package pets.controllers;


import pets.models.AttributeType;
import pets.services.AttributeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/attributeTypes")
public class AttributeTypeController {

    @Autowired
    AttributeTypeService attributeTypeService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<AttributeType> all(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        return attributeTypeService.all(PageRequest.of(page, size));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<AttributeType> get(
            @PathVariable("id") Long id
    ) {
        return attributeTypeService.findAllById(id);
    }
    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(method = RequestMethod.POST)
    public AttributeType create(
            @RequestBody AttributeType attributeType
    ) {
        return attributeTypeService.create(attributeType);
    }
    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(method = RequestMethod.PUT)
    public AttributeType update(
            @RequestBody AttributeType attributeType
    ) {
        return attributeTypeService.update(attributeType);
    }

}
