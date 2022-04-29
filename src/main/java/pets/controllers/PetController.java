package pets.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pets.exceptions.HttpException;
import pets.models.Pet;
import pets.models.Picture;
import pets.models.PictureType;
import pets.services.PetService;
import pets.validators.PetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/pets")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    PetValidator petValidator;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Pet> all(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("searchParams") String filterMap
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> searchParams = objectMapper.readValue(filterMap, HashMap.class);
        return petService.all(searchParams, PageRequest.of(page, size));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Pet get(
            @PathVariable("id") Long id
    ) {
        return petService.get(id);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(method = RequestMethod.POST)
    public Pet create(
            @RequestBody Pet pet
    ) throws HttpException {
        petValidator.validateCreate(pet);
        return petService.create(pet);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(method = RequestMethod.PUT)
    public Pet update(
            @RequestBody Pet pet
    ) {
        return petService.update(pet);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public Boolean delete(
            @PathVariable("id") Long id
    ) {
        return petService.delete(id);
    }


    @RequestMapping(path = "/filter", method = RequestMethod.GET)
    public Page<Pet> filterPets(
            @RequestParam(value = "countryID", required = false) Long countryID,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        return petService.filterPets(countryID, PageRequest.of(page, size));
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(path = "/uploadPetImage/{petId}", method = RequestMethod.PUT)
    public List<Picture> uploadPictureAttachment(
            @PathVariable("petId") Long petId,
            @RequestParam(value = "pictureType", required = false) PictureType pictureType,
            @RequestParam("files") MultipartFile[] multipartFiles
    )  {

        System.out.println(multipartFiles);
        petService.uploadPetsPictures(petId, pictureType, multipartFiles);
        return null;
    }


    @RequestMapping(path = "/downloadPetImage/{pictureId}", method = RequestMethod.GET)
    public ResponseEntity uploadPictureAttachment(
            @PathVariable("pictureId") Long pictureId
    ) throws IOException {
        File file = petService.getPictureById(pictureId);
        HttpHeaders headers = new HttpHeaders();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType(Files.probeContentType(Path.of(file.getAbsolutePath()))))
                .body(resource);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @RequestMapping(path="removePicture/{petId}", method = RequestMethod.DELETE)
    public Pet removePicture(
            @PathVariable("petId") Long petId,
            @RequestBody Picture picture
    ){
        return petService.removePicture(petId, picture);
    }

    @RequestMapping(path = "pictureTypes", method = RequestMethod.GET)
    public PictureType[] pictureTypes (){
        return petService.pictureTypes();
    }

    @RequestMapping(path = "updatePictureType/{petId}", method = RequestMethod.PUT)
    public void updatePictureType(
            @PathVariable("petId") Long petId,
            @RequestParam("pictureId") Long pictureId,
            @RequestParam("pictureType") PictureType pictureType
    ){
        petService.updatePictureType(petId, pictureId, pictureType);
    }

}
