package dev.aucta.handgrenades.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aucta.handgrenades.exceptions.HttpException;
import dev.aucta.handgrenades.models.Grenade;
import dev.aucta.handgrenades.models.Picture;
import dev.aucta.handgrenades.models.PictureType;
import dev.aucta.handgrenades.services.GrenadeService;
import dev.aucta.handgrenades.validators.GrenadeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/grenades")
public class GrenadeController {

    @Autowired
    GrenadeService grenadeService;

    @Autowired
    GrenadeValidator grenadeValidator;

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
    ) throws HttpException {
        grenadeValidator.validateCreate(grenade);
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


    @RequestMapping(path = "/filter", method = RequestMethod.GET)
    public Page<Grenade> filterGrenades(
            @RequestParam(value = "producerID", required = false) Long producerID,
            @RequestParam(value = "countryID", required = false) Long countryID,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        return grenadeService.filterGrenades(producerID, countryID, PageRequest.of(page, size));
    }

    @RequestMapping(path = "/uploadGrenadeImage/{grenadeId}", method = RequestMethod.PUT)
    public List<Picture> uploadPictureAttachment(
            @PathVariable("grenadeId") Long grenadeId,
            @RequestParam("pictureType") PictureType pictureType,
            @RequestParam("files") MultipartFile[] multipartFiles
    ) {
        System.out.println(multipartFiles);
        grenadeService.uploadGrenadePictures(grenadeId, pictureType, multipartFiles);
        return null;
    }

    @RequestMapping(path = "/downloadGrenadeImage/{pictureId}", method = RequestMethod.GET)
    public ResponseEntity uploadPictureAttachment(
            @PathVariable("pictureId") Long pictureId
    ) throws IOException {
        File file = grenadeService.getPictureById(pictureId);
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

}
